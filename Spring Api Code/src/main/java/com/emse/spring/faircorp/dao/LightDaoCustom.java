package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Light;

import java.util.List;

public interface LightDaoCustom  {
    List<Light> findOnLights();

    // method to find the room lights when we send the id of the room
    public List<Light> findLightsByRoomId(Long id);
}