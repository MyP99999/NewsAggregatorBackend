package com.example.EvidenNewsAggregator.validations;


import com.example.EvidenNewsAggregator.auth.RegisterRequest;
import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import com.example.EvidenNewsAggregator.user.UserService;
import com.example.EvidenNewsAggregator.utils.LogAndResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RegisterValidation {

    @Autowired
    private UserService userService;

    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private  LogAndResponseUtil logAndResponseUtil;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])([A-Za-z\\d@#$%^&+=!]{10,})$";

    public ResponseEntity<Object> test(RegisterRequest request)
    {
        return validateRegistration(request);
    }

    public ResponseEntity<Object> validateRegistration(RegisterRequest request) {
        String methodName = "validateRegistration - ";

        // Check if the user already exists
        Users existingUser = userService.findByUsername(request.getUsername());
        Users existingEmail = userService.findByEmail(request.getEmail());

        if (existingUser != null) {
            return logAndResponseUtil.generate(logger, methodName, "User already exists with this username", HttpStatus.CONFLICT, null);
        }
        if (existingEmail != null) {
            return logAndResponseUtil.generate(logger, methodName, "User already exists with this email", HttpStatus.CONFLICT, null);
        }
        if (!request.getEmail().matches(EMAIL_REGEX)) {
            return logAndResponseUtil.generate(logger, methodName, "Invalid email format", HttpStatus.BAD_REQUEST, null);
        }
        // You can uncomment the password validation if needed
        if (!request.getPassword().matches(PASSWORD_REGEX)) {
            return logAndResponseUtil.generate(logger, methodName, "Invalid password format", HttpStatus.BAD_REQUEST, null);
        }


        return null; // Return null for success
    }
}