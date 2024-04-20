package ict.run;

import ict.db.CampusDB;

public class CreateCampus {
    public static void main(String[] args) {
        CampusDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        db = new CampusDB(dbUrl, dbUser, dbPassword);

        db.createCampusTable();

        System.out.println("Created campus table");
    }
    
}
