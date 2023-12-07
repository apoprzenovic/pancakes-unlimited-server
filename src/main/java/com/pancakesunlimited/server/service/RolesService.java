package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Roles;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {

    private final RolesRepository rolesRepository;

    @Autowired
    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public ResponseEntity<List<Roles>> getAllRoles() {
        List<Roles> roles = rolesRepository.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    public ResponseEntity<Roles> getRoleById(Integer id) {
        Roles role = rolesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role" + "id: " + id));
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    public ResponseEntity<Roles> createRole(Roles role) {
        Roles createdRole = rolesRepository.save(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    public ResponseEntity<Roles> updateRole(Integer id, Roles roleDetails) {
        Roles currentRole = getRoleById(id).getBody();

        if (roleDetails.getName() != null && !roleDetails.getName().isEmpty()) {
            currentRole.setName(roleDetails.getName());
        }

        Roles updatedRole = rolesRepository.save(currentRole);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteRole(Integer id) {
        Roles role = getRoleById(id).getBody();
        rolesRepository.delete(role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}