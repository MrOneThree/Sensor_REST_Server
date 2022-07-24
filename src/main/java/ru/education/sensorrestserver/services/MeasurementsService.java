package ru.education.sensorrestserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.sensorrestserver.models.Measurement;
import ru.education.sensorrestserver.repo.MeasurementRepo;

import java.util.List;

@Service
public class MeasurementsService {

    private final MeasurementRepo repo;

    @Autowired
    public MeasurementsService(MeasurementRepo repo) {
        this.repo = repo;
    }

    public List<Measurement> getAllMeasurements() {
        return repo.findAll();
    }

    public long rainyDaysCount(){
        return  repo.findAll().stream()
                .filter(Measurement::getRaining)
                .count();
    }

    @Transactional
    public void save(Measurement measurement){
        repo.save(measurement);
    }
}
