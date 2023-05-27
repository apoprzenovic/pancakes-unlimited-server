package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> { }
//In order to save to a database, save() method is needed because of:
// ddl-auto: none
