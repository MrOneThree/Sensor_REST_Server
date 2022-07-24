package ru.education.sensorrestserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MeasurementDto {
    @NotEmpty(message = "Measurement value should not be empty")
    @Min(value = (-100), message = "Minimal measurement value is -100")
    @Max(value = (100), message = "Maximal measurement value is 100")
    private Double value;
    @NotEmpty(message = "raining should not be empty")
    private Boolean raining;
    private SensorDto sensor;
}
