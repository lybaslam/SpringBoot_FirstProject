package com.Practice.MyFirstProject.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

@Service
public class FileOperations {

    private final DataSource dataSource;
    private static final Logger LOGGER = LogManager.getLogger(FileOperations.class);

    @Autowired
    public FileOperations(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void write_to_csv(String filePath) {
        try {
            // establishing data base connection
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();

            //Query result
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Stack s");

            //fileWriter
            FileWriter fileWriter = new FileWriter(filePath);

            // Write CSV Header
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                //excluding create_at column while writing in csv file
                if (!"created_at".equalsIgnoreCase(resultSet.getMetaData().getColumnName(i))) {
                    fileWriter.append(resultSet.getMetaData().getColumnName(i));
                }
                if (i < resultSet.getMetaData().getColumnCount()) {
                    fileWriter.append(',');
                }
            }

            //appending next line
            fileWriter.append('\n');

            // Write CSV Data
            while (resultSet.next()) {

                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //excluding create_at column while writing in csv file
                    if (!"created_at".equalsIgnoreCase(resultSet.getMetaData().getColumnName(i))) {
                        fileWriter.append(resultSet.getString(i));
                    }
                    if (i < resultSet.getMetaData().getColumnCount()) {
                        fileWriter.append(',');
                    }
                }

                //appending the line into file after writing one row
                fileWriter.append("\n");
            }

            fileWriter.close();
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            LOGGER.error("Error writing to csv", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.error("Error writing to csv", e);
            throw new RuntimeException(e);
        }
    }


    public void write_to_Db(MultipartFile multipartFile) {
        try {
            // establishing data base connection
            Connection connection = dataSource.getConnection();
            //using jdbc for inserting
            PreparedStatement statement = connection.prepareStatement("Insert into stack (id,tool,name,created_at,employee_id) values(?,?,?,?,?)");

            //converting file to input stream then converting it to character and using buffer reader for easy reading
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
            String LineText =bufferedReader.readLine();
            while((LineText = bufferedReader.readLine()) != null)
            {
                String [] splittedString= LineText.split(",");
                String dateString = splittedString[3];//for converting csv format of date to  db format
                //System.out.println(dateString);
                //System.out.println(splittedString.length);
                // Parse the input date string into a Date object
                SimpleDateFormat  inputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                java.util.Date  parsedDate = inputFormat.parse(dateString);

                //Empty check
                if(splittedString[0].isEmpty())
                {   //it will auto generate id
                    statement.setNull(1, java.sql.Types.INTEGER);
                }
                else
                {
                    statement.setInt(1,Integer.parseInt(splittedString[0]));
                }

                statement.setString(2,splittedString[1].isEmpty() ? null: splittedString[1]);
                statement.setString(3,splittedString[2] .isEmpty() ? null:splittedString[2]);
                statement.setTimestamp(4, new java.sql.Timestamp(parsedDate.getTime()));
                if(!"null".equals(splittedString[4]))
                {
                    statement.setInt(5,Integer.parseInt(splittedString[4]));
                }
                else
                {
                    statement.setNull(5, java.sql.Types.INTEGER);
                }

                statement.executeUpdate();
            }

            bufferedReader.close();
            statement.close();
            connection.close();
        } catch (SQLException e)
        {
            LOGGER.error("Error writing to Database", e);
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            LOGGER.error("Error writing to Database", e);
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
