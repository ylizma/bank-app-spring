package com.ylizma.bankmanagement.controller;

import com.ylizma.bankmanagement.domain.UserDetails;
import com.ylizma.bankmanagement.model.AuthenticationResponse;
import com.ylizma.bankmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDetails userDetails) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUserName(), userDetails.getPassword())
            );
        } catch (Exception e) {
            throw new Exception(e);
        }
        final org.springframework.security.core.userdetails.UserDetails user = userDetailsService.loadUserByUsername(userDetails.getUserName());
        final String jwt = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
