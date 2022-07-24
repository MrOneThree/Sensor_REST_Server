package ru.education.sensorrestserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.education.sensorrestserver.models.Measurement;

@Repository
public interface MeasurementRepo extends JpaRepository<Measurement, Long> {
}
