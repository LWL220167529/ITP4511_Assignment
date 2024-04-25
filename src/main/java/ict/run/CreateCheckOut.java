/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.run;
import ict.db.Database;
import ict.db.UserDB;

/**
 *
 * @author User
 */
public class CreateCheckOut {
    public static void main(String[] args) {
        UserDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false"; 
        String dbUser = "root"; 
        String dbPassword = "root"; 

        Database database = new Database(dbUrl, dbUser, dbPassword);

        db = new UserDB(database);
        System.out.println("Creating CheckOut table");
        db.createUserTable();
        System.out.println("Checkout table created");
    }
}
