package com.example.EvidenNewsAggregator.controllers;


import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import com.example.EvidenNewsAggregator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public Iterable<Users> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Users findById(@PathVariable Integer id){
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        int deletedRows = userService.deleteById(id);

        if (deletedRows > 0) {
            return ResponseEntity.ok("User with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable Integer id, @RequestBody Users updatedUser) {
        updatedUser.setId(id); // Make sure the ID in the updatedUser object matches the path variable ID
        int updatedRows = userService.updateUserFields(id, updatedUser);

        if (updatedRows > 0) {
            return ResponseEntity.ok("User with ID " + id + " has been updated.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
