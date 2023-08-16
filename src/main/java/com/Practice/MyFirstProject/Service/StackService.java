package com.Practice.MyFirstProject.Service;
import com.Practice.MyFirstProject.Entity.Stack;
import java.util.List;


public interface StackService {

    //Read entity
    List<Stack> findAll();
    Stack findbyId(int id);
    //Post
    Stack save(Stack stack);
    //and Put entity

    Stack update(Stack stack,int update_id);
    //Delete  entity
    void deleteById(int  id);
}
