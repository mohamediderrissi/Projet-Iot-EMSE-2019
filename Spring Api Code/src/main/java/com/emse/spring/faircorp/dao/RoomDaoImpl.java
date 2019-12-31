package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RoomDaoImpl implements RoomDaoCustom {

    @PersistenceContext
    private EntityManager em;


    public Room findRoomByName(String name) {
        String jpql = "select r from Room r where r.name = :name";
        return em.createQuery(jpql, Room.class)
                .setParameter("name", name)
                .getSingleResult();
    }


}
