package ict.run;

import ict.db.DeviceDB;

public class CreateDeviceTable {
    public static void main(String[] args) {
        DeviceDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        db = new DeviceDB(dbUrl, dbUser, dbPassword);

        db.createDeviceTable();

        System.out.println("Creating device table");
    }
}
