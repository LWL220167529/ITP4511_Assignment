package ict.run;
import ict.db.UserDB;
import ict.db.Database;

public class CreateCheckIn {
    public static void main(String[] args) {
        UserDB db;
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Assignment_DB?useSSL=false"; 
        String dbUser = "root"; 
        String dbPassword = "root"; 

        Database database = new Database(dbUrl, dbUser, dbPassword);

        db = new UserDB(database);
        System.out.println("Creating CheckOut table");
        db.createUserTable();
        System.out.println("CheckIn table created");
    }
}
