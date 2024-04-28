package ict.run;

import ict.db.CheckInDB;
import ict.db.Database;

public class CreateCheckIn {
    public static void main(String[] args) {
      
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";


        
        CheckInDB db = new CheckInDB(dbUrl, dbUser, dbPassword);

        try {
            System.out.println("Creating CheckIn table");
            boolean isCreated = db.createCheckInTable();
            if (isCreated) {
                System.out.println("CheckIn table created successfully");
            } else {
                System.out.println("Failed to create CheckIn table");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
