package com.company;

import java.io.IOException;

public class Student extends User
{
    protected int sem;
    protected String dept;
    protected String roll_no;

    @Override
    public void get_details() {
        super.get_details();
        System.out.println("Enter your Roll Number\n");
        roll_no = s.nextLine();
        System.out.println("Enter your semester\n");
        sem = s.nextInt();
        System.out.println("Enter your Department\n");
        dept = s.nextLine();
    }

    @Override
    public void register() throws IOException {
        Student temp = this;
        Admin admin = new Admin(temp);
    }
}

