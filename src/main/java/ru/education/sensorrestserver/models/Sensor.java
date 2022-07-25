package ru.education.sensorrestserver.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sensor_id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Sensor name should not be empty")
    @Length(message = "Sensor name should be from 1 to 100 characters long", min = 1, max = 100)
    private String name;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Measurement> measurements;

}
