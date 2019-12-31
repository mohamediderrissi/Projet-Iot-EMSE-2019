package com.emse.spring.faircorp.controller;


import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private BuildingDao buildingDao;

    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao
                .findAll()
                .stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(room -> new RoomDto(room)).orElse(null);
    }

    @PutMapping(path = "/{id}/switchOn")
    public RoomDto switchOn(@PathVariable Long id) {
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
       for(Light light : room.getLights()) light.setStatus(Status.ON);
        return new RoomDto(room);
    }
    @PutMapping(path = "/{id}/switchOff")
    public RoomDto switchOff(@PathVariable Long id) {
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
       for(Light light : room.getLights()) light.setStatus(Status.OFF);
        return new RoomDto(room);
    }


    @PostMapping
    public RoomDto create(@RequestBody RoomDto dto) {
        Room room = null;
        if (dto.getId() != null) {
            room = roomDao.findById(dto.getId()).orElse(null);
        }
        if (room == null) {
            room = roomDao.save(new Room(dto.getName(), dto.getFloor(), dto.getLights(), buildingDao.getOne(dto.getBuildingId())));
        } else {
            room.setName(dto.getName());
            room.setFloor(dto.getFloor());
            room.setLights(dto.getLights());
            roomDao.save(room);
        }
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        roomDao.deleteLightsByRoomId(id);
        roomDao.deleteById(id);
    }

}
