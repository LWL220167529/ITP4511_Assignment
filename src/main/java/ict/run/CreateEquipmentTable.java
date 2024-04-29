package ict.run;

import ict.db.Database;
import ict.db.EquipmentDB;

public class CreateEquipmentTable {
    public static void main(String[] args) {
        EquipmentDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "";

        Database database = new Database(dbUrl, dbUser, dbPassword);

        db = new EquipmentDB(database);

        db.createEquipmentTable();

        System.out.println("Creating device table");
    }
}
