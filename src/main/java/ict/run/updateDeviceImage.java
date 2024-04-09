package ict.run;

import ict.bean.Device;
import ict.db.DeviceDB;

public class updateDeviceImage {
    public static void main(String[] args) {
        DeviceDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        db = new DeviceDB(dbUrl, dbUser, dbPassword);
        
        String [] Device = {
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

        for (String device : Device) {
            db.updateDeviceImageByName(device, device + ".jpg");
        }
    }
}
