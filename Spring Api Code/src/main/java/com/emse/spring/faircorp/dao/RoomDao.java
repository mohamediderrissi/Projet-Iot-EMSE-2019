package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RoomDao extends RoomDaoCustom, JpaRepository<Room, Long> {
    @Modifying
    @Query("delete from Light lt where lt.room.id=:id")
    void deleteLightsByRoomId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("delete from Room r where r.building.id=:id")
    void deleteRoomsByBuildingId(@Param("id") Long id);
}
