package com.auth.auth.aspects;

import com.auth.auth.annotations.RequireRole;
import com.auth.auth.services.AuthService;
import com.auth.auth.utility.JwtUtil;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public aspect AuthenticationAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    pointcut restControllerMethods(RequireRole requireRole):
            execution(@RequireRole * *(..)) && @annotation(requireRole);

    Object around(RequireRole requireRole): restControllerMethods(requireRole){
        Set<String> allowedRoles = new HashSet<>(Arrays.asList(requireRole.value()));
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try{
                    jwtUtil.extractAllClaims(token);
                } catch (Exception e) {
                    return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
                }

                String username = jwtUtil.getUsernameFromToken(token);
                UserDetails userDetails = authService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);


                Set<String> userRoles = userDetails.getAuthorities().stream()
                        .map(auth -> auth.getAuthority().substring(5))
                        .collect(Collectors.toSet());

                if (allowedRoles.stream().count() > 0 && !allowedRoles.stream().anyMatch(userRoles::contains)){
                    return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
                }
            } else {
                System.out.println("No valid Authorization header found");
            }

            // Log request details (optional)
            System.out.println("Request URI: " + request.getRequestURI());
            System.out.println("HTTP Method: " + request.getMethod());
        } else {
            System.out.println("Could not retrieve HTTP request information");
        }

        return proceed(requireRole);
    }



}
