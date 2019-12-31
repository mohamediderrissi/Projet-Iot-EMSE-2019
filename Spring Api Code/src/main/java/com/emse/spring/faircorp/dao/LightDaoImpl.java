package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public  class LightDaoImpl implements LightDaoCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Light> findOnLights() {
        String jpql = "select lt from Light lt where lt.status = :value";
        return em.createQuery(jpql, Light.class)
                .setParameter("value", Status.ON)
                .getResultList();
    }

    @Override
    //Find  lights of a room identified by its ID
    public List<Light> findLightsByRoomId(Long id) {
        String jpql = "select lt from Light lt  where lt.room.id = :roomId";
        return em.createQuery(jpql, Light.class)
                .setParameter("roomId", id)
                .getResultList();
    }

}