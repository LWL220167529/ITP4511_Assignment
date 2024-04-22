package ict.db;

import java.io.IOException;
import java.sql.Statement;

import ict.bean.Campus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeliveryDB {
    
    private Database db;

    public DeliveryDB(Database db) {
        this.db = db;
    }

    public Connection getConnection() throws SQLException, IOException {
        return db.getConnection();
    }
    
}
