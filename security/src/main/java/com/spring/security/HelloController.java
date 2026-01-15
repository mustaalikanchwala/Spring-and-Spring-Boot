package com.spring.security;

import com.spring.security.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HelloController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    jwtUtils jwtUtils;

    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/admin/hello")
    public String adminHello(){
        return "Hello ADMIN";
    }

    @GetMapping("/user/hello")
    public String userHello(){
        return "Hello USER";
    }

    @PostMapping("/singin")
    public String login(@RequestBody LoginRequest request){
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "Cannot Authenthicate";
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtfromUsername(userDetails);
        return jwt;
    }
}
