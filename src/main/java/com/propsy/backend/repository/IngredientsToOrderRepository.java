package com.propsy.backend.repository;

import com.propsy.backend.domain.IngredientsToOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IngredientsToOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientsToOrderRepository extends JpaRepository<IngredientsToOrder, Long> {

}
