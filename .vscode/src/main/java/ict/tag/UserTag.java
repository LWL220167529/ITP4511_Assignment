/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tag;

import ict.bean.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author User
 */
public class UserTag extends SimpleTagSupport {
    private String tagType;
    private List<User> users;

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            if ("simple".equalsIgnoreCase(tagType)) {
                
            } else if ("table".equalsIgnoreCase(tagType)) {
                // display the list format
                for (User user : users) {
                    out.println("<tr>" +
                    "<td>" + user.getUsername() + "</td>" +
                    "<td>" + user.getPhone() + "</td>" +
                    "<td>" + user.getEmail() + "</td>" +
                    "<td>" + ("courier".equalsIgnoreCase(user.getRole()) ? "" : setCampus(user.getCampus())) + "</td>" +
                    "<td>" + user.getRole() + "</td>" +
                    "<td><form method=\"post\" action=\"User\">" +
                    "<input type=\"hidden\" name=\"action\" value=\"edit\">" +
                    "<input type=\"hidden\" name=\"id\" value=\"" + user.getId() + "\">" +
                    "<input type=\"submit\" value=\"Edit\">" +
                    "</form></td>" +
                    "</tr>");
                }
            } else {
                out.println("No such type");
            }
        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }
    
    public String setCampus(String campus) {
        if ("CW".equals(campus)) {
            return "Chai Wan (CW)";
        } else if ("LWL".equals(campus)) {
            return "Lee Wai Lee (LWL)";
        } else if ("ST".equals(campus)) {
            return "Sha Tin (ST)";
        } else if ("TM".equals(campus)) {
            return "Tuen Mun (TM)";
        } else if ("TY".equals(campus)) {
            return "Tsing Yi (TY)";
        } else {
            return "Unknown";
        }
    }
}
