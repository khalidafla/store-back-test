package ma.alten.store.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.alten.store.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ProductManagementFilter extends OncePerRequestFilter {

    @Value("${admin.email}")
    private String adminEmail;

    private final UserService userService;

    public ProductManagementFilter(UserService userService) {
        this.userService = userService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/products")) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not authenticated");
                return;
            }

            var user = userService.findByUsername(authentication.getName());
            if (!adminEmail.equals(user.getEmail())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to manage products");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
