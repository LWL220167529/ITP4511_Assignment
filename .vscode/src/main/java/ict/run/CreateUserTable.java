/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.run;
import ict.db.UserDB;

/**
 *
 * @author User
 */
public class CreateUserTable {
    public static void main(String[] args) {
        UserDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false"; 
        String dbUser = "root"; 
        String dbPassword = "root"; 

        db = new UserDB(dbUrl, dbUser, dbPassword);
        System.out.println("Creating user table");
        db.createUserTable();
        System.out.println("User table created");
    }
}
