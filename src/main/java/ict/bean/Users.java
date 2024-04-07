/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Users {
    private List<User> users;

    public Users() {
        users = new ArrayList<User>();
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getList() {
        return users;
    }
}
