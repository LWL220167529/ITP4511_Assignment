/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.io.IOException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author User
 */
public class UserDB {
    
    private String dburl;
    private String dbUser;
    private String dbPassword;

    public UserDB(String dburl, String dbUser, String dbPassword) {
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

    public boolean isValidUser(String user, String pwd) {
        boolean isValid = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        String preQueryStatement = "SELECT * FROM user WHERE "
                + "username=? and password=?";
        // 2. get the prepare Statement
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(preQueryStatement);
            // 3. update the placeholders with username and pwd
            stmt.setString(1, user);
            stmt.setString(2, pwd);
            // 4. execute the query and assign to the result
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }

            return isValid;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
            return isValid;
        } catch (IOException ex) {
            ex.printStackTrace();
            return isValid;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                while (ex != null) {
                    ex.printStackTrace();
                    ex = ex.getNextException();
                }
            }
        }
    }

    public void CreateUserTable() {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "create table if not exists user ("
                    + "Id int not null auto_increment,"
                    + "username varchar(25) not null,"
                    + "password varchar(25) not null,"
                    + "phone varchar(25),"
                    + "email varchar(25),"
                    + "role varchar(25) not null,"
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

    public boolean addUserInfo(String id, String user, String pwd) {
        boolean added = false;
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("insert into user (ID, USERNAME, PASSWORD)"
            + " values (?,?,?)");
            stmt.setString(1, id);
            stmt.setString(2, user);
            stmt.setString(3, pwd);
            int rowCount = stmt.executeUpdate();

            if (rowCount >= 1 ) {
                added = true;
            }
            
            return added;
        }catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
            return added;
        } catch (IOException ex) {
            ex.printStackTrace();
            return added;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                while (ex != null) {
                    ex.printStackTrace();
                    ex = ex.getNextException();
                }
            }
        }
    }
}
