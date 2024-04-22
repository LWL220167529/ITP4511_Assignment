/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ict.bean.User;
import ict.bean.Users;

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
    
    private Database db;

    public UserDB(Database db) {
        this.db = db;
    }

    public Connection getConnection() throws SQLException, IOException {

        return db.getConnection();
    }

    public List<User> getUsers(){
        List<User> User = null;
        Connection conn = null;
        Statement stmt = null;

        try {
            User = new ArrayList<>();
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "select * from user";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setCampus(rs.getString("campus"));
                user.setRole(rs.getString("role"));
                User.add(user);
            }
            rs.close();
            return User;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
            return User;
        } catch (IOException ex) {
            ex.printStackTrace();
            return User;
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

    public User getUserById(int id) {
        User user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("select * from user where id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setCampus(rs.getString("campus"));
                user.setRole(rs.getString("role"));
            }
            rs.close();
            return user;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
            return user;
        } catch (IOException ex) {
            ex.printStackTrace();
            return user;
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

    public String [] isValidUser(String user, String pwd) {
        String [] isValid = new String[2];
        isValid[0] = "false";
        Connection conn = null;
        PreparedStatement stmt = null;
        String preQueryStatement = "SELECT * FROM user WHERE "
                + "username=? and password=? or email=? and password=? or phone=? and password=?";
        // 2. get the prepare Statement
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(preQueryStatement);
            // 3. update the placeholders with username and pwd
            stmt.setString(1, user);
            stmt.setString(2, pwd);
            stmt.setString(3, user);
            stmt.setString(4, pwd);
            stmt.setString(5, user);
            stmt.setString(6, pwd);
            // 4. execute the query and assign to the result
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isValid[0] = "true";
                isValid[1] = String.valueOf(rs.getInt("id")); // Convert integer to string
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

    public void createUserTable() {
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
                    + "campus varchar(25) not null,"
                    + "role varchar(25) not null,"
                    + "primary key (Id)"
                    + "FOREIGN KEY (campus) REFERENCES campus(id))";
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

    public boolean addUser(User user, String pwd) {
        boolean added = false;
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            if (existsUser(user.getPhone(), user.getEmail())) {
                return added;
            }
            System.out.println(user.toString());
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO user (`username`, `password`, `phone`, `email`, `campus`, `role`)"
            + " values (?,?,?,?,?,?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, pwd);
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getCampus());
            stmt.setString(6, user.getRole());
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

    public boolean existsUser(String phone, String email) {
        boolean exists = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("select * from user where email = ? or phone = ?");
            stmt.setString(1, email);
            stmt.setString(2, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exists = true;
            }
            return exists;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
            return exists;
        } catch (IOException ex) {
            ex.printStackTrace();
            return exists;
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

    public boolean updateUser(User user) {
        boolean updated = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("update user set username = ?, phone = ?, email = ?, role = ?, campus = ? where id = ?");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, (user.getId() == 0) ? "admin" : user.getRole());
            stmt.setString(5, user.getCampus());
            stmt.setInt(6, user.getId());
            int rowCount = stmt.executeUpdate();
            if (rowCount >= 1) {
                updated = true;
            }
            return updated;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
            return updated;
        } catch (IOException ex) {
            ex.printStackTrace();
            return updated;
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

    public Users getCourier() {
        Users users = new Users();
        List<User> User = null;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            User = new ArrayList<>();
            conn = getConnection();
            String sql = "select * from user where role = 'courier';";
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setCampus(rs.getString("campus"));
                user.setRole(rs.getString("role"));
                User.add(user);
            }
            rs.close();
            users.setUsers(User);
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
                while (ex != null) {
                    ex.printStackTrace();
                    ex = ex.getNextException();
                }
            }
        }
        
        return users;
    }
}
