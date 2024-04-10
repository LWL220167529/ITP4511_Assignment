package ict.db;

import java.io.IOException;
import java.sql.Statement;

import ict.bean.Campus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CampusDB {
    
    private String dburl;
    private String dbUser;
    private String dbPassword;

    public CampusDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }
    
    public void createCampusTable() {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "create table if not exists campus ("
                    + "Id varchar(25) not null,"
                    + "address varchar(225) not null,"
                    + "primary key (Id)"
                    + ")";
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addCampus(Campus campus) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO campus (Id, address) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, campus.getId());
            pstmt.setString(2, campus.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
