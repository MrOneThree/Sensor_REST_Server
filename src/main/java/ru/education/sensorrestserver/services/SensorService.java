package ru.education.sensorrestserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.sensorrestserver.exceptions.sensor.SensorNotFoundException;
import ru.education.sensorrestserver.models.Sensor;
import ru.education.sensorrestserver.repo.SensorRepo;

@Service
public class SensorService {

    private final SensorRepo repo;

    @Autowired
    public SensorService(SensorRepo repo) {
        this.repo = repo;
    }

    public Boolean existsSensorByName(String name){
        return repo.existsSensorByName(name);
    }

    @Transactional
    public void save(Sensor sensor){
        repo.save(sensor);
    }
}
