/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author User
 */
public class User {
    private final String[] defaultCampus = { "CW", "LWL", "ST", "TM", "TY" };
    private final String[] defaultRole = { "user", "technician", "staff", "courier", "admin" };

    private int id;
    private String username;
    private String email;
    private String phone;
    private String campus;
    private String role;

    public User() {
    }

    public User(String username, String email, String phone, String campus, String role) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.campus = campus;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCampus() {
        return campus;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCampus(String campus) {
        if (campus.equals(defaultCampus[0]) || campus.equals(defaultCampus[1]) || campus.equals(defaultCampus[2])
                || campus.equals(defaultCampus[3]) || campus.equals(defaultCampus[4])) {
            this.campus = campus;
        } else {
            throw new IllegalArgumentException("Invalid campus");
        }
    }

    public void setRole(String role) {
        if (role.equals(defaultRole[0]) || role.equals(defaultRole[1]) || role.equals(defaultRole[2])
                || role.equals(defaultRole[3]) || role.equals(defaultRole[4])) {
            this.role = role;
        } else {
            throw new IllegalArgumentException("Invalid role");
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" + "id=" + id + ", username=" + username + ", email=" + email + ", phone=" + phone + ", role=" + role + '}';
    }

    public boolean isNull() {
        return id == 0 && username == null && email == null && phone == null && role == null;
    }
}
