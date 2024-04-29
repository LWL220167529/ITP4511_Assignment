package ict.run;

import java.io.IOException;

import ict.db.CheckOutDB;
import ict.db.Database;

public class CreateCheckOut {
    public static void main(String[] args) throws IOException {
        CheckOutDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false"; 
        String dbUser = "root"; 
        String dbPassword = "root"; 
        Database database = new Database(dbUrl, dbUser, dbPassword);

        db = new CheckOutDB(database);
        System.out.println("Creating CheckOut table");
        db.createCheckOutTable();
        System.out.println("Checkout table created");
    }
}
