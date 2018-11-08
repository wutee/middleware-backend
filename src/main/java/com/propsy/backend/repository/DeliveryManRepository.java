package com.propsy.backend.repository;

import com.propsy.backend.domain.DeliveryMan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeliveryMan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long> {

}
