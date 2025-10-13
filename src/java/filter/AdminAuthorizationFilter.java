package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.AuthUtils;

@WebFilter(filterName = "AdminAuthorizationFilter", urlPatterns = {
    "/formUser.jsp",
    "/formProduct.jsp",
    "/formCategory.jsp",
    "/Table.jsp"
})
public class AdminAuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AdminAuthorizationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Kiểm tra quyền Admin
        if (!AuthUtils.isAdmin(httpRequest)) {
            httpRequest.setAttribute("errorMessage", 
                "You don't have permission to access this page. Admin access required.");
            httpRequest.getRequestDispatcher("/welcome.jsp").forward(request, response);
            return;
        }
        
        // Cho phép tiếp tục
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("AdminAuthorizationFilter destroyed");
    }
}