package com.Practice.MyFirstProject.Service;
import com.Practice.MyFirstProject.Entity.Employee;
import java.util.HashMap;
import java.util.List;


public interface EmployeeService {

    //Read entity
    List<Employee> findAll();
    Employee findbyId(int id);
    //Post and Put entity
    Employee save(Employee employee);
    Employee update(Employee employee,int update_id);
    Employee addEmployeeWithLabAndStacks( HashMap<String,Object> payload);
    //Delete  entity
    void deleteById(int id);

}
