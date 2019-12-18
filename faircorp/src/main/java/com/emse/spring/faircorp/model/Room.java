package com.emse.spring.faircorp.model;

import javax.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue
    private Long roomId;


    @OneToOne(mappedBy = "room")
    Light light;

    @OneToOne(mappedBy = "room")
    Noise noise;

    public Room(){

    }

    public Room(Light light) {
        this.light = light;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long id) {
        this.roomId = roomId;
    }


    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Noise getNoise() {
        return noise;
    }

    public void setNoise(Noise noise) {
        this.noise = noise;
    }
}
