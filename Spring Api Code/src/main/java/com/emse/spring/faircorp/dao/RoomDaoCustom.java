package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDaoCustom {
    public Room findRoomByName(String name);
}
