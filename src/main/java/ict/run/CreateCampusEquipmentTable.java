package ict.run;

import ict.db.CampusEquipmentDB;

public class CreateCampusEquipmentTable {
    
    public static void main(String[] args) {
        CampusEquipmentDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "";

        db = new CampusEquipmentDB(dbUrl, dbUser, dbPassword);

        db.createCampusEquipmentTable();

        System.out.println("Creating campus device table");
    }
}
