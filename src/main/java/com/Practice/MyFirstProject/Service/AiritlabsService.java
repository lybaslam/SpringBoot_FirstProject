package com.Practice.MyFirstProject.Service;
import com.Practice.MyFirstProject.Entity.AirItLabs;
import java.util.List;


public interface AiritlabsService {

    //Read entity
    List<AirItLabs> findAll();
    AirItLabs findbyId(int id);

    //Post
    AirItLabs save(AirItLabs airItLabs);
    //Put entity
    AirItLabs update(AirItLabs airItLabs,int  update_Id);
    //Delete  entity
    void deleteById(int id);
}
