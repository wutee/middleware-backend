package com.propsy.backend.repository;

import com.propsy.backend.domain.DeliveryPersonnel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeliveryPersonnel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryPersonnelRepository extends JpaRepository<DeliveryPersonnel, Long> {

}
