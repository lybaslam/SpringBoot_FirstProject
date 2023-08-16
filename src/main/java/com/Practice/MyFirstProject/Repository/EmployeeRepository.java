package com.Practice.MyFirstProject.Repository;
import com.Practice.MyFirstProject.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
   //no code
}
