package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Roles;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Service class for the {@link Roles} Entity
 */
@Service
public class RolesService {

    private final RolesRepository rolesRepository;

    /**
     * Constructor for the RolesService class
     *
     * @param rolesRepository - the repository for the {@link Roles} Entity
     */
    @Autowired
    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    /**
     * Method to get all roles using {@link RolesRepository}
     *
     * @return - a list of all roles
     */
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    /**
     * Method to get a role by id using {@link RolesRepository}
     *
     * @param id - the id of the role to be returned
     * @return - the role with the specified id
     */
    public Roles getRoleById(Integer id) {
        return rolesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient" + "id: " + id));
    }

    /**
     * Method to create a role using {@link RolesRepository}
     *
     * @param role - the role to be created
     * @return - the created role
     */
    public Roles createRole(Roles role) {
        return rolesRepository.save(role);
    }

    /**
     * Method to update a role using {@link RolesRepository}
     *
     * @param id          - the id of the role to be updated
     * @param roleDetails - the role details to be updated
     * @return - the updated role
     */
    public Roles updateRole(Integer id, Roles roleDetails) {
        Roles currentRole = getRoleById(id);

        if (roleDetails.getName() != null && !roleDetails.getName().isEmpty()) {
            currentRole.setName(roleDetails.getName());
        }

        return rolesRepository.save(currentRole);
    }


    /**
     * Method to delete a role using {@link RolesRepository}
     *
     * @param id - the id of the role to be deleted
     */
    public void deleteRole(Integer id) {
        Roles role = getRoleById(id);
        rolesRepository.delete(role);
    }
}
