package ict.db;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ict.bean.UserReserve;
import ict.bean.WishEquipment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ReserveDB {
    private String dburl;
    private String dbUser;
    private String dbPassword;

    public ReserveDB(String dburl, String dbUser, String dbPassword) {
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
    
    public void createReserveTable() {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "create table if not exists Reserve (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "user_id int not null," +
                    "equipment_id int not null," +
                    "belong_campus_id varchar(25) not null," +
                    "destination_campus_id varchar(25) not null," +
                    "quantity int not null," +
                    "status varchar(255) not null," +
                    "date date not null," +
                    "FOREIGN KEY (belong_campus_id) REFERENCES campus(Id), " +
                    "FOREIGN KEY (destination_campus_id) REFERENCES campus(Id), " +
                    "FOREIGN KEY (equipment_id) REFERENCES campus_equipment(id), " +
                    "FOREIGN KEY (user_id) REFERENCES user(id)" +
                    ")";
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

    public boolean addReserve(UserReserve reserve) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO Reserve (user_id, equipment_id, belong_campus_id, destination_campus_id, quantity, status, date) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reserve.getUserId());
            pstmt.setInt(2, reserve.getEquipmentId());
            pstmt.setString(3, reserve.getBelongCampusId());
            pstmt.setString(4, reserve.getDestinationCampusId());
            pstmt.setInt(5, reserve.getQuantity());
            pstmt.setString(6, reserve.getStatus());
            pstmt.setDate(7, reserve.getDate());
            int rowCount = pstmt.executeUpdate();

            if (rowCount >= 1 ) {
                result = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
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
        return result;
    }

    public List<UserReserve> getAllReserves() {
        List<UserReserve> reserves = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM Reserve";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                UserReserve reserve = new UserReserve();
                reserve.setId(rs.getInt("id"));
                reserve.setUserId(rs.getInt("user_id"));
                reserve.setEquipmentId(rs.getInt("equipment_id"));
                reserve.setBelongCampusId(rs.getString("belong_campus_id"));
                reserve.setDestinationCampusId(rs.getString("destination_campus_id"));
                reserve.setQuantity(rs.getInt("quantity"));
                reserve.setStatus(rs.getString("status"));
                reserve.setDate(rs.getDate("date"));
                reserves.add(reserve);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
        return reserves;
    }

    public List<WishEquipment> getReservesByUserId(int userId) {
        List<WishEquipment> reserves = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT r.id, r.user_id, u.username, r.equipment_id, r.quantity, c.campus as belong_campus_id, r.destination_campus_id, " +
            "r.status, e.name, e.image, r.date, cs.address as belong_campus_name, cs2.address as destination_campus_name " +
            "FROM Reserve r " +
            "JOIN campus_equipment c ON r.equipment_id = c.id " +
            "JOIN equipment e ON c.equipment_id = e.id " +
            "JOIN user u ON u.id = r.user_id " +
            "JOIN campus cs ON cs.id = c.campus " +
            "join campus cs2 ON  cs2.id = destination_campus_id " +
            "where r.user_id = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WishEquipment reserve = new WishEquipment();
                // reserve id
                reserve.setId(rs.getInt("id"));
                reserve.setQuantity(rs.getInt("quantity"));
                reserve.setStatus(rs.getString("status"));
                reserve.setDate(rs.getDate("date"));
                // user details
                reserve.setUserId(rs.getInt("user_id"));
                reserve.setUsername(rs.getString("username"));
                // equipment details
                reserve.setEquipmentId(rs.getInt("equipment_id"));
                reserve.setEquipmentName(rs.getString("name"));
                reserve.setEquipmentImage(rs.getString("image"));
                // campus details
                reserve.setBelongCampusId(rs.getString("belong_campus_id"));
                reserve.setBelongCampusName(rs.getString("belong_campus_name"));
                reserve.setDestinationCampusId(rs.getString("destination_campus_id"));
                reserve.setDestinationCampusName(rs.getString("destination_campus_name"));
                reserves.add(reserve);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
        return reserves;
    }

    public void deleteReserve(int reserveId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "DELETE FROM Reserve WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reserveId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    public void updateReserveStatus(int reserveId, String newStatus) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "UPDATE Reserve SET status = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, reserveId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
