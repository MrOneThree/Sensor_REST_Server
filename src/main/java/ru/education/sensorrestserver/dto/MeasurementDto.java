package ru.education.sensorrestserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MeasurementDto {
    @Min(value = (-100), message = "Minimal measurement value is -100")
    @Max(value = (100), message = "Maximum measurement value is 100")
    @NotNull(message = "value should not be empty")
    private Double value;
    @NotNull(message = "raining check should not be empty")
    private Boolean raining;
    @NotNull(message = "Sensor is empty")
    private SensorDto sensor;
    private long timestamp;
}
