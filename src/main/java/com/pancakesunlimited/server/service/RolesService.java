package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Roles;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {

    private final RolesRepository rolesRepository;

    @Autowired
    public RolesService(RolesRepository rolesRepository){
        this.rolesRepository = rolesRepository;
    }

    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    public Roles getRoleById(Integer id) {
        return rolesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient" + "id: " + id));
    }

    public Roles createRole(Roles role) {
        return rolesRepository.save(role);
    }

    public Roles updateRole(Integer id, Roles roleDetails) {
        Roles newRole = getRoleById(id);
        newRole.setName(roleDetails.getName());
        return rolesRepository.save(newRole);
    }

    public void deleteRole(Integer id) {
        Roles role = getRoleById(id);
        rolesRepository.delete(role);
    }
}
