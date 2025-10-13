/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserDTO;
import utils.AuthUtils;

/**
 *
 * @author Admin
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {
    //    "/UserController", 
    "/ProductController",
    "/CategoryController",
    "/OrderController",
    "/CartController",
    "/welcome.jsp",
    "/header.jsp",
    "/footer.jsp",
    "/verifyAcount.jsp",
    "/newPassword.jsp",
    "/detailOfProduct.jsp"

})

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());

        // Kiểm tra đăng nhập
        if (!AuthUtils.isLoggedIn(httpRequest)) {
            System.out.println("FILTER: User not logged in. Redirecting to login.jsp");
            httpResponse.sendRedirect(contextPath + "/login.jsp");
            return;
        } else {
            System.out.println("FILTER: User is logged in. Proceed to " + path);
        }

        // Cho phép tiếp tục
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("AuthenticationFilter destroyed");
    }

}
