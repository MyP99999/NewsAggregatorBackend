package com.example.EvidenNewsAggregator.auth;

import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import com.example.EvidenNewsAggregator.responses.MessageResponse;
import com.example.EvidenNewsAggregator.user.UserService;
import com.example.EvidenNewsAggregator.validations.RegisterValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService; // Injected via constructor
    private final RegisterValidation registerValidation;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request)
    {
        ResponseEntity<Object> validationResponse = registerValidation.validateRegistration(request);
        if (request.getUsername() == null || request.getUsername().isEmpty() ||
                request.getEmail() == null || request.getEmail().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: All fields are required!"));
        }else if (validationResponse != null)
        {
            return validationResponse;
        }

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        try {
            service.authenticate(request);
        } catch (AuthenticationException e) {
            if (e instanceof BadCredentialsException) {
                // Invalid username or password
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Invalid username or password!"));

            } else {
                // Generic authentication failure
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse("Error: Authentication failed."));
            }
        }

        return ResponseEntity.ok(new MessageResponse("Authentication successful!"));
    }
}
