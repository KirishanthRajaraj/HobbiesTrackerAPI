package com.kiri.hobby_tracker.Config;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kiri.hobby_tracker.Model.User;
import com.kiri.hobby_tracker.Service.JwtUtilHelper;
import com.kiri.hobby_tracker.Service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtilHelper jwtUtil;
    private final UserService userService;

    public JwtAuthFilter(JwtUtilHelper jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get the Authorization header
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        // 2. Check if it starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    username = jwtUtil.extractUsername(token);
                    break;
                }
            }
        }

        // 3. If we got a username and no authentication is set yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Validate the token
            if (jwtUtil.isTokenValid(token)) {

                // Load user details from DB
                User user = userService.findByUsername(username).orElse(null);

                if (user != null) {
                    // Convert roles (Set<String>) into a list of GrantedAuthority
                    Collection<GrantedAuthority> authorities = user.getRoles().stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    // Create authentication token
                    UsernamePasswordAuthenticationToken authToken
                            = new UsernamePasswordAuthenticationToken(username, null, authorities);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Store in SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // 4. Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
