package com.Practice.MyFirstProject.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name="stack")
public class Stack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int Id;
    @Column(name="name")
    private  String Name;
    @Column(name="tool")
    private String Tool;

    @ManyToOne(fetch=FetchType.LAZY,cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="employee_id")
    @JsonBackReference
    private  Employee employee;


    // constructors default constructor is necessary
    public Stack() {
    }

   /*
    public Stack(int id, String name, String tool, Employee employee) {
        Id = id;
        Name = name;
        Tool = tool;
        this.employee = employee;
    } */
    public Stack(String name, String tool) {
        Name = name;
        Tool = tool;
    }

    //getter and setter

    public int getId() {
        return Id;
    }

    @JsonProperty("Id")
    public void setId(int id) {
        Id = id;
    }



    public String getName() {
        return Name;
    }
    @JsonProperty("Name")
    public void setName(String name) {
        Name = name;
    }

    public String getTool() {
        return Tool;
    }

    @JsonProperty("Tool")
    public void setTool(String tool) {
        Tool = tool;
    }

    @JsonProperty("employee")
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    //toString

    @Override
    public String toString() {
        return "Stack{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Tool='" + Tool + '\'' +
                '}';
    }
}
