package ict.db;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ict.bean.CampusEquipment;
import ict.bean.UserReserve;
import ict.bean.WishEquipment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ReserveDB {
    private Database db;
    private final String selectCampusEquipmenSql = "SELECT r.id, r.user_id, r.equipment_id, r.quantity, " +
    "e.name, e.image, r.status, r.date ";
    private final String selectUserSql = "select r.id, r.user_id, u.username ";
    private final String selectCampusSql = "select r.id, r.belong_campus_id, cs.address as belong_campus_name, r.destination_campus_id, cs2.address as destination_campus_name ";
    private final String selectDUserSql = "select r.id, r.delivery_user_id, du.username as delivery_username ";
    private final String fromSql = "FROM Reserve r ";
    private final String joinCampusEquipmentSql = "JOIN campus_equipment c ON r.equipment_id = c.id ";
    private final String joinEquipmentSql = "JOIN equipment e ON c.equipment_id = e.id ";
    private final String joinUserSql = "JOIN user u ON u.id = r.user_id ";
    private final String joinCampusSql = "JOIN campus cs ON cs.id = r.belong_campus_id " +
    "JOIN campus cs2 ON  cs2.id = destination_campus_id ";
    private final String joinDUserSql = "JOIN user du ON du.id = r.delivery_user_id ";

    public ReserveDB(Database db) {
        this.db = db;
    }

    private Connection getConnection() throws SQLException, IOException {
        return db.getConnection();
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

    public UserReserve getUserReserveById(int id) {
        UserReserve reserve = new UserReserve();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM Reserve";
            String whereSql = " WHERE id = ?";
            pstmt = conn.prepareStatement(sql+whereSql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                reserve.setId(rs.getInt("id"));
                reserve.setUserId(rs.getInt("user_id"));
                reserve.setEquipmentId(rs.getInt("equipment_id"));
                reserve.setBelongCampusId(rs.getString("belong_campus_id"));
                reserve.setDestinationCampusId(rs.getString("destination_campus_id"));
                reserve.setQuantity(rs.getInt("quantity"));
                reserve.setStatus(rs.getString("status"));
                reserve.setDate(rs.getDate("date"));
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
        return reserve;
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
        ResultSet[] rs = new ResultSet[4];
        boolean isNext = true;
        try {
            conn = getConnection();
            String[] sqls = {
                    selectCampusEquipmenSql + fromSql + joinCampusEquipmentSql + joinEquipmentSql,
                    selectUserSql + fromSql + joinUserSql,
                    selectCampusSql + fromSql + joinCampusSql,
                    selectDUserSql + fromSql + joinDUserSql
            };
            for (int i = 0; i < rs.length; i++) {
                pstmt = conn.prepareStatement(sqls[i]);
                rs[i] = pstmt.executeQuery();
            }
            rs[3].next();
            while (rs[0].next() && rs[1].next() && rs[2].next()) {
                if (isNext && rs[0].getInt("id") == rs[3].getInt("id")) {
                    reserves.add(setReserves(rs[0], rs[1], rs[2], rs[3]));
                    isNext = rs[3].next();
                } else {
                    reserves.add(setReserves(rs[0], rs[1], rs[2], null));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs[0].close();
                    rs[1].close();
                    rs[2].close();

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

    /**
     * Retrieves a list of WishEquipment objects based on the specified date and
     * campus.
     *
     * @param date   The date to filter the reserves by.
     * @param campus The campus to filter the reserves by.
     * @return A list of WishEquipment objects that match the specified date and
     *         campus.
     */
    public List<WishEquipment> getReservesByDateAndStatus(Date date, String campus) {
        List<WishEquipment> reserves = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rsReserve = null;
        ResultSet rsUser = null;
        ResultSet rsCampus = null;
        String whereSql = "WHERE r.date = ? AND r.status = 'pending' and r.belong_campus_id = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(
                    selectCampusEquipmenSql + fromSql + joinCampusEquipmentSql + joinEquipmentSql + whereSql);
            pstmt.setDate(1, new java.sql.Date(date.getTime()));
            pstmt.setString(2, campus);
            rsReserve = pstmt.executeQuery();
            String[] sqls = {
                    selectUserSql + fromSql + joinUserSql + whereSql,
                    selectCampusSql + fromSql + joinCampusSql + whereSql
            };
            pstmt = conn.prepareStatement(sqls[0]);
            pstmt.setDate(1, new java.sql.Date(date.getTime()));
            pstmt.setString(2, campus);
            rsUser = pstmt.executeQuery();
            pstmt = conn.prepareStatement(sqls[1]);
            pstmt.setDate(1, new java.sql.Date(date.getTime()));
            pstmt.setString(2, campus);
            rsCampus = pstmt.executeQuery();
            while (rsReserve.next() && rsUser.next() && rsCampus.next()) {
                reserves.add(setReserves(rsReserve, rsUser, rsCampus, null));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rsReserve != null && rsUser != null && rsCampus != null) {
                    rsReserve.close();
                    rsUser.close();
                    rsCampus.close();
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
        String whereSql = "WHERE r.user_id = ?";
        String[] sqls = {
                selectCampusEquipmenSql + fromSql + joinCampusEquipmentSql + joinEquipmentSql,
                selectUserSql + fromSql + joinUserSql,
                selectCampusSql + fromSql + joinCampusSql,
                selectDUserSql + fromSql + joinDUserSql
        };

        reserves = tryGetReserves(sqls, whereSql, userId);
        return reserves;
    }

    public WishEquipment getReserveById(int reserveId) {
        WishEquipment reserve = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet[] rs = new ResultSet[3];

        String whereSql = "WHERE r.id = ?";
        try {
            conn = getConnection();
            String[] sqls = {
                    selectCampusEquipmenSql + fromSql + joinCampusEquipmentSql + joinEquipmentSql,
                    selectUserSql + fromSql + joinUserSql,
                    selectCampusSql + fromSql + joinCampusSql
            };
            for (int i = 0; i < rs.length; i++) {
                pstmt = conn.prepareStatement(sqls[i] + whereSql);
                pstmt.setInt(1, reserveId);
                rs[i] = pstmt.executeQuery();
            }
            while (rs[0].next() && rs[1].next() && rs[2].next()) {
                reserve = setReserves(rs[0], rs[1], rs[2], null);
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
        String[] sqls = new String[4];
        ResultSet[] rs = new ResultSet[4];
        String sqlWhere = "WHERE r.status = ? AND r.user_id = ?";

        sqls[0] = selectCampusEquipmenSql + fromSql + joinCampusEquipmentSql + joinEquipmentSql;
        sqls[1] = selectUserSql + fromSql + joinUserSql;
        sqls[2] = selectCampusSql + fromSql + joinCampusSql;
        if (!status.equalsIgnoreCase("pending")) {
            sqls[3] = selectDUserSql + fromSql + joinDUserSql;
        }
        try {
            conn = getConnection();
            for (int i = 0; i < rs.length; i++) {
                if (sqls[i] == null) {
                    continue;
                }
                pstmt = conn.prepareStatement(sqls[i] + sqlWhere);
                pstmt.setString(1, status);
                pstmt.setInt(2, userId);
                rs[i] = pstmt.executeQuery();
            }
            while (rs[0].next() && rs[1].next() && rs[2].next()) {
                if (rs[3] != null && rs[3].next()) {
                }
                reserves.add(setReserves(rs[0], rs[1], rs[2], rs[3]));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            int loopLimit = (status.equalsIgnoreCase("pending")) ? 3 : 4;
            try {
                if (rs[0] != null && rs[1] != null && rs[2] != null || rs[3] != null) {
                    for (int i = 0; i < loopLimit; i++) {
                        rs[i].close();
                    }
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

    public List<WishEquipment> getReservesByStatusAndDeliveryUserId(String status, int deliveryUserId) {
        List<WishEquipment> reserves = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet[] rs = new ResultSet[4];
        String whereSql = "WHERE r.status = ? AND r.delivery_user_id = ?";
        try {
            conn = getConnection();
            String[] sqls = {
                    selectCampusEquipmenSql + fromSql + joinCampusEquipmentSql + joinEquipmentSql,
                    selectUserSql + fromSql + joinUserSql,
                    selectCampusSql + fromSql + joinCampusSql,
                    selectDUserSql + fromSql + joinDUserSql
            };
            for (int i = 0; i < rs.length; i++) {
                pstmt = conn.prepareStatement(sqls[i] + whereSql);
                pstmt.setInt(1, deliveryUserId);
                rs[i] = pstmt.executeQuery();
            }
            while (rs[0].next() && rs[1].next() && rs[2].next() && rs[3].next()) {
                reserves.add(setReserves(rs[0], rs[1], rs[2], rs[3]));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs[0] != null && rs[1] != null && rs[2] != null && rs[3] != null) {
                    for (int i = 0; i < rs.length; i++) {
                        rs[i].close();
                    }
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

    public List<WishEquipment> getReservesByDeliveryUserId(int deliveryUserId, String campus, String status) {
        List<WishEquipment> reserves = new ArrayList<>();
        String whereSql = "WHERE r.delivery_user_id = ? and r.date >= CURDATE() and r.belong_campus_id = ? and r.status = ?" + 
        " ORDER BY r.date ASC";
        String[] sqls = {
                selectCampusEquipmenSql + fromSql + joinCampusEquipmentSql + joinEquipmentSql,
                selectUserSql + fromSql + joinUserSql,
                selectCampusSql + fromSql + joinCampusSql,
                selectDUserSql + fromSql + joinDUserSql
        };
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet[] rs = new ResultSet[4];
        boolean isNext = true;
        try {
            conn = getConnection();
            for (int i = 0; i < rs.length; i++) {
                pstmt = conn.prepareStatement(sqls[i] + whereSql);
                pstmt.setInt(1, deliveryUserId);
                pstmt.setString(2, campus);
                pstmt.setString(3, status);
                rs[i] = pstmt.executeQuery();
            }
            rs[3].next();
            while (rs[0].next() && rs[1].next() && rs[2].next()) {
                if (isNext && rs[0].getInt("id") == rs[3].getInt("id")) {
                    reserves.add(setReserves(rs[0], rs[1], rs[2], rs[3]));
                    isNext = rs[3].next();
                } else {
                    reserves.add(setReserves(rs[0], rs[1], rs[2], null));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs[0] != null && rs[1] != null && rs[2] != null && rs[3] != null) {
                    for (int i = 0; i < rs.length; i++) {
                        rs[i].close();
                    }
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

    public List<WishEquipment> tryGetReserves(String[] sqls, String whereSql, int id) {
        List<WishEquipment> reserves = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet[] rs = new ResultSet[4];
        boolean isNext = true;
        try {
            conn = getConnection();
            for (int i = 0; i < rs.length; i++) {
                pstmt = conn.prepareStatement(sqls[i] + whereSql);
                pstmt.setInt(1, id);
                rs[i] = pstmt.executeQuery();
            }
            rs[3].next();
            while (rs[0].next() && rs[1].next() && rs[2].next()) {
                if (isNext && rs[0].getInt("id") == rs[3].getInt("id")) {
                    reserves.add(setReserves(rs[0], rs[1], rs[2], rs[3]));
                    isNext = rs[3].next();
                } else {
                    reserves.add(setReserves(rs[0], rs[1], rs[2], null));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs[0] != null && rs[1] != null && rs[2] != null && rs[3] != null) {
                    for (int i = 0; i < rs.length; i++) {
                        rs[i].close();
                    }
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
        CampusEquipmentDB equipmentDB = new CampusEquipmentDB(db);
        try {
            UserReserve userReserve = getUserReserveById(reserveId);
            CampusEquipment campusEquipment = equipmentDB.getCampusEquipmentById(userReserve.getEquipmentId());
            if (campusEquipment.getQuantity() < userReserve.getQuantity()) {
                return false;
            }
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

    public WishEquipment setReserves(ResultSet rsReserve, ResultSet rsUser, ResultSet rsCampus, ResultSet rsDelivery)
            throws SQLException {
        WishEquipment reserve = new WishEquipment();
        if (rsReserve != null) {
            // reserve id
            reserve.setId(rsReserve.getInt("id"));
            reserve.setQuantity(rsReserve.getInt("quantity"));
            reserve.setStatus(rsReserve.getString("status"));
            reserve.setDate(rsReserve.getDate("date"));
            // equipment details
            reserve.setEquipmentId(rsReserve.getInt("equipment_id"));
            reserve.setEquipmentName(rsReserve.getString("name"));
            reserve.setEquipmentImage(rsReserve.getString("image"));
        }
        if (rsUser != null) {
            // user details
            reserve.setUserId(rsUser.getInt("user_id"));
            reserve.setUsername(rsUser.getString("username"));
        }
        if (rsCampus != null) {
            // campus details
            reserve.setBelongCampusId(rsCampus.getString("belong_campus_id"));
            reserve.setBelongCampusName(rsCampus.getString("belong_campus_name"));
            reserve.setDestinationCampusId(rsCampus.getString("destination_campus_id"));
            reserve.setDestinationCampusName(rsCampus.getString("destination_campus_name"));
        }
        // delivery user details
        if (rsDelivery != null) {
            reserve.setDeliveryUserId(rsDelivery.getInt("delivery_user_id"));
            reserve.setDeliveryUsername(rsDelivery.getString("delivery_username"));
        }
        return reserve;
    }
}
