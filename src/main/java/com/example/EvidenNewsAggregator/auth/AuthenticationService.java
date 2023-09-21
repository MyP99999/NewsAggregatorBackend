package com.example.EvidenNewsAggregator.auth;

import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import com.example.EvidenNewsAggregator.jwt.JwtService;
import com.example.EvidenNewsAggregator.user.UserDetailServiceImp;
import com.example.EvidenNewsAggregator.user.UserRepository;
import com.example.EvidenNewsAggregator.validations.RegisterValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
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
        userRepository.add(newUser);

        // Authenticate the new user
        UserDetails userDetails = userDetailServiceImp.loadUserByUsername(newUser.getUsername());

        // Generate a JWT token for the new user
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }



//    public AuthenticationResponse register(RegisterRequest request) {
//        // Check if the user already exists
//        Users existingUser = userRepository.findByUsername(request.getUsername());
//        Users existingEmail = userRepository.findByEmail(request.getEmail());
//
//        if (existingUser != null) {
//            return AuthenticationResponse.error("User with the same username already exists.");
//        }
//        if (existingEmail != null) {
//            return AuthenticationResponse.error("User with the same email already exists.");
//        }
//        if (!request.getEmail().matches(EMAIL_REGEX)) {
//            return AuthenticationResponse.error("Invalid email format.");
//        }
////        if (!request.getPassword().matches(PASSWORD_REGEX)) {
////            return AuthenticationResponse.error("Invalid password format. Password must have at least 10 characters, including at least one letter, one number, and one special character.");
////        }
//
//        Users newUser = new Users();
//
//        newUser.setUsername(request.getUsername());
//        newUser.setEmail(request.getEmail());
//
//        String encodedPassword = passwordEncoder.encode(request.getPassword());
//        newUser.setPassword(encodedPassword);
//
//        newUser.setRoleId(1);
//
//        // Save the new user to the database
//        userRepository.add(newUser);
//
//        // Authenticate the new user
//        UserDetails userDetails = userDetailServiceImp.loadUserByUsername(newUser.getUsername());
//
//        // Generate a JWT token for the new user
//        String jwtToken = jwtService.generateToken(userDetails);
//
//        return AuthenticationResponse.builder().token(jwtToken).build();
//    }

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
