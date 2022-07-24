package ru.education.sensorrestserver.exceptions.measurement;

public class MeasurementNotCreatedException extends RuntimeException{
    public MeasurementNotCreatedException(String message) {
        super(message);
    }
}
