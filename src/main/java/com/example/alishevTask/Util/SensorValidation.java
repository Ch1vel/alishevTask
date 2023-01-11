package com.example.alishevTask.Util;

import com.example.alishevTask.Model.Sensor;
import com.example.alishevTask.Service.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@Component
public class SensorValidation implements Validator {

    private final SensorService sensorService;

    public SensorValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if(!sensorService.addSensor(sensor)) {
            errors.rejectValue("name","","Sensor is already added");
        }
    }
}
