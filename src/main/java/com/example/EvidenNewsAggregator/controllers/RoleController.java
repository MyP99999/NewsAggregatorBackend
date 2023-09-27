package com.example.EvidenNewsAggregator.controllers;

import com.example.EvidenNewsAggregator.entities.tables.pojos.Roles;
import com.example.EvidenNewsAggregator.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class RoleController {

    @Autowired
    private final RoleService roleService;

    @GetMapping
    public Iterable<Roles> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Roles findById(@PathVariable Integer id) {
        return roleService.findById(id);
    }

}
