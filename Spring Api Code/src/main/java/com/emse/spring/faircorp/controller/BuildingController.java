package com.emse.spring.faircorp.controller;


import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/buildings/")
@Transactional
public class BuildingController {



    @Autowired
    private BuildingDao buildingDao;

    @GetMapping
    public List<Building> findAll() {
        return buildingDao
                .findAll();
        /*
                .stream()
                .map(BuildingDto::new)
                .collect(Collectors.toList());
*/

    }

    @GetMapping(path = "/{id}")
    public Building findById(@PathVariable Long id) {
        return buildingDao.findById(id).orElse(null);
    }

    @PostMapping
    public BuildingDto create(@RequestBody BuildingDto dto) {
        Building building = null;
        if (dto.getId() != null) {
            building = buildingDao.findById(dto.getId()).orElse(null);
        }
        if (building == null) {
            building = buildingDao.save(new Building(dto.getName(),dto.getRooms()));
        } else {
            building.setName(dto.getName());
            buildingDao.save(building);
        }
        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        buildingDao.deleteById(id);
    }

}
