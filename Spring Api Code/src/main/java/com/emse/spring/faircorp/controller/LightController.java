package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.dao.LightDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dto.LightDto;
import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Status;
import com.emse.spring.faircorp.mqtt.MqttManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController  // 1.
@RequestMapping("/api/lights") // 2.
@Transactional // 3.
public class LightController {

    @Autowired
    private LightDao lightDao; // 4.
    @Autowired
    private RoomDao roomDao;


    @GetMapping // 5.
    public List<LightDto> findAll() {
        return lightDao
                .findAll()
                .stream()
                .map(LightDto::new)
                .collect(Collectors.toList());

    }

   @GetMapping(path = "/{id}")
    public LightDto findById(@PathVariable Long id) {
        return lightDao.findById(id).map(light -> new LightDto(light)).orElse(null);
    }

   @PutMapping(path = "/{id}/switchOn")
    public LightDto switchOn(@PathVariable Long id) {
        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);
       /**
        * We Publish a SwitchOn  and In the Payload we inject the Light ID
        * The MqttManager use Singleton Pattern
        */
       try{
           MqttManager.getInstance().publishSwitchOn(String.valueOf(id));
       }catch (Exception e)
       {
           e.printStackTrace();
       }

       light.setStatus(Status.ON);
        return new LightDto(light);
    }
    @PutMapping(path = "/{id}/switchOff")
    public LightDto switchOff(@PathVariable Long id) {
        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);
        try{
            MqttManager.getInstance().publishSwitchOff(String.valueOf(id));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        light.setStatus(Status.OFF);
        return new LightDto(light);
    }

    /**
     * For The create method :
     *      If the id exist already --------> Update : we should publish an update
     *      else : create
     */
    @PostMapping
    public LightDto create(@RequestBody LightDto dto) {
        Light light = null;
        if (dto.getId() != null) {
            light = lightDao.findById(dto.getId()).orElse(null);
        }

        if (light == null) {
            light = lightDao.save(new Light(dto.getId(),dto.getBrightness(), dto.getHue(), dto.getStatus(),roomDao.getOne(dto.getRoomId())));
        } else {
            /**
             * We publish to update the light's brightness and/or Hue
             * We send the required information in a String format with  the following pattern :
             *  " id \n value_brightness \n value_hue \n Light_Status"
             */
            String message = ""+dto.getId()  +"\n" + dto.getBrightness() +"\n" + dto.getHue()+"\n" + dto.getStatus();
            try {
                MqttManager.getInstance().publishUpdateLightBrightnessOrHue(message);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            light.setStatus(Status.ON);
            light.setBrightness(dto.getBrightness());
            light.setHue(dto.getHue());
            lightDao.save(light);
        }

        return new LightDto(light);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        lightDao.deleteById(id);
    }

}
