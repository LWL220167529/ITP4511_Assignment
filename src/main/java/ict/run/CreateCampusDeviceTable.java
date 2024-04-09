package ict.run;

import ict.db.CampusDeviceDB;

public class CreateCampusDeviceTable {
    
    public static void main(String[] args) {
        CampusDeviceDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        db = new CampusDeviceDB(dbUrl, dbUser, dbPassword);

        db.createCampusDeviceTable();

        System.out.println("Creating campus device table");
    }
}
