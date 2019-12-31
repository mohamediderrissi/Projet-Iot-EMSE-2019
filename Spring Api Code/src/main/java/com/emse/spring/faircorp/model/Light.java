package com.emse.spring.faircorp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "LIGHT")
public class Light {

    @Id
    private Long id;

    @Column(nullable = false)
    private Integer hue;

    private Integer brightness;

    @Enumerated(EnumType.STRING)
    private Status status;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "room_Id" , referencedColumnName = "id")
    Room room;

    public Light() {
    }

    public Light(Long id,Integer brightness, Integer hue , Status status,Room room) {
        this.id = id;
        this.brightness = brightness;
        this.hue = hue;
        this.status = status;
        this.room = room;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHue() {
        return hue;
    }

    public void setHue(Integer hue) {
        this.hue = hue;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
