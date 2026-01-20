//package com.employee.management.filter;
//
//import com.employee.management.enums.Role;
//import com.employee.management.util.JwtUtil;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class JwtFilter implements Filter {
//
//    private final JwtUtil jwtUtil;
//
//    public JwtFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public void doFilter(
//            ServletRequest request,
//            ServletResponse response,
//            FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest req=(HttpServletRequest) request;
//        HttpServletResponse res=(HttpServletResponse) response;
//
//        String path=req.getRequestURI();
//        String method=req.getMethod();
//
//        //public api
//        if (path.startsWith("/auth") || method.equals("GET")){
//            chain.doFilter(request,response);
//            return;
//        }
//
//        String header=req.getHeader("Authorization");
//        if (header == null || !header.startsWith("Bearer ")) {
//            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        String token=header.substring(7);
//
//        if (token.isBlank()){
//            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        if (!jwtUtil.isTokenValid(token)){
//            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        //private api
//        Claims claims= jwtUtil.extractClaims(token);
//        String roleStr=claims.get("role", String.class);
//        Role role = Role.valueOf(roleStr);
//
//        if (method.equals("POST") && role != Role.ADMIN) {
//            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }
//
//        if (method.equals("DELETE") && role != Role.ADMIN) {
//            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }
//
//        if (method.equals("PUT") && role != Role.ADMIN) {
//            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }
//
//        if (method.equals("PATCH") && role != Role.ADMIN) {
//            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }
//        chain.doFilter(request,response);
//    }
//}
