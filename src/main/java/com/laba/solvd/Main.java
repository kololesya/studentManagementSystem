package com.laba.solvd;

import com.laba.solvd.exceptions.CourseNotFoundException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CourseNotFoundException {

        Scanner scanner = new Scanner(System.in);  // Create an object for input data
        AdministratorInterface adminInterface = new AdministratorInterface(scanner);  //create an admin interface
        adminInterface.run();  // Run program
    }
}
