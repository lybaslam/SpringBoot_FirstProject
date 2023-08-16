package com.Practice.MyFirstProject.Service;
import com.Practice.MyFirstProject.Entity.Employee;
import com.Practice.MyFirstProject.Entity.Stack;
import com.Practice.MyFirstProject.Repository.EmployeeRepository;
import com.Practice.MyFirstProject.Repository.StackRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StackServiceImpl implements StackService{

    private StackRepository stackDao;
    private EmployeeRepository employeeDao;
    private static final Logger LOGGER= LogManager.getLogger(StackServiceImpl.class);
   //dependency injection constructor

    @Autowired
    public StackServiceImpl(StackRepository stackDao,EmployeeRepository employeeDao)
    {

        this.stackDao = stackDao;
        this.employeeDao=employeeDao;
    }

    @Override
    public List<Stack> findAll()
    {
       try
       {
           return  stackDao.findAll();
       }
       catch(Exception e)
       {
           LOGGER.error("Error Finding Stack list: ",e);
           throw new RuntimeException("Error Finding Stack list: ",e);
       }

    }

    @Override
    public Stack findbyId(int  id)
    {
        // id does not exist exception can occur here,illegal argument exception
        try
        {

            //checking if object is returned
            Optional<Stack> existing_stack_result= stackDao.findById(id);
            return existing_stack_result.get();

        }
        catch(Exception e)
        {
            LOGGER.error("Error occurred while fetching Stack with ID: " + id, e);
            throw new RuntimeException("Error occurred while fetching Stack with ID: " + id,e);
        }

    }

    @Override
    @Transactional
    public Stack save(Stack stack) {

        // data integrity exception like key constrains, entity already exist exception
        try
        {
            return stackDao.save(stack);
        }
        catch(Exception e){
            LOGGER.error("Error occurred while saving Stack: " + stack, e);
            throw new RuntimeException("Error occurred while saving Stack: " + stack,e);
        }
    }

    @Override
    @Transactional
    public Stack update(Stack stack, int update_id) {
        try
        {
            Optional<Stack> existing_stack_result = stackDao.findById(update_id);
            if(stack.getName()!=null)
            {
                existing_stack_result.get().setName(stack.getName());
            }
            if(stack.getTool()!=null)
            {
                existing_stack_result.get().setTool(stack.getTool());
            }

            //adding employee reference to already existing stack
            if(stack.getEmployee()!=null)
            {
                Optional<Employee> existing_emp_result = employeeDao.findById(stack.getEmployee().getId());
                existing_stack_result.get().setEmployee(existing_emp_result.get());
                //do i have to add stack too in employee's stacklist
            }


            return stackDao.save(existing_stack_result.get());
        }
        catch (Exception e)
        {
            LOGGER.error("Error occurred while updating  Stack: " + stack, e);
            throw new RuntimeException("Error occurred while updating Stack: " + stack,e);
        }

    }

    @Override
    @Transactional
    public void deleteById(int nameid) {
        // id does not exist exception can occur here,illegal argument exception(does not match argument type
        try
        {
            Optional<Stack> existing_stack_result = stackDao.findById(nameid);
            if(existing_stack_result.isPresent())
            {
                stackDao.deleteById(nameid);
            }
            else {
                throw new RuntimeException("Error occurred while Deleting Stack with ID: " + nameid);
            }

        }
        catch(Exception e)
        {
            LOGGER.error("Error occurred while Deleting Stack with ID: " + nameid, e);
            throw new RuntimeException("Error occurred while Deleting Stack with ID: " + nameid,e);
        }

    }
}
