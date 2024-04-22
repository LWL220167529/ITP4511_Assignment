package ict.run;

import ict.db.Database;
import ict.db.ReserveDB;

public class CreateReserve {
    public static void main(String[] args) {
        ReserveDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false";
        String dbUser = "root";
        String dbPassword = "root";

        Database database = new Database(dbUrl, dbUser, dbPassword);

        db = new ReserveDB(database);
        
        db.createReserveTable();

        System.out.println("Creating reserve table");
    }
    
}
