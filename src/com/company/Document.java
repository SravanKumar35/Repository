package com.company;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/***********************************************************************************************************************/

public class Document
{
    protected String doc_name;
    protected String doc_id;
    protected String author;
    protected  String upload_date;
    protected String keywords;
    protected String category;
    protected String dest_Addrss;
    Scanner s = new Scanner(System.in);


    Document(String user)
    {
        String s = user + ".csv";
        boolean alreadyExists = new File(s).exists();

        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(s, true), ',');

            // if the file didn't already exist then we need to write out the header line
            if (!alreadyExists)
            {
                csvOutput.write("username");
                csvOutput.write("document_path");

                csvOutput.endRecord();
            }
            // else assume that the file already has the correct header line

            CsvReader login = new CsvReader(s);
            login.readHeaders();
            boolean result = false;
            while(login.readRecord())
            {
                String x = login.get("user_id");
                if(x.equals(user))
                {
                    csvOutput.write(dest_Addrss);
                    csvOutput.endRecord();
                }
                else
                {
                    csvOutput.write(user);
                    csvOutput.write(dest_Addrss);
                }
            }
            login.close();



            csvOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //            Document Class Function getDetails()

    public void display_details() {

        System.out.println("\n\nDocument Name: " + doc_name);
        System.out.println("Document ID: " + doc_id);
        System.out.println("Author: " + author);
        System.out.println("Upload Date: " + upload_date);
        System.out.println("Keywords: " + keywords);
        System.out.println("Category: " + category);

    }

//            Document Class Function getDetails()

    public void getDetails()
    {
        System.out.println("\n\nEnter the document Name");
        doc_name = s.nextLine();
        System.out.println("\n\nEnter the category Of Document");
        category = s.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        upload_date = (dtf.format(now));
    }

}
