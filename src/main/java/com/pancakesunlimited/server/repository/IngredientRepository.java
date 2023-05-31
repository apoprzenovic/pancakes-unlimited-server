package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Repository class for {@link Ingredient} entity
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    /**
     * Stored procedure to get the most ordered ingredient last month
     * The stored procedure is viewable here: create_schema.xml
     *
     * @return the id of the most ordered ingredient last month
     */
    @Procedure("MostOrderedIngredientLastMonth")
    List<Integer> getMostOrderedIngredientLastMonth();

    /**
     * Stored procedure to get the most ordered healthy ingredient last month
     * The stored procedure is viewable here: create_schema.xml
     *
     * @return the id of the most ordered healthy ingredient last month
     */
    @Procedure("MostOrderedHealthyIngredientLastMonth")
    List<Integer> getMostOrderedHealthyIngredientLastMonth();

    Ingredient findOneById(Integer id);
}

// In order to save to a database, save() method is needed because of:
// ddl-auto: none
