package ict.run;

import ict.bean.Device;
import ict.db.DeviceDB;

public class AddDevice {
    public static void main(String[] args) {
        DeviceDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        db = new DeviceDB(dbUrl, dbUser, dbPassword);

        String [] Device = {
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

        for (String name : Device) {
            Device device = new Device(name);
            if (db.addDevice(device)) {
                System.out.println("Device " + name + " added.");
            } else {
                System.out.println("Device " + name + " exists.");
            }
        }

        System.out.println("addDevice() done.");
    }
}
