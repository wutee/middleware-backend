package com.propsy.backend.repository;

import com.propsy.backend.domain.RestaurantWorker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RestaurantWorker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantWorkerRepository extends JpaRepository<RestaurantWorker, Long> {

}
