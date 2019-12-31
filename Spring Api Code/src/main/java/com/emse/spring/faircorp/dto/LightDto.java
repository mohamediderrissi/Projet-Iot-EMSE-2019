package com.emse.spring.faircorp.dto;

import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Status;

public class LightDto {

        private  Long id;
        private Integer hue;
        private Integer brightness;
        private  Status status;
        private Long roomId;

        public LightDto() {
        }

        public LightDto(Light light) {
            this.id = light.getId();
            this.brightness = light.getBrightness();
            this.hue = light.getHue();
            this.status = light.getStatus();
            this.roomId = light.getRoom().getId();
        }


    public Long getId() {
            return id;
        }


        public Status getStatus() {
            return status;
        }

    public Long getRoomId() {
        return roomId;
    }


    public void setStatus(Status status) {
        this.status = status;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
}

