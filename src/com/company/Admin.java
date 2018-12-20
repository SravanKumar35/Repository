package com.company;

import com.csvreader.CsvWriter;

import java.io.*;
import java.util.InputMismatchException;


public class Admin
{
    protected String admin_id;
    protected String password;


    Admin()
    {

    }



    Admin(User temp) throws IOException {
        String login_id = temp.user_id;
        String password = temp.pswd;
        String name = temp.name;
        boolean result = store_user(login_id, password, name);
        if(result)
            System.out.println("Successfully Registered\n");
        else
            System.out.println("User not registered\n");
    }

    Admin(Student temp) throws IOException
    {
        String login_id = temp.user_id;
        String password = temp.pswd;
        String name = temp.name;
        boolean result = store_user(login_id, password, name);
        if(result)
            System.out.println("Successfully Registered\n");
        else
            System.out.println("User not registered\n");
    }

    Admin(Faculty temp) throws IOException
    {
        String login_id = temp.user_id;
        String password = temp.pswd;
        String name = temp.name;
        boolean result = store_user(login_id, password, name);
        if(result)
            System.out.println("Successfully Registered\n");
        else
            System.out.println("User not registered\n");
    }



    private boolean store_user(String login_id, String password, String name) throws IOException
    {

        String outputFile = "Users.csv";

        // before we open the file check to see if it already exists
        boolean alreadyExists = new File(outputFile).exists();

        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

            // if the file didn't already exist then we need to write out the header line
            if (!alreadyExists)
            {
                csvOutput.write("user_id");
                csvOutput.write("password");
                csvOutput.write("name");

                csvOutput.endRecord();
            }
            // else assume that the file already has the correct header line

            // write out a few records
            csvOutput.write(login_id);
            csvOutput.write(password);
            csvOutput.write(name);
            csvOutput.endRecord();

            csvOutput.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}

