package com.Practice.MyFirstProject.Entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="airitlabs")
public class AirItLabs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int Id;
    @Column(name="address")
    private String Address;
    @Column(name="contact")
    private String Contact;
    @Column(name="email")
    private String Email;

     //Defining mappings , deciding fetch type===???
    //mappedBy attribute in the AirItLabs entity specifies that the AirItLabs entity is the owner of the relationship and the airItLabs field in the Employee entity owns the relationship.
    @OneToMany(fetch=FetchType.LAZY,mappedBy = "airItLabs",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonManagedReference
    List<Employee> employeeList;


    //Defining mappings

    // constructors
    public AirItLabs() {
    }

    public AirItLabs(String address, String contact, String email) {
        Address = address;
        Contact = contact;
        Email = email;
    }

    public AirItLabs(int id, String address, String contact, String email, List<Employee> employeeList) {
        Id = id;
        Address = address;
        Contact = contact;
        Email = email;
        this.employeeList = employeeList;
    }
//getter and setter


    public int getId() {
        return Id;
    }
    @JsonProperty("Id")
    public void setId(int id) {
        Id = id;
    }

    public String getAddress() {
        return Address;
    }

    @JsonProperty("Address")
    public void setAddress(String address) {
        Address = address;
    }

    public String getContact() {
        return Contact;
    }

    @JsonProperty("Contact")
    public void setContact(String contact) {
        Contact = contact;
    }

    public String getEmail() {
        return Email;
    }
    @JsonProperty("Email")
    public void setEmail(String email) {
        Email = email;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }
    @JsonProperty("employeeList")
    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

     // Adding convienence method for bidirectional relation
      public void addEmployee(Employee employee)
      {
          if(employeeList==null)
          {
              employeeList=new ArrayList<>();
          }

          employeeList.add(employee);
          //setting lab for this employee
          employee.setAirItLabs(this);
      }

    //to string
    @Override
    public String toString() {
        return "AirItLabs{" +
                "Id=" + Id +
                ", Address='" + Address + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}
