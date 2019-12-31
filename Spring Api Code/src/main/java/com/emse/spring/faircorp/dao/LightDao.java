package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Light;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LightDao extends LightDaoCustom, JpaRepository<Light, Long> {
    @Transactional
    @Modifying
    @Query("delete from Light lt where lt.room.building.id=:id")
    void deleteLightsByBuildingId(@Param("id") Long id);
}
