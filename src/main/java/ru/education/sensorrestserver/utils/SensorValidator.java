package ru.education.sensorrestserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.education.sensorrestserver.exceptions.sensor.SensorNotFoundException;
import ru.education.sensorrestserver.models.Sensor;
import ru.education.sensorrestserver.services.SensorService;
@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;
    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorService.existsSensorByName(sensor.getName()))
            errors.rejectValue("name","","Sensor with name "+ sensor.getName() + " already exists.");
    }
}
