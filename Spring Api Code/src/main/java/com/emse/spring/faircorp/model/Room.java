package com.emse.spring.faircorp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
    public class Room {
        @Id
        @GeneratedValue
        private Long id;

        @Column
        private String name;

    @Column
    private String floor;

    @JsonManagedReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    List<Light> lights;

    @JsonBackReference
    @ManyToOne()
    Building building;

    public Room(){

    }

    public Room(String name, String floor, List<Light> lights, Building building) {
        this.name = name;
        this.floor = floor;
        this.lights = lights;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = this.id;
    }


    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

}
