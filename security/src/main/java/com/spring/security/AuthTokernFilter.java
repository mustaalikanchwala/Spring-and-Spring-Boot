package com.spring.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthTokernFilter extends OncePerRequestFilter {
    @Autowired
    private jwtUtils jwtUtils;

//    @Autowired
//    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            System.out.println("AUTH TOKEN CALLED");
        String jwt = getJwtToken(request);
        if(jwt != null && jwtUtils.validateJwt(jwt)){
            String userName = jwtUtils.getUserNamefromToken(jwt);
            Claims claims = jwtUtils.getAllClaims(jwt);
            List<String> roles = claims.get("roles", List.class);
            List<GrantedAuthority> authorities = roles.stream().map(role -> (GrantedAuthority)new SimpleGrantedAuthority(role)).toList();
//            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName,null , authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);
    }

    private String getJwtToken(HttpServletRequest request){
        String jwt = jwtUtils.getJwtFromHeader(request);
        return jwt;
    }
}
