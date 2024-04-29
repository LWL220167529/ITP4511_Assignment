package ict.run;

import ict.db.CheckOutDB;
import ict.db.Database;

public class CreateCheckOut {
    public static void main(String[] args) {
        
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "";


        
        CheckOutDB db = new CheckOutDB(dbUrl, dbUser, dbPassword);

        try {
            System.out.println("Creating CheckOut table");
            boolean isCreated = db.createCheckOutTable();
            if (isCreated) {
                System.out.println("CheckOut table created successfully");
            } else {
                System.out.println("Failed to create CheckOut table");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
