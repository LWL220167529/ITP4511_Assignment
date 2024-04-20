package ict.run;

import ict.db.EquipmentDB;

public class updateEquipmentImage {
    public static void main(String[] args) {
        EquipmentDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "";

        db = new EquipmentDB(dbUrl, dbUser, dbPassword);
        
        String [] Equipment = {
            "desktop computers",
            "laptops",
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

        for (String device : Equipment) {
            db.updateEquipmentImageByName(device, device + ".jpg");
        }
    }
}
