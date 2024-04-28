package ict.run;

import ict.db.EquipmentDB;

public class CreateEquipmentTable {
    public static void main(String[] args) {
        EquipmentDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        db = new EquipmentDB(dbUrl, dbUser, dbPassword);

        db.createEquipmentTable();

        System.out.println("Creating device table");
    }
}
