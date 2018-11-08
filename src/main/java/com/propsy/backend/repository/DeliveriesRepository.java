package com.propsy.backend.repository;

import com.propsy.backend.domain.Deliveries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Deliveries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveriesRepository extends JpaRepository<Deliveries, Long> {

}
