package com.Practice.MyFirstProject.RestController;

import com.Practice.MyFirstProject.Entity.AirItLabs;
import com.Practice.MyFirstProject.Service.AiritlabsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
//@Controller
@RequestMapping("/api/airitlabs")
public class AiritLabsController {


    private AiritlabsService airitlabsService;
    private static final Logger LOGGER = LogManager.getLogger(AiritLabsController.class);
    //Dependency injection
    @Autowired
    public AiritLabsController(AiritlabsService airitlabsService)
    {
            this.airitlabsService = airitlabsService;
    }

    //defining endpoints
    @GetMapping
    public List<AirItLabs> findAll()
    {
        try
        {
            return airitlabsService.findAll();
        }
        catch(Exception e)
        {
            LOGGER.error("Error Finding AirItLab list: ",e);
            throw new RuntimeException("Error Finding AirItLab list: ",e);
        }

    }

    @GetMapping("/{Id}")
    public AirItLabs findAiritLabById(@PathVariable int Id )
    {
        try
        {
            return airitlabsService.findbyId(Id);
        }
        catch(Exception e)
        {
            LOGGER.error("Error Finding AirItLab with id: "+ Id,e);
            throw new RuntimeException("Error Finding AirItLab with id: "+ Id,e);
        }

    }

    @PostMapping("/add")
    public AirItLabs createAirItLab(@RequestBody AirItLabs airItLabs )
    {
        // if the pass id in json ....set it to zero
        //this is to force a save of new item...instead of update
        try
        {
           // System.out.println("Hello");
            LOGGER.debug("Debug Message Logged !!!");
           //System.out.println("Received Payload: " + airItLabs.toString());
            return airitlabsService.save(airItLabs);
        }
        catch(Exception e)
        {
            LOGGER.error("Error creating AiritLabs: ", e);
            throw new RuntimeException("Error creating AiritLabs: ", e);
        }

    }


    @PutMapping("/{update_Id}")
    public AirItLabs UpdateAirItLab(@RequestBody AirItLabs airItLabs, @PathVariable int update_Id )
    {
        // if the pass id in json ....set it to zero
        //this is to force a save of new item...instead of update
        try
        {
            // employees.setId(0);
            return airitlabsService.update(airItLabs,update_Id);
        }
        catch(Exception e)
        {
            LOGGER.error("Error updating AiritLabs ",e);
            throw new RuntimeException("Error updating AiritLabs ",e);
        }

    }


    @DeleteMapping("/{Id}")
    public void deleteAiritlabById(@PathVariable int Id)
    {
        try
        {
            airitlabsService.deleteById(Id);
        }
        catch(Exception e)
        {

            LOGGER.error("Error deleting AirItLab with id: "+ Id,e);
            throw new RuntimeException("Error deleting AirItLab with id: " + Id,e);
        }
    }


}
