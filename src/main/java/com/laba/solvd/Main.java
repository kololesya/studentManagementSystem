package com.laba.solvd;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Create an object for input data
        AdministratorInterface adminInterface = new AdministratorInterface(scanner);  //create an admin interface
        adminInterface.run();  // Run program
    }
}
