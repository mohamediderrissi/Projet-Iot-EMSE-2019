package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;


@RestController
public class RoomController {

    @Autowired
    private RoomDao roomDao;


    @RequestMapping("/api/rooms/")
    public List<Room> getRooms()
    {
        return roomDao.findAll();
    }

    @RequestMapping(value = "/api/rooms/{roomId}/switch-light-and-list" , method = RequestMethod.GET)
    @Transactional
    public void switchLight(@PathVariable Long roomId)
    {
        if (roomDao.findById(roomId).isPresent())
        {
            Room room = roomDao.findById(roomId).get();
            if(room.getLight().getStatus()== Status.ON) room.getLight().setStatus(Status.OFF);
            else { room.getLight().setStatus(Status.ON); }

            roomDao.save(room);

        }
    }


}
