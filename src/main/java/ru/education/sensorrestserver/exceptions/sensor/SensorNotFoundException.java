package ru.education.sensorrestserver.exceptions.sensor;

import java.util.function.Supplier;

public class SensorNotFoundException extends RuntimeException  {

    public SensorNotFoundException (String message){
        super(message);
    }
}
