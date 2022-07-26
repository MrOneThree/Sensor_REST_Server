package ru.education.sensorrestserver.utils;

import org.springframework.stereotype.Component;

/**
 * @author Kirill Popov
 */
@Component
public class Clock {
    public long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }
}
