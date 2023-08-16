package com.Practice.MyFirstProject.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String Name;
    @Column(name="designation")
    private String Designation;


    // owning side of  "air_it_lab_id" FK
    //defining bidirectional mapping
    // donot cascade delete
    @ManyToOne(fetch=FetchType.LAZY,cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    //join column value is same as fk in employee table
    //joinColumn attribute is used to specify that the air_it_lab_id column in the Employee table will be used as the foreign key to link to the AirItLabs table.
    @JoinColumn(name="air_it_lab_id")
    @JsonBackReference
    private AirItLabs airItLabs;


    //Fetch type to be decided
    @OneToMany(fetch=FetchType.LAZY,mappedBy = "employee",cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonManagedReference
    List<Stack> stackList;



    //constructor
    public Employee() {
    }

    public Employee(String name, String designation) {
        Name = name;
        Designation = designation;

    }
    // getter and setter

    public int getId() {
        return id;
    }
     @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        Name = name;
    }

    public String getDesignation() {
        return Designation;
    }
    @JsonProperty("Designation")
    public void setDesignation(String designation) {
        Designation = designation;
    }

    public AirItLabs getAirItLabs() {
        return airItLabs;
    }

    @JsonProperty("airItLabs")
    public void setAirItLabs(AirItLabs airItLabs) {
        this.airItLabs = airItLabs;
    }

    public List<Stack> getStackList() {
        return stackList;
    }
    @JsonProperty("stackList")
    public void setStackList(List<Stack> stackList) {
        this.stackList = stackList;
    }




    // Adding convience method to add stack
    public void addStack(Stack stack)
    {
        if(stackList==null)
        {
            stackList=new ArrayList<>();
        }

        stackList.add(stack);
        //adding employee for this stack
        stack.setEmployee(this);

    }

   //tostring

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Designation='" + Designation + '\'' +
                ", airitlab_id='" + airItLabs.getId() + '\'' +
                '}';
    }
}
