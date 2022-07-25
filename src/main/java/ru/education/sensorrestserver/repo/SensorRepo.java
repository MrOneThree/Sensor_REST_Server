package ru.education.sensorrestserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.education.sensorrestserver.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorRepo extends JpaRepository<Sensor, Long> {
    Boolean existsSensorByName(String name);

    Optional<Sensor> findByName(String name);
}
