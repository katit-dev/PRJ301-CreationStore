/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.UserDTO;

/**
 *
 * @author Admin
 */
public class AuthUtils {
    
    /**
     * Get current user
     */
   public static UserDTO getCurrentUser(HttpServletRequest request){
       HttpSession session = request.getSession();
       if(session != null){
           return (UserDTO)session.getAttribute("user");// duoc gui tu UserController.logn
       }
       return null;
   }
   
   /**
    * check is logged or not
    */
   public static boolean isLoggedIn(HttpServletRequest request){
       return getCurrentUser(request) != null;
   }
   
   /**
    * check role of user
    */
   public static boolean hasRole(HttpServletRequest request, String role){
       UserDTO user = getCurrentUser(request);
       if(user != null){
           String userRole = user.getRole();
           return userRole.equals(role);
       }
       return false;
   }
   
   /**
    * is Admin check
    */
   
   public static boolean isAdmin(HttpServletRequest request){
       return hasRole(request, "admin");
   }
    
   /**
    * 
    */
   public static String getAcessDeniedMessage(String action){
       return "You don't have permission to " + action + ". Please contact founder.";
   }
}
