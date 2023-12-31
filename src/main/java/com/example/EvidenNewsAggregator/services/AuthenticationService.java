package com.example.EvidenNewsAggregator.services;

import com.example.EvidenNewsAggregator.auth.AuthenticationRequest;
import com.example.EvidenNewsAggregator.auth.AuthenticationResponse;
import com.example.EvidenNewsAggregator.auth.RegisterRequest;
import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import com.example.EvidenNewsAggregator.jwt.JwtService;
import com.example.EvidenNewsAggregator.user.UserDetailServiceImp;
import com.example.EvidenNewsAggregator.services.UserService;
import com.example.EvidenNewsAggregator.validations.RegisterValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])([A-Za-z\\d@#$%^&+=!]{10,})$";

    @Autowired
    private RegisterValidation registerValidation;

    public AuthenticationResponse register(RegisterRequest request) {


        Users newUser = new Users();

        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(encodedPassword);

        newUser.setRoleId(1);

        // Save the new user to the database
        userService.add(newUser);

        // Authenticate the new user
        UserDetails userDetails = userDetailServiceImp.loadUserByUsername(newUser.getUsername());

        // Generate a JWT token for the new user
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userDetailServiceImp.loadUserByUsername(request.getUsername());
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
