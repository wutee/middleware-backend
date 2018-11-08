package com.propsy.backend.repository;

import com.propsy.backend.domain.FoodInMenu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FoodInMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodInMenuRepository extends JpaRepository<FoodInMenu, Long> {

}
