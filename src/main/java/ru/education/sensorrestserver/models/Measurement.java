package ru.education.sensorrestserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurement_id;

    @NotEmpty(message = "Measurement value should not be empty")
    @Min(value = (-100), message = "Minimal measurement value is -100")
    @Max(value = (100), message = "Maximal measurement value is 100")
    private Double value;

    @NotEmpty(message = "raining should not be empty")
    private Boolean raining;


    @Column(nullable = false)
    private long timestamp;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

}
