package ict.run;

import ict.db.CampusEquipmentDB;
import ict.db.Database;

public class CreateCampusEquipmentTable {
    
    public static void main(String[] args) {
        CampusEquipmentDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        Database database = new Database(dbUrl, dbUser, dbPassword);

        db = new CampusEquipmentDB(database);

        db.createCampusEquipmentTable();

        System.out.println("Creating campus device table");
    }
}
