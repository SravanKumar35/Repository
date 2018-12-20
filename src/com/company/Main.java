package com.company;

import com.csvreader.CsvReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Boolean.TRUE;


public class Main {

    public static void main(String[] args) throws IOException {

        Student student;
        Faculty faculty;
        User user;

        do {

            System.out.println("\n\n\n\n\t\t\t\t\t\t\t.....Welcome to the Project.....\n\n\n\n");
            System.out.println("\n\n\n\n\t\t\t\t\t\t\t1. Register");
            System.out.println("\n\t\t\t\t\t\t\t2. Login");
            System.out.println("\n\t\t\t\t\t\t\t3. Exit\n\n\n");

            Scanner s = new Scanner(System.in);
            int choice1 = s.nextInt();
            boolean x = true;
            try
            {
                switch (choice1) {
                    case 1:
                        while (x) {
                            System.out.println("\n\n\t\t\t\t\t\t\tAre you a ");
                            System.out.println("\n\t\t\t\t\t\t\t1. Student");
                            System.out.println("\n\t\t\t\t\t\t\t2. Faculty");
                            System.out.println("\n\t\t\t\t\t\t\t3.Guest\n");
                            int choice2 = s.nextInt();
                            switch (choice2) {
                                case 1:
                                    x = false;
                                    student = new Student();
                                    student.get_details();
                                    student.register();
                                    nextactivity(student.user_id);
                                    break;

                                case 2:
                                    x = false;
                                    faculty = new Faculty();
                                    faculty.get_details();
                                    faculty.register();
                                    break;

                                case 3:
                                    x = false;
                                    user = new User();
                                    user.get_details();
                                    user.register();
                                    break;

                                default:
                                    x = true;
                                    System.out.println("\nWrong Option Please Select Again\n");
                                    break;
                            }

                        }
                    case 2:
                        Scanner sk = new Scanner(System.in);
                        System.out.println("Enter User_id");
                        String user_id = sk.nextLine();
                        System.out.println("Enter Password");
                        String password = sk.nextLine();

                        boolean query = User.login(user_id, password);
                        if (query) {
                            System.out.println("\nLogin Successful\n");
                            nextactivity(user_id);
                        } else {
                            System.out.println("\nLogin Insuccessful\n");
                        }
                        break;

                    case 3:
                        System.exit(0);


                }
            }
            catch (InputMismatchException e)
            {
                e.printStackTrace();
            }



        } while (true);
    }

    public static void nextactivity(String user_id) throws IOException {
        while (TRUE) {
            Scanner s = new Scanner(System.in);
            String name = null;
            System.out.println("\n\n\t\t\tUser Please select one of the options");
            System.out.println("\t\t\t\t1. Upload \n\t\t\t\t2. Search \n\t\t\t\t3. Delete \n\t\t\t\t4. Logout ");
            int n = s.nextInt();
            try
            {
                if (n == 1) {
                    CsvReader temp = new CsvReader("Users.csv");
                    temp.readHeaders();
                    while(temp.readRecord())
                    {
                        String x = temp.get("user_id");
                        if(x.equals(user_id))
                        {
                            name = temp.get("name");
                        }
                    }
                    Document document = new Document(name);
                    User.upload(document, name);
                    System.out.println("\n\n1. Press 1 to view uploaded file\n2. Back");
                    int a = s.nextInt();
                    switch (a) {
                        case 1:
                            User.view(document);

                    }
                }
                else if (n == 2)
                {
                    User.search();
                }
                else if (n == 3)
                {
                    User.delete();
                }
                else if (n == 4)
                {
                    User.logout();
                    break;
                }
                else {
                    System.out.println("\t\t\tWrong Input Please Try again");
                }
            }
            catch (InputMismatchException e)
            {
                e.printStackTrace();
            }


        }
    }

    private static String getname(String user_id) throws IOException {
        CsvReader login = new CsvReader("Users.csv");
        login.readHeaders();
        String z = null;
        boolean result = false;
        while (login.readRecord())
        {
            String x = login.get("user_id");
            if (x.equals(user_id)) {
                z = login.get("name");
                break;
            }
        }
        login.close();
        return z;
    }
}

