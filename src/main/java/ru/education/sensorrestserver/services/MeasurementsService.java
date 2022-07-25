package ru.education.sensorrestserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.sensorrestserver.models.Measurement;
import ru.education.sensorrestserver.repo.MeasurementRepo;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
        Calendar cal = Calendar.getInstance();
        return  repo.findAll().stream()
                .filter(Measurement::getRaining)
                .map(measurement -> {
                    cal.setTimeInMillis(measurement.getTimestamp());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    return cal.getTime();
                })
                .distinct()
                .count();
    }

    @Transactional
    public void save(Measurement measurement){
        repo.save(measurement);
    }
}
