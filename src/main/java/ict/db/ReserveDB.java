package ict.db;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ict.bean.UserReserve;
import ict.bean.WishEquipment;
import oracle.net.aso.r;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    "delivery_user_id int," +
                    "quantity int not null," +
                    "status varchar(255) not null," +
                    "date date not null," +
                    "FOREIGN KEY (belong_campus_id) REFERENCES campus(Id), " +
                    "FOREIGN KEY (destination_campus_id) REFERENCES campus(Id), " +
                    "FOREIGN KEY (equipment_id) REFERENCES campus_equipment(id), " +
                    "FOREIGN KEY (user_id) REFERENCES user(id)," + // Added missing comma here
                    "FOREIGN KEY (delivery_user_id) REFERENCES user(id)" +
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
            String sql = "INSERT INTO Reserve (user_id, equipment_id, belong_campus_id, destination_campus_id, quantity, status, date) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reserve.getUserId());
            pstmt.setInt(2, reserve.getCampusEquipmentId());
            pstmt.setString(3, reserve.getBelongCampusId());
            pstmt.setString(4, reserve.getDestinationCampusId());
            pstmt.setInt(5, reserve.getQuantity());
            pstmt.setString(6, reserve.getStatus());
            pstmt.setDate(7, reserve.getDate());
            int rowCount = pstmt.executeUpdate();

            if (rowCount >= 1) {
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

    public List<WishEquipment> getReserves() {
        List<WishEquipment> reserves = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql1 = "SELECT r.id, r.user_id, u.username, r.equipment_id, r.quantity, c.campus as belong_campus_id, r.destination_campus_id, "
                +
                "r.status, e.name, e.image, r.date, cs.address as belong_campus_name, cs2.address as destination_campus_name "
                +
                "FROM Reserve r " +
                "JOIN campus_equipment c ON r.equipment_id = c.id " +
                "JOIN equipment e ON c.equipment_id = e.id " +
                "JOIN user u ON u.id = r.user_id " +
                "JOIN campus cs ON cs.id = c.campus " +
                "join campus cs2 ON  cs2.id = destination_campus_id where r.status = 'pending';";
        String sql2 = "SELECT r.id, r.user_id, u.username, r.equipment_id, r.delivery_user_id, du.username as delivery_username, r.quantity, c.campus as belong_campus_id, r.destination_campus_id, "
                +
                "r.status, e.name, e.image, r.date, cs.address as belong_campus_name, cs2.address as destination_campus_name "
                +
                "FROM Reserve r " +
                "JOIN campus_equipment c ON r.equipment_id = c.id " +
                "JOIN equipment e ON c.equipment_id = e.id " +
                "JOIN user u ON u.id = r.user_id " +
                "JOIN user du ON du.id = r.delivery_user_id " +
                "JOIN campus cs ON cs.id = c.campus " +
                "join campus cs2 ON  cs2.id = destination_campus_id ";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql1);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                reserves.add(setReserves(rs, false));
            }
            pstmt.close();
            pstmt = conn.prepareStatement(sql2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                reserves.add(setReserves(rs, true));
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

    public List<WishEquipment> getReservesByDateAndStatus(Date date, String campus) {
        List<WishEquipment> reserves = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT r.id, r.user_id, u.username, r.equipment_id, r.quantity, c.campus as belong_campus_id, r.destination_campus_id, "
                +
                "r.status, e.name, e.image, r.date, cs.address as belong_campus_name, cs2.address as destination_campus_name "
                +
                "FROM Reserve r " +
                "JOIN campus_equipment c ON r.equipment_id = c.id " +
                "JOIN equipment e ON c.equipment_id = e.id " +
                "JOIN user u ON u.id = r.user_id " +
                "JOIN campus cs ON cs.id = c.campus " +
                "JOIN campus cs2 ON  cs2.id = destination_campus_id " +
                "WHERE r.date = ? AND r.status = 'pending' and r.belong_campus_id = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(date.getTime()));
            pstmt.setString(2, campus);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                reserves.add(setReserves(rs, false));
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
        String sql1 = "SELECT r.id, r.user_id, u.username, r.equipment_id, r.quantity, c.campus as belong_campus_id, r.destination_campus_id, "
                +
                "r.status, e.name, e.image, r.date, cs.address as belong_campus_name, cs2.address as destination_campus_name "
                +
                "FROM Reserve r " +
                "JOIN campus_equipment c ON r.equipment_id = c.id " +
                "JOIN equipment e ON c.equipment_id = e.id " +
                "JOIN user u ON u.id = r.user_id " +
                "JOIN campus cs ON cs.id = c.campus " +
                "JOIN campus cs2 ON  cs2.id = destination_campus_id " +
                "WHERE r.status = 'pending' AND r.user_id = ?";
        String sql2 = "SELECT r.id, r.user_id, u.username, r.equipment_id, r.delivery_user_id, du.username as delivery_username, r.quantity, c.campus as belong_campus_id, r.destination_campus_id, "
                +
                "r.status, e.name, e.image, r.date, cs.address as belong_campus_name, cs2.address as destination_campus_name "
                +
                "FROM Reserve r " +
                "JOIN campus_equipment c ON r.equipment_id = c.id " +
                "JOIN equipment e ON c.equipment_id = e.id " +
                "JOIN user u ON u.id = r.user_id " +
                "JOIN user du ON du.id = r.delivery_user_id " +
                "JOIN campus cs ON cs.id = c.campus " +
                "join campus cs2 ON  cs2.id = destination_campus_id " +
                "where r.user_id = ?;";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql1);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                reserves.add(setReserves(rs, false));
            }
            pstmt.close();
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                reserves.add(setReserves(rs, true));
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

    public WishEquipment getReserveById(int reserveId) {
        WishEquipment reserve = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT r.id, r.user_id, u.username, r.equipment_id, r.quantity, c.campus as belong_campus_id, r.destination_campus_id, "
                +
                "r.status, e.name, e.image, r.date, cs.address as belong_campus_name, cs2.address as destination_campus_name "
                +
                "FROM Reserve r " +
                "JOIN campus_equipment c ON r.equipment_id = c.id " +
                "JOIN equipment e ON c.equipment_id = e.id " +
                "JOIN user u ON u.id = r.user_id " +
                "JOIN campus cs ON cs.id = c.campus " +
                "JOIN campus cs2 ON  cs2.id = destination_campus_id " +
                "WHERE r.id = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reserveId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                reserve = setReserves(rs, false);
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
        return reserve;
    }

    public List<WishEquipment> getReservesByStatusAndUserId(String status, int userId) {
        List<WishEquipment> reserves = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean delivery = false;
        String sqlSelect = "SELECT r.id, r.user_id, u.username, r.equipment_id, r.quantity, c.campus as belong_campus_id, r.destination_campus_id, "
                +
                "r.status, e.name, e.image, r.date, cs.address as belong_campus_name, cs2.address as destination_campus_name ";
        String sqlFrom = "FROM Reserve r ";
        String sqlJoin = "JOIN campus_equipment c ON r.equipment_id = c.id " +
                "JOIN equipment e ON c.equipment_id = e.id " +
                "JOIN user u ON u.id = r.user_id " +
                "JOIN campus cs ON cs.id = c.campus " +
                "JOIN campus cs2 ON  cs2.id = destination_campus_id ";
        String sqlWhere = "WHERE r.status = ? AND r.user_id = ?";

        String sql;
        if (status.equalsIgnoreCase("pending")) {
            sql = sqlSelect + sqlFrom + sqlJoin + sqlWhere;
        } else {
            sql = sqlSelect + ", r.delivery_user_id, du.username as delivery_username " + sqlFrom + sqlJoin +
                    "JOIN user du ON du.id = r.delivery_user_id " + sqlWhere;
            delivery = true;
        }
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WishEquipment reserve = setReserves(rs, delivery);
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

    public List<Date> getPendingReserveDates() {
        List<Date> dates = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT r.date FROM Reserve r ";
        String sqlWhere = "WHERE r.status = 'pending' ";
        String sqlOrderBy = "ORDER BY r.date ASC";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql + sqlWhere + sqlOrderBy);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Date date = rs.getDate("date");
                dates.add(date);
            }
        } catch (SQLException | IOException ex) {
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
        return dates;
    }

    public boolean cancelReserve(int reserveId) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "UPDATE Reserve SET status = 'canceled' WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reserveId);
            if (pstmt.executeUpdate() > 0) {
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

    public boolean updateReserveStatus(int reserveId, int courierId, String newStatus) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "UPDATE Reserve SET status = ?, delivery_user_id = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, courierId);
            pstmt.setInt(3, reserveId);
            if (pstmt.executeUpdate() > 0) {
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

    public WishEquipment setReserves(ResultSet rs, boolean delivery) throws SQLException {
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
        // delivery user details
        if (delivery) {
            reserve.setDeliveryUserId(rs.getInt("delivery_user_id"));
            reserve.setDeliveryUsername(rs.getString("delivery_username"));
        }
        return reserve;
    }
}
