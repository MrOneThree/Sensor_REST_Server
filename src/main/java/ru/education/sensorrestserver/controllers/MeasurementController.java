package ru.education.sensorrestserver.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.education.sensorrestserver.dto.MeasurementDto;
import ru.education.sensorrestserver.exceptions.measurement.MeasurementErrorResponse;
import ru.education.sensorrestserver.exceptions.measurement.MeasurementNotCreatedException;
import ru.education.sensorrestserver.models.Measurement;
import ru.education.sensorrestserver.services.MeasurementsService;
import ru.education.sensorrestserver.services.SensorService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementsService measurementsService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementsService measurementsService, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MeasurementDto> getAllMeasurements(){
        return measurementsService.getAllMeasurements().stream()
                        .map(measurement -> modelMapper.map(measurement, MeasurementDto.class))
                        .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public long rainyDaysCount(){
        return measurementsService.rainyDaysCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDto measurementDto,
                                                     BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new MeasurementNotCreatedException(errorMsg.toString());
        }
        Measurement measurement = modelMapper.map(measurementDto, Measurement.class);

        enrichMeasurement(measurement);
        measurementsService.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private void enrichMeasurement(Measurement measurement){
        measurement.setTimestamp(System.currentTimeMillis());
        measurement.setSensor(sensorService.findSensorByName(measurement.getSensor().getName()));
    }
}
