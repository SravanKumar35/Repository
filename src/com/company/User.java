package com.company;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.awt.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Scanner;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.valueOf;

/***********************************************************************************************************************/

public class User
{
    protected String name;
    protected String user_id ;
    protected String pswd;
    protected String privilege;
    Scanner s = new Scanner(System.in);
    File doc;


//    User Functions

    public void get_details()
    {
        System.out.println("\nPlease Enter your Username\n");
        name = s.nextLine();
        System.out.println("Enter your User-id(e-mail)\n");
        System.out.println("Please note that email id is required for your further logins\n");
        user_id = s.nextLine();
        System.out.println("Create your password\n");
        pswd = s.nextLine();
    }

    public void register() throws IOException
    {
        User temp = this;
        Admin admin = new Admin(temp);
    }

    public static boolean login(String login_id, String password) throws IOException
    {

        CsvReader login = new CsvReader("Users.csv");
        login.readHeaders();
        boolean result = false;
        while(login.readRecord())
        {
            String x = login.get("user_id");
            String y = login.get("password");
            if(x.equals(login_id))
            {
                System.out.println("username exists");
                if(y.equals(password))
                {
                    String z = login.get("name");
                    result = true;
                    break;
                }
                else
                {
                    System.out.println("Incorrect Password\nTry Again");
                    result = false;
                }
            }
            else
            {
            }
        }
        login.close();
        return result;
    }




    public static void upload(Document document, String name) throws IOException {
        int x;
        String fname1, src_Path, des_Path, ext1, ext2;
        File sourceFile, destFile = null;
        boolean p =false;
        Scanner s = new Scanner(System.in);
        do
        {
            x = 0;
            System.out.println("\n\nEnter the name of the file with which you want to upload");
            fname1 = s.nextLine();
            src_Path = "E:\\Required\\4th SEM\\Repo\\" + fname1;
            sourceFile = new File(src_Path);
            if (!sourceFile.exists())
            {
                System.out.println("Source File Not Found!");
            }
            else
            {
                ext1 = getFileExtension(fname1);

                document.getDetails();
                document.author = name;
                des_Path = "G:\\Repository\\"  + document.doc_name;
                destFile = new File(des_Path);
                document.dest_Addrss = des_Path;

                ext2 = getFileExtension(document.doc_name);

                if(!ext1.equals(ext2))
                {
                    x=1;
                    System.out.println("\nExtensions of Source File and Destination File are not same\nPlease Try Again\n");
                }
            }

        }while(x == 1);



        /* verify whether file exist in source location */


        /* if file not exist then create one */
        if (!destFile.exists())
        {
            try {
                destFile.createNewFile();
                System.out.println("\n\nDestination file doesn't exist. Creating one!");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        FileChannel source = null;
        FileChannel destination = null;

        try
        {

            /**
             * getChannel() returns unique FileChannel object associated a file
             * output stream.
             */
            source = new FileInputStream(sourceFile).getChannel();

            destination = new FileOutputStream(destFile).getChannel();

            if (destination != null && source != null)
            {
                destination.transferFrom(source, 0, source.size());
                System.out.println("\n\nFile Saved\n\n");
                p = true;
            }

        } catch (IOException e)
        {
            p= false;
            e.printStackTrace();
        }

        finally
        {
            if (source != null)
            {
                try
                {
                    source.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (destination != null)
            {
                try
                {
                    destination.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }



            PDFManager pdfManager = new PDFManager();
            pdfManager.ToText(document.dest_Addrss);

            String b = name + ".csv";
            boolean alreadyExists = new File(b).exists();
            try
            {
                CsvWriter csvOutput = new CsvWriter(new FileWriter(b, true), ',');
                if (!alreadyExists)
                {
//                csvOutput.write("Page Count");
                    csvOutput.write("User");
                    csvOutput.write("Path");

                    csvOutput.endRecord();
                }
                // else assume that the file already has the correct header line

                // write out a few records
//            csvOutput.write(count);
                csvOutput.write(name);
                csvOutput.write(document.dest_Addrss);
                csvOutput.endRecord();

                csvOutput.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }





        }


//        If file uploads Successfully
        if(p)
        {
            System.out.println("\n\n\t\tFile Successfully Uploaded\n");

        }
        else
        {
            System.out.println("Error occurred while file Uploading");
        }


    }

    private static String getFileExtension(String Name) {

        if(Name.lastIndexOf(".") != -1 && Name.lastIndexOf(".") != 0)
            return Name.substring(Name.lastIndexOf(".")+1);
        else return "";
    }




    static void view(Document document) throws IOException {
        Scanner s = new Scanner(System.in);
        document.display_details();
        System.out.println("Press 1 to open the file\n");
        int x = s.nextInt();
        File file = new File(document.dest_Addrss);
        if(x==1)
        {

            //first check if Desktop is supported by Platform or not
            if(!Desktop.isDesktopSupported()){
                System.out.println("Desktop is not supported");
                return;
            }

            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) desktop.open(file);

            //let's try to open PDF file
            if(file.exists()) desktop.open(file);

        }

    }



    public static void search() throws IOException {
        CsvReader meta = new CsvReader("Meta.csv");
        meta.readHeaders();
        Scanner s = new Scanner(System.in);
        System.out.println("Enter keyword: ");
        String key;
        int i=1;
        key = s.nextLine();
        String[] data = new String[100];
        int search_count = 0;
        while(meta.readRecord())
        {
            String x = meta.get("Keywords");
            if(x.equals(key))
            {
                System.out.println(i + ". " + meta.get("Title"));
                String path = meta.get("Path");
                data[i++] = path;
                search_count++;



            }

            String y = meta.get("Title");

            if (y.equals(key))
            {
                System.out.println(i + ". " + meta.get("Title"));
                String path = meta.get("Path");
                data[i++] = path;
                search_count++;

            }

            String z = meta.get("Author");

            if(z.equals(key))
            {
                System.out.println(i + ". " + meta.get("Title"));
                String path = meta.get("Path");
                data[i++] = path;
                search_count++;
            }


        }

        System.out.println("Choose your documents and 0 to discard\n");
        int choice;
        while(true)
        {
            choice = s.nextInt();
            if(choice == 0)
                break;
            else
            {
                File file = new File(data[choice]);
                if(!Desktop.isDesktopSupported()){
                    System.out.println("Desktop is not supported");
                    return;
                }

                Desktop desktop = Desktop.getDesktop();
                if(file.exists()) desktop.open(file);

                //let's try to open PDF file
                file = new File("/Users/pankaj/java.pdf");
                if(file.exists()) desktop.open(file);
            }
        }
        meta.close();


    }



    public static void delete()
    {

    }



    public static void logout()
    {

    }


}