package com.propsy.backend.repository;

import com.propsy.backend.domain.FoodInOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FoodInOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodInOrderRepository extends JpaRepository<FoodInOrder, Long> {

}
