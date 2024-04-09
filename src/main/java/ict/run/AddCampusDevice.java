package ict.run;

import ict.bean.CampusDevice;
import ict.db.CampusDeviceDB;
import ict.db.DeviceDB;

import java.util.Random;

public class AddCampusDevice {
    private final static String[] defaultCampus = { "CW", "LWL", "ST", "TM", "TY" };

    public static void main(String[] args) {
        CampusDeviceDB cddb;
        DeviceDB ddb;
        Random random = new Random();
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        cddb = new CampusDeviceDB(dbUrl, dbUser, dbPassword);

        ddb = new DeviceDB(dbUrl, dbUser, dbPassword);

        int [] deviceIds;

        deviceIds = ddb.getAllDeviceIds();

        for (String campus : defaultCampus) {
            for (int deviceId : deviceIds) {
                CampusDevice campusDevice = new CampusDevice(campus, deviceId, random.nextInt(11), "available");
                if (cddb.addCampusDevice(campusDevice)) {
                    System.out.println("Campus Device " + campus + " added.");
                } else {
                    System.out.println("Campus Device " + campus + " exists.");
                }
            }
        }

        cddb.updateStatusForQuantityLessThanOne();

        System.out.println("addCampusDevice() done.");
    }
}
