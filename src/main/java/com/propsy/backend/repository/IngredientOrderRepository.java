package com.propsy.backend.repository;

import com.propsy.backend.domain.IngredientOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the IngredientOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientOrderRepository extends JpaRepository<IngredientOrder, Long> {

    @Query(value = "select distinct ingredient_order from IngredientOrder ingredient_order left join fetch ingredient_order.ingredientLists",
        countQuery = "select count(distinct ingredient_order) from IngredientOrder ingredient_order")
    Page<IngredientOrder> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct ingredient_order from IngredientOrder ingredient_order left join fetch ingredient_order.ingredientLists")
    List<IngredientOrder> findAllWithEagerRelationships();

    @Query("select ingredient_order from IngredientOrder ingredient_order left join fetch ingredient_order.ingredientLists where ingredient_order.id =:id")
    Optional<IngredientOrder> findOneWithEagerRelationships(@Param("id") Long id);

}
