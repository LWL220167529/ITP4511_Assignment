package ict.run;

import ict.db.CampusDB;
import ict.db.Database;

public class CreateCampus {
    public static void main(String[] args) {
        CampusDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        Database database = new Database(dbUrl, dbUser, dbPassword);

        db = new CampusDB(database);

        db.createCampusTable();

        System.out.println("Created campus table");
    }
    
}
