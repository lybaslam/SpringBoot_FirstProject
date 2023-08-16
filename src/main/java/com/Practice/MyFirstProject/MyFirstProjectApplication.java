package com.Practice.MyFirstProject;

import com.Practice.MyFirstProject.Entity.AirItLabs;
import com.Practice.MyFirstProject.Entity.Employee;
import com.Practice.MyFirstProject.Entity.Stack;
import com.Practice.MyFirstProject.Service.AiritlabsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyFirstProjectApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(MyFirstProjectApplication.class, args);
	}
	/*@Bean
	public CommandLineRunner commandLineRunner(AiritlabsService airitlabsService ){
		return runner ->{

			//createLab(airitlabsService);

		};
	}

	public void createLab(AiritlabsService airitlabsService)
	{


		System.out.println("Creating lab object");



		// creating temorary variable
		 AirItLabs temp1=new AirItLabs();
		Stack s1= new Stack("Backend Development", "Spring Boot");
		Stack s2= new Stack("Design Tools", "Sketch");
		Employee e1= new Employee("Sania","Software Developer");
		Employee e2= new Employee("Wajiha", "UI/UX Designer");
		// setting values of airit lab fields
		System.out.println("Setting temp AirItlabs object");
		temp1.setAddress("Model Town");
		temp1.setContact("0340-5814982");
		temp1.setEmail("airitlabs@gmail.com");


		e1.addStack(s1);
		e2.addStack(s2);
		temp1.addEmployee(e1);
		temp1.addEmployee(e2);

		System.out.println("Saving lab ");

		airitlabsService.save(temp1);
		System.out.println("Done!!!");
	}

	 */

}
