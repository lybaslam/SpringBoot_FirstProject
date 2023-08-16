package com.Practice.MyFirstProject.Service;
import com.Practice.MyFirstProject.Entity.AirItLabs;
import com.Practice.MyFirstProject.Entity.Employee;
import com.Practice.MyFirstProject.Entity.Stack;
import com.Practice.MyFirstProject.Repository.AiritLabsRepository;
import com.Practice.MyFirstProject.Repository.EmployeeRepository;
import com.Practice.MyFirstProject.Repository.StackRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{


    private EmployeeRepository employeeDao;
    private AiritLabsRepository airitlabDao;
    private StackRepository stackDao;
    private static final Logger LOGGER= LogManager.getLogger(EmployeeServiceImpl.class);

    //dependency injection by constructor

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeDao, AiritLabsRepository airitlabDao, StackRepository stackDao)
    {
        this.employeeDao = employeeDao;
        this.airitlabDao = airitlabDao;
        this.stackDao = stackDao;
    }



    @Override
    public List<Employee> findAll()
    {

      try
      {
          return employeeDao.findAll();
      }
      catch(Exception  e)
      {
          LOGGER.error("Error Finding employee list: ",e);
          throw new RuntimeException("Error Finding employee list: ",e);
      }

    }

    @Override
    public Employee findbyId(int id)
    {

        try
        {
            Optional<Employee> existing_Employee= employeeDao.findById(id);

            return existing_Employee.get();
        }
        catch(Exception e)
        {
            LOGGER.error("Employee of Id does not exist: "+ id,e);
            throw new RuntimeException("Error Fetching Employee by ID:" + id,e);
        }

    }

    @Override
    @Transactional
    public Employee save(Employee employee)
    {
        // data integrity exception like key constrains, entity already exist exception
        try
        {
              /*
                Optional<AirItLabs> result = airitlabDao.findById(employee.getAirItLabs().getId());

                if (result.isPresent()) {

                    employee.setAirItLabs(result.get());
                }

                if(employee.getStackList()!=null)    //if there is no stacklist  in post request skip this step
                {
                    //attaching stack in present session
                    // Attaching stacks in the present session
                    Iterator<Stack> stackIterator = employee.getStackList().iterator();
                    //creating a temporary array of stacks
                    List<Stack> stacksToAdd = new ArrayList<>();
                    while (stackIterator.hasNext())
                    {
                        Stack temp = stackIterator.next();
                        Optional<Stack> r = stackDao.findById(temp.getId());

                        {
                            r.get().setEmployee(employee);
                            stacksToAdd.add(r.get());
                            // If you want to remove the original element from the list, use:
                            // stackIterator.remove();
                        } else {
                            //if stacking in list does not exist then first add stack in table
                            Stack newlyAddedStack = stackDao.save(temp);
                            newlyAddedStack.setEmployee(employee);
                            stacksToAdd.add(newlyAddedStack);
                        }
                    }
                    //setting employee stack list to temp stackstoAdd list
                    employee.setStackList(stacksToAdd);
                }*/
           //saving employee
           return employeeDao.save(employee) ;

        }
        catch (Exception e )
        {
            LOGGER.error("Error Saving Employee: "+employee,e);
            throw new RuntimeException("Error Saving Employee: "+employee,e);
        }
    }

    @Override
    @Transactional
    public Employee update(Employee employee,int update_id)
    {

        try
        {
            // updating given fields  existing employee by the fields given in the payload
            Optional<Employee>  existing_Employee_result= employeeDao.findById(update_id);
            if(employee.getName()!=null)
            {
                existing_Employee_result.get().setName(employee.getName());
            }
            if(employee.getDesignation()!=null)
            {
                existing_Employee_result.get().setDesignation(employee.getDesignation());
            }
            //setting reference of existing airitlab in the employee in a scenario where an employee was initially added by didnot had fk value of company id
            if(employee.getAirItLabs()!=null)
            {
                Optional<AirItLabs>  existing_lab_result= airitlabDao.findById(employee.getAirItLabs().getId());

                existing_Employee_result.get().setAirItLabs(existing_lab_result.get());

                //do i have to add employee too in the airit list of employee //check
            }
            //here adding given stacks in employees's list of stack
            if(employee.getStackList()!=null)
            {
                for(Stack Temp_stack:employee.getStackList())
                {
                    //If employee is referencing to existing Stack find it from db and set its reference
                    Optional<Stack> existing_stack= stackDao.findById(Temp_stack.getId());
                    if(existing_stack.isPresent())
                    {
                        existing_stack.get().setEmployee(existing_Employee_result.get());
                        existing_Employee_result.get().addStack(existing_stack.get());
                    }
                    else
                    {
                        //if stack does not exists
                        //setting employee reference in stack to be added
                        Temp_stack.setEmployee(existing_Employee_result.get());
                        //if stack is new, just simply add it to employee
                        existing_Employee_result.get().addStack(Temp_stack);
                    }

                }

            }
           //this will save employee and corresponding stacks too
            return employeeDao.save(existing_Employee_result.get());

        }
        catch (Exception e)
        {
            LOGGER.error("Error updating Employee: " + employee, e);
            throw new RuntimeException("Error updating Employee: " + employee, e);
        }
    }

    @Override
    @Transactional
    public Employee addEmployeeWithLabAndStacks(HashMap<String,Object> payload)
    {
        try
        {
            //checking if employee already exists
            Optional<Employee> emp_result = employeeDao.findById((int) payload.get("Employee.id"));
            Employee employeeToBeAdded;
            if(emp_result.isPresent())
            {
               // employee exists
                employeeToBeAdded = emp_result.get();
            }
            else
            {
                //making employee object to be added
                 employeeToBeAdded = new Employee((String) payload.get("Employee.Name"), (String) payload.get("Employee.Designation"));

            }


            //extracting list of stacks hashmap
            List<HashMap<String, Object>> StacksList = (List<HashMap<String, Object>>) payload.get("stackList");

            //Checks if given airitlab object exist
            Optional<AirItLabs> result = airitlabDao.findById((int) payload.get("AiritLabs.Id"));

            if (result.isPresent())
            {
                //1: if yes simply set its reference
                employeeToBeAdded.setAirItLabs(result.get());
            }
            else
            {  //2: if no make a new object
                //making airitlab object out of payload
                AirItLabs airItLabs = new AirItLabs((String) payload.get("AiritLabs.Address"), (String) payload.get("AiritLabs.Contact"), (String) payload.get("AiritLabs.Email"));
                employeeToBeAdded.setAirItLabs(airItLabs);
            }


            //looping through list of stack
            for (HashMap<String, Object> temp_stack_obj : StacksList)
            {

                Optional<Stack> stackOptional = stackDao.findById((int) temp_stack_obj.get("Id"));
                if(stackOptional.isPresent())   //if stack is already present
                {
                    //setting employee reference in stack
                    stackOptional.get().setEmployee(employeeToBeAdded);
                    //adding existing stack in employee
                    employeeToBeAdded.addStack(stackOptional.get());
                }
                else
                {
                    //if stack is not present make a new object and then add
                    Stack tempStack = new Stack((String) temp_stack_obj.get("Name"), (String) temp_stack_obj.get("Tool"));
                    //Setting employee reference on stack
                    tempStack.setEmployee(employeeToBeAdded);
                    //adding in employee stack list
                    employeeToBeAdded.addStack(tempStack);
                    //EmployeeStacks.add(tempStack);
                }

            }


            //saving employee and its associated entities
            return employeeDao.save(employeeToBeAdded);

        }
        catch(Exception e)
        {
            LOGGER.error("Error creating Employee with Lab and Stack  ",e);
            throw new RuntimeException("Error creating Employee with Lab and Stack  ",e);
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) {

        // id does not exist exception can occur here,illegal argument exception(does not match argument type
        //since relation is bidirectional so we need to first remove its reference from referencing side and then delete it
        try
        {
            Optional<Employee> existing_Employee_result =employeeDao.findById(id);
            if(existing_Employee_result.isPresent())
            {
                List<Stack> stacks= existing_Employee_result.get().getStackList();
                if(stacks!=null)
                {
                    for(Stack temp: stacks)
                    {
                        temp.setEmployee(null);  //setting reference back to null to avoid fk constraint
                    }
                }

                employeeDao.deleteById(id);

            }
            else
            {
                throw new RuntimeException("Error deleting Employee with id: " + id);
            }


        }
        catch(Exception e)
        {
            LOGGER.error("Error occurred while Deleting Employee with ID: " + id, e);
            throw new RuntimeException("Error occurred while Deleting Employee with ID: " + id,e);
        }

    }
}
