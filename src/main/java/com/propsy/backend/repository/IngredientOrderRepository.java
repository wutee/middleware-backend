package com.propsy.backend.repository;

import com.propsy.backend.domain.IngredientOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IngredientOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientOrderRepository extends JpaRepository<IngredientOrder, Long> {

}
