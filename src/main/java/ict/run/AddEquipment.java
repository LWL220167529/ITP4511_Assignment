package ict.run;

import ict.bean.Equipment;
import ict.db.Database;
import ict.db.EquipmentDB;

public class AddEquipment {
    public static void main(String[] args) {
        EquipmentDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        Database database = new Database(dbUrl, dbUser, dbPassword);

        db = new EquipmentDB(database);

        String [] Equipment = {
            "desktop computers",
            "laptops",
            "workstations",
            "routers",
            "switches",
            "firewalls",
            "wireless access points",
            "Printers ",
            "Scanners",
            "monitors",
            "keyboards",
            "mice",
            "cables",
            "cameras",
            "smartphones",
        };

        for (String name : Equipment) {
            Equipment device = new Equipment(name);
            if (db.addEquipment(device)) {
                System.out.println("Equipment " + name + " added.");
            } else {
                System.out.println("Equipment " + name + " exists.");
            }
        }

        System.out.println("addEquipment() done.");
    }
}
