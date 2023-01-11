package com.example.alishevTask.Service;

import com.example.alishevTask.Model.Sensor;
import com.example.alishevTask.Repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }
    @Transactional
    public boolean addSensor(Sensor sensor) {
        Optional<Sensor> sensorOptional = sensorRepository.findByName(sensor.getName());
        if(sensorOptional.isPresent()) return false;
        sensorRepository.save(sensor);
        return true;
    }
}
