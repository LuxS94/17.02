package org.example._7_02.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example._7_02.entities.User;
import org.example._7_02.exceptions.UnauthorizedException;
import org.example._7_02.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTCheckerFilter extends OncePerRequestFilter {
    private final JWTTools jwtTools;
    private final UserService us;

    @Autowired
    public JWTCheckerFilter(JWTTools jwtTools, UserService us) {
        this.jwtTools = jwtTools;
        this.us = us;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userHeader = request.getHeader("Authorization");
        if (userHeader == null || !userHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nel formato corretto");
        String Token = userHeader.replace("Bearer ", "");
        jwtTools.verifyToken(Token);
        filterChain.doFilter(request, response);


        String userid = jwtTools.extractIdFromToken(Token);
        User authenticatedUser = this.us.findById(userid);
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return new AntPathMatcher().match("/user/**", request.getServletPath());
//
//    }
}

