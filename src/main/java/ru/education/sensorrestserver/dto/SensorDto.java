package ru.education.sensorrestserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SensorDto {
    @NotEmpty(message = "Sensor name should not be empty")
    @Length(message = "Sensor name should be from 1 to 100 characters long", min = 1, max = 100)
    private String name;
}
