package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Roles;
import com.pancakesunlimited.server.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pu/roles")
public class RolesController {

    private final RolesService rolesService;

    @Autowired
    public RolesController(RolesService rolesService){
        this.rolesService = rolesService;
    }

    @GetMapping
    public List<Roles> getAllRoles() {
        return rolesService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Roles getRole(@PathVariable Integer id) {
        return rolesService.getRoleById(id);
    }

    @PostMapping
    public Roles createRole(@RequestBody Roles role) {
        return rolesService.createRole(role);
    }

    @PutMapping("/{id}")
    public Roles updateRole(@PathVariable Integer id, @RequestBody Roles roleDetails) {
        return rolesService.updateRole(id, roleDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteRole(@PathVariable Integer id) {
        rolesService.deleteRole(id);
    }
}
