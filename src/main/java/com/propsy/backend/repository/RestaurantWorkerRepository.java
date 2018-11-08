package com.propsy.backend.repository;

import com.propsy.backend.domain.RestaurantWorker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RestaurantWorker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantWorkerRepository extends JpaRepository<RestaurantWorker, Long> {

    @Query(value = "select distinct restaurant_worker from RestaurantWorker restaurant_worker left join fetch restaurant_worker.employers",
        countQuery = "select count(distinct restaurant_worker) from RestaurantWorker restaurant_worker")
    Page<RestaurantWorker> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct restaurant_worker from RestaurantWorker restaurant_worker left join fetch restaurant_worker.employers")
    List<RestaurantWorker> findAllWithEagerRelationships();

    @Query("select restaurant_worker from RestaurantWorker restaurant_worker left join fetch restaurant_worker.employers where restaurant_worker.id =:id")
    Optional<RestaurantWorker> findOneWithEagerRelationships(@Param("id") Long id);

}
