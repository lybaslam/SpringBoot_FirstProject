package com.Practice.MyFirstProject.Service;
import com.Practice.MyFirstProject.Entity.AirItLabs;
import com.Practice.MyFirstProject.Entity.Employee;
import com.Practice.MyFirstProject.Repository.AiritLabsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AiritlabsServiceImpl  implements AiritlabsService{

    private AiritLabsRepository airitlabsDao;

    private static final Logger LOGGER = LogManager.getLogger(AiritlabsServiceImpl.class);

    //dependency injection by constructor
    @Autowired
    public AiritlabsServiceImpl(AiritLabsRepository airitlabsDao)
    {
        this.airitlabsDao = airitlabsDao;
    }

    @Override
    public List<AirItLabs> findAll()
    {

        try
        {
            return  airitlabsDao.findAll();
        }
        catch(Exception  e)
        {
            LOGGER.error("Error Finding AirItLab list: ",e);
            throw new RuntimeException("Error Finding AirItLab list: ",e);
        }

    }

    @Override
    public AirItLabs findbyId(int id) {

        // id does not exist exception can occur here,illegal argument exception
        try
        {

            //checking if object is returned
            Optional<AirItLabs> existing_airitlab_result= airitlabsDao.findById(id);

            return existing_airitlab_result.get();


        }
        catch(Exception e)
        {
            LOGGER.error("Error occurred while fetching AirItLabs with ID: " + id, e);
            throw new RuntimeException("Error occurred while fetching AirItLabs with ID: " + id,e);
        }

    }


    //
    @Override
    @Transactional
    public AirItLabs save(AirItLabs airItLabs) {
       // data integrity exception like key constrains, entity already exist exception
        try
       {
            /*

           // creating temorary variable
           AirItLabs temp1=new AirItLabs();
           // setting values of airit lab fields
           System.out.println("Setting temp AirItlabs object");
           temp1.setAddress(airItLabs.getAddress());
           temp1.setContact(airItLabs.getContact());
           temp1.setEmail(airItLabs.getEmail());
           //  creating a new  employee list object
           List<Employee> tempEmpl= new ArrayList<>();
           for(Employee employee: airItLabs.getEmployeeList() )
           {
               //taking one employee from payload and making its new object and adding field values
               Employee temp2= new Employee();
               temp2.setName(employee.getName());
               temp2.setDesignation(employee.getDesignation());
               //creating stack object
               List<Stack> stacks=new ArrayList<>();
               for(Stack s: employee.getStackList() )
               {
                   //taking one object and saving its copy in new list
                   Stack stack= new Stack();
                   stack.setName(s.getName());
                   stack.setTool(s.getTool());
                   //adding in new stack list
                   stacks.add(stack);
               }
               //setting employee stack
               temp2.setStackList(stacks);
                 //adding employee in temp employee list
               tempEmpl.add(temp2);
           }
            //setting list of employee in airit temp object
           temp1.setEmployeeList(tempEmpl);


             */

      // saving the created temp1 air it lab object in db

           return airitlabsDao.save(airItLabs);
       }
       catch(Exception e)
       {
            LOGGER.error("Error occurred while saving AirItLabs: " + airItLabs, e);
           throw new RuntimeException("Error occurred while saving AirItLabs: " + airItLabs,e);
       }

    }

    @Override
    @Transactional
    public AirItLabs update(AirItLabs airItLabs, int update_Id)
    {

        try
        { //updating given fields
            Optional<AirItLabs>  existing_airitlab_result= airitlabsDao.findById(update_Id);
             if(airItLabs.getAddress()!=null)
             {
                 existing_airitlab_result.get().setAddress(airItLabs.getAddress());
             }
            if(airItLabs.getContact()!=null)
            {
                existing_airitlab_result.get().setContact(airItLabs.getContact());
            }
            if(airItLabs.getEmail()!=null)
            {
                existing_airitlab_result.get().setEmail(airItLabs.getEmail());
            }

            if(airItLabs.getEmployeeList()!=null)
            {

                //adding new employee through airitlab
                for(Employee Temp_employee:airItLabs.getEmployeeList())
                {
                     //setting reference of airitlab in the given new employee
                     Temp_employee.setAirItLabs(existing_airitlab_result.get());
                     //adding the temp-employee in airit's employeelist
                    existing_airitlab_result.get().addEmployee(Temp_employee);
                }

            }
            //saving airitlab object
             return airitlabsDao.save(existing_airitlab_result.get());
        }
        catch(Exception e){
            LOGGER.error("Error occurred while updating AirItLabs: " + airItLabs, e);
            throw new RuntimeException("Error occurred while updating  AirItLabs: " + airItLabs,e);
        }



    }

    @Override
    @Transactional
    public void deleteById(int id) {
        // id does not exist exception can occur here,illegal argument exception(does not match argument type
        //since relation is bidirectional so we need to first remove its reference from referencing side and then delete it
        try
        {
            Optional<AirItLabs> existing_airitlab_result = airitlabsDao.findById(id);
            if(existing_airitlab_result.isPresent())
            {
                List<Employee> employees=existing_airitlab_result.get().getEmployeeList();

                if(employees!=null)
                {
                    for(Employee temp:employees )
                    {
                        temp.setAirItLabs(null);   //setting reference back to null if we do not do it, it gives foreign key constraint
                    }
                }

                airitlabsDao.deleteById(id);

            }
            else
            {
                throw new RuntimeException("Error occurred while Deleting AirItLabs with ID: " + id);
            }


        }
        catch(Exception e)
        {
             LOGGER.error("Error occurred while Deleting AirItLabs with ID: " + id, e);
            throw new RuntimeException("Error occurred while Deleting AirItLabs with ID: " + id,e);
        }

    }
}
