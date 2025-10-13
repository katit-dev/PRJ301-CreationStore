/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DbUtils;
import utils.PasswordUtils;

/**
 *
 * @author Admin
 */
public class UserDAO {

    public UserDAO() {
    }

    

    /**
     *
     */
    public boolean updatePassword(int userid, String newPassword) {
        boolean success = false;
        String sql = "UPDATE Users SET password = ? WHERE UserId = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setInt(2, userid);
            int rowAffected = ps.executeUpdate();
            success = (rowAffected > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Login
     */
    public boolean login(String username, String password) {
        try {
            UserDTO user = getUserByUserName(username);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Get user by username
     */
    public UserDTO getUserByUserName(String username) {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("UserId");
                String userName = rs.getString("Username");
                String password = rs.getString("Password");
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String role = rs.getString("Role");
                Date date = rs.getDate("CreatedAt");

                return new UserDTO(userId, userName, password, fullName, email, role, date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * list user by usernaem
     */
    public List<UserDTO> listUserByName(String username) {
        List<UserDTO> userList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT UserId, Username, Password, FullName, Email, Role, CreatedAt FROM Users WHERE Username LIKE ?";

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(sql);
            String searchKeyword = "%" + username + "%";
            ps.setString(1, searchKeyword);
            rs = ps.executeQuery();

            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getInt("UserId"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setFullName(rs.getString("FullName"));
                user.setEmail(rs.getString("Email"));
                user.setRole(rs.getString("Role"));
                user.setCreatedAt(rs.getTimestamp("CreatedAt"));

                userList.add(user);
            }
        } catch (Exception e) {
            System.err.println("Error in listUserByName(): " + e.getMessage());
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * Get user by id
     */
    public UserDTO getUserByUserId(int userid) {
        String sql = "SELECT * FROM Users WHERE UserId = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("UserId");
                String userName = rs.getString("Username");
                String password = rs.getString("Password");
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String role = rs.getString("Role");
                Date date = rs.getDate("CreatedAt");

                return new UserDTO(userId, userName, password, fullName, email, role, date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Register
     */
    public boolean register(UserDTO user) {
        boolean success = false;
        String sql = "INSERT INTO Users (Username, Password, FullName, Email) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmail());
//            ps.setString(5, user.getRole());
//           ps.setDate(6, new java.sql.Date(user.getCreatedAt().getTime()));

            int rowAffected = ps.executeUpdate();
            success = (rowAffected > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Get User by email
     */
    public UserDTO getUserByEmail(String userEmail) {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String userName = rs.getString("Username");
                String password = rs.getString("Password");
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String role = rs.getString("Role");
                Date date = rs.getDate("CreatedAt");

                return new UserDTO(userName, password, fullName, email, role, date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * check is existing
     */
    public boolean isUserExistingByUserName(String username) {
        return getUserByUserName(username) != null;
    }

    public boolean isUserExistingByEmail(String userEmail) {
        return getUserByEmail(userEmail) != null;
    }

    /**
     * Get all users
     */
    public List<UserDTO> getAllUser() {
        List<UserDTO> allUserList = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getInt("UserId"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password")); // Chỉ dùng khi bạn cần, có thể loại bỏ nếu không cần hiển thị password
                user.setFullName(rs.getString("FullName"));
                user.setEmail(rs.getString("Email"));
                user.setRole(rs.getString("Role"));
                user.setCreatedAt(rs.getDate("CreatedAt"));

                allUserList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUserList;

    }

    /**
     * update user
     */
    public boolean updateUser(UserDTO user) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE Users SET Username = ?, Password = ?, FullName = ?, Email = ?, Role = ?, CreatedAt = ? WHERE UserId = ?";

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole());
            ps.setDate(6, new java.sql.Date(user.getCreatedAt().getTime()));
            ps.setInt(7, user.getUserId());

            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;

        } catch (Exception e) {
            System.err.println("Error in updateUser(): " + e.getMessage());
            e.printStackTrace();
        } 

        return success;
    }

    /**
     * Update user profile
     */
    public boolean updateProfile(int userid, String username, String fullname, String email) {
        boolean success = false;
        String sql = "UPDATE Users SET Username = ?, FullName = ? , Email = ? WHERE UserId = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, fullname);
            ps.setString(3, email);
            ps.setInt(4, userid);
            int rowAffected = ps.executeUpdate();
            success = (rowAffected > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
    
}
