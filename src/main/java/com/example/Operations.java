package com.example;

import java.util.Scanner;

public class Operations {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Initialization initial = new Initialization();
        String operation = "o";
        int id=0;
        String name ="";
        while (operation!=null) {
            System.out.println("Want to read, update, insert, or delete lines from the database?");
            System.out.println(
                    "Enter u for Update, i for Insert, d for Delete and r for Read to perform respective operations, to stop the simulation enter s ");
            operation = scan.nextLine();
            if (operation.equalsIgnoreCase("r")) {
                initial.readDataBase();
            } else if (operation.equalsIgnoreCase("u")) {
                System.out.println("Enter id to update name");
                id = scan.nextInt();
                System.out.println("Enter name to update in respective id");
                scan.nextLine();
                name = scan.nextLine();
                initial.updateDataBase(id,name);
            } else if (operation.equalsIgnoreCase("d")) {
                System.out.println("Enter ID number to delete entry");
                id = scan.nextInt();
                initial.deleteInDataBase(id);
            } else if (operation.equalsIgnoreCase("i")) {
                initial.insertIntoDataBase();
            }else if(operation.equalsIgnoreCase("s")){
                //operation =null;
                break;
            }
        }
        scan.close();

    }
}