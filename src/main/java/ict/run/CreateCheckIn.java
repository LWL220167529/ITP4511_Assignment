/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.run;
import java.io.IOException;

import ict.db.CheckInDB;

/**
 *
 * @author User
 */
public class CreateCheckIn {
    public static void main(String[] args) throws IOException {
        CheckInDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false"; 
        String dbUser = "root"; 
        String dbPassword = ""; 

        db = new CheckInDB(dbUrl, dbUser, dbPassword);
        System.out.println("Creating CheckOut table");
        db.createCheckInTable();
        System.out.println("CheckIn table created");
    }
}
