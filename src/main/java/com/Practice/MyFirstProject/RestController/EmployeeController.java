package com.Practice.MyFirstProject.RestController;
import com.Practice.MyFirstProject.Entity.Employee;

import com.Practice.MyFirstProject.Service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private static final Logger LOGGER= LogManager.getLogger(EmployeeController.class);


    @Autowired
    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    //defining endpoints

    @GetMapping
    public List<Employee> findEmployees()
    {
        try
        {
            return employeeService.findAll();
        }
        catch(Exception e)
        {
            LOGGER.error("Error Finding employee list: ",e);
            throw new RuntimeException("Error Finding employee list: ",e);
        }

    }

    @GetMapping("/{employeeId}")
    public Employee findEmployees(@PathVariable int employeeId )
    {
        try
        {
            return employeeService.findbyId(employeeId);
        }
        catch(Exception e)
        {
            LOGGER.error("Employee with given id does not exist: "+ employeeId,e);
            throw new RuntimeException("Error Finding employee with id: "+ employeeId,e);
        }

    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employees )
    {
        // if the pass id in json ....set it to zero
        //this is to force a save of new item...instead of update
        try
        {

            return employeeService.save(employees);
        }
        catch(Exception e)
        {
            LOGGER.error("Error creating employee ",e);
            throw new RuntimeException("Error creating employee ",e);
        }

    }


    @PutMapping("/{update_id}")
    public Employee UpdateEmployee(@RequestBody Employee employees,@PathVariable int update_id )
    {
        // if the pass id in json ....set it to zero
        //this is to force a save of new item...instead of update
        try
        {
            return employeeService.update(employees,update_id);
        }
        catch(Exception e)
        {
            LOGGER.error("Error updating employee ",e);
            throw new RuntimeException("Error updating employee ",e);
        }

    }


    @DeleteMapping("/{employeeid}")
    public void deleteEmployee(@PathVariable int employeeid)
    {
        try
        {
            employeeService.deleteById(employeeid);
        }
        catch(Exception e)
        {
            LOGGER.error("Error deleting employee with id: "+ employeeid,e);
            throw new RuntimeException("Error deleting employee with id: " + employeeid,e);
        }
    }
   //adding an employee payload given its company and stacks
    @PostMapping("/process")
    public Employee addEmployeeWithLabAndStacks(@RequestBody HashMap<String,Object> payload)
    {
        try {

            return  employeeService.addEmployeeWithLabAndStacks(payload);
        }
        catch(Exception e)
        {
            LOGGER.error("Error constructing Employee with Lab and Stack  ",e);
            throw new RuntimeException("Error constructing Employee with Lab and Stack  ",e);
        }

    }

}
