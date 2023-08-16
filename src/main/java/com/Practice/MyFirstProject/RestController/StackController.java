package com.Practice.MyFirstProject.RestController;
import com.Practice.MyFirstProject.Entity.Stack;
import com.Practice.MyFirstProject.Service.StackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/stacks")
public class StackController {

    private StackService stackService;
    private static final Logger LOGGER= LogManager.getLogger(StackController.class);

    @Autowired
    public StackController(StackService stackService) {
        this.stackService = stackService;
    }

    // defining endpoints
    @GetMapping
    public List<Stack>findAll()
    {
        try
        {
            return stackService.findAll();
        }
        catch(Exception e)
        {
            LOGGER.error("Error Finding Stack list: ",e);
            throw new RuntimeException("Error Finding Stack list: ",e);
        }
    }

    @GetMapping("/{id}")
    public Stack findStackById(@PathVariable int id)
    {
        try
        {
            return stackService.findbyId(id);
        }
        catch(Exception e)
        {
            LOGGER.error("Error Finding Stack with id: "+ id,e);
            throw new RuntimeException("Error Finding Stack id: "+id,e);
        }
    }

    @PostMapping
    public Stack createStack(@RequestBody  Stack stack)
    {
        // if the pass id in json ....set it to zero
        //this is to force a save of new item...instead of update

        try
        {

            return stackService.save(stack);
        }
        catch(Exception e)
        {
            LOGGER.error("Error creating stack ",e);
            throw new RuntimeException("Error creating stack ",e);
        }
    }

    @PutMapping("/{update_id}")
    public Stack UpdateStack(@RequestBody Stack stack,@PathVariable int update_id )
    {
        // if the pass id in json ....set it to zero
        //this is to force a save of new item...instead of update

        try
        {

            return stackService.update(stack,update_id);
        }
        catch(Exception e)
        {
            LOGGER.error("Error updating Stack ",e);
            throw new RuntimeException("Error updating Stack ",e);
        }

    }


    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id)
    {
        try
        {
            stackService.deleteById(id);
        }
        catch(Exception e)
        {
            LOGGER.error("Error deleting stack with id: "+ id,e);
            throw new RuntimeException("Error deleting stack with id: " + id,e);
        }
    }

}
