package com.Practice.MyFirstProject.RestController;
import com.Practice.MyFirstProject.Service.FileOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/FileOperations")
public class ImportingAndExportingFile {

    @Autowired
    private FileOperations fileOperations;
    private static final Logger LOGGER = LogManager.getLogger(ImportingAndExportingFile.class);
    @GetMapping("/export/{filePath}")
    public String exportEntities(@PathVariable String filePath) {
        try
        {
            fileOperations.write_to_csv(filePath); // Export to CSV
            return "Entities exported successfully!";
        }
        catch (Exception e)
        {
            LOGGER.error("Error writing to csv",e);
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/upload")
    public String importEntities(@RequestParam("file")MultipartFile multipartFile) {
        try
        {
            fileOperations.write_to_Db(multipartFile); // Export to CSV
            return "Entities imported successfully!";
        }
        catch (Exception e)
        {
            LOGGER.error("Error writing to Database",e);
            throw new RuntimeException(e);
        }
    }
}
