package com.laba.solvd;

import com.laba.solvd.exceptions.CourseNotFoundException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CourseNotFoundException {

        // Create an object for input data
        Scanner scanner = new Scanner(System.in);
        //create an admin interface
        AdministratorInterface adminInterface = new AdministratorInterface(scanner);
        // Run program
        adminInterface.run();
    }
}
