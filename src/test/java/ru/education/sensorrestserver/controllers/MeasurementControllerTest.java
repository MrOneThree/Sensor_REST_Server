package ru.education.sensorrestserver.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import ru.education.sensorrestserver.dto.MeasurementDto;
import ru.education.sensorrestserver.exceptions.sensor.SensorNotFoundException;
import ru.education.sensorrestserver.services.MeasurementsService;
import ru.education.sensorrestserver.services.SensorService;
import ru.education.sensorrestserver.utils.Clock;

import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Kirill Popov
 */
@WebMvcTest(MeasurementController.class)
class MeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ModelMapper modelMapper = new ModelMapper();
    @MockBean
    private Clock clock;
    @MockBean
    private MeasurementsService service;
    @MockBean
    private SensorService sensorService;

    private MockMvc underTest;

    @BeforeEach
    void setUp() {
       underTest = MockMvcBuilders.standaloneSetup(new MeasurementController(service, sensorService, modelMapper, clock)).build();
    }
    //------------------------getAllMeasurements() Test------------------------------------
    @Test
    void shouldReturnAllMeasurements() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/measurements"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    //------------------------rainyDaysCount() Test------------------------------------
    @Test
    void rainyDaysCount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/measurements/rainyDaysCount"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    //-------------------------addMeasurement() Test------------------------------------
    @Test
    void shouldReturnStatusOk_WhenSuppliedWith_ValidMeasurementDto_ThenChecksIfValid_AndThen_SavesMeasurement() throws Exception {

        String uri = "/measurements/add";
        String jsonRequest = "{\"value\": 10.0,\"raining\": false,\"sensor\":{\"name\": \"Second Sensor\"}}";

        underTest.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andExpect(status().isOk());
    }
    @Test
    void shouldReturnStatusBadRequest_WhenSuppliedWith_MeasurementDto_NullValue_ThenChecksIfValid_AndThen_SavesMeasurement() throws Exception {

        String uri = "/measurements/add";
        String jsonRequest ="{\"value\": ,\"raining\": false,\"sensor\":{\"name\": \"Second Sensor\"}}";

        underTest.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andExpect(status().isBadRequest());
    }
    @Test
    void shouldReturnStatusBadRequest_WhenSuppliedWith_MeasurementDto_ValueLessThanMinus100_ThenChecksIfValid_AndThen_SavesMeasurement() throws Exception {

        String uri = "/measurements/add";
        String jsonRequest ="{\"value\": -101.0,\"raining\": false,\"sensor\":{\"name\": \"Second Sensor\"}}";
             //   "{\"value\": 101.0,\"raining\": false,\"sensor\":{\"name\": \"Second Sensor\"}}";

        underTest.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andExpect(status().isBadRequest());
    }
    @Test
    void shouldReturnStatusBadRequest_WhenSuppliedWith_MeasurementDto_ValueBiggerThan100_ThenChecksIfValid_AndThen_SavesMeasurement() throws Exception {

        String uri = "/measurements/add";
        String jsonRequest ="{\"value\": 101.0,\"raining\": false,\"sensor\":{\"name\": \"Second Sensor\"}}";

        underTest.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andExpect(status().isBadRequest());
    }
    @Test
    void shouldReturnStatusBadRequest_WhenSuppliedWith_MeasurementDto_RainingIsNull_ThenChecksIfValid_AndThen_SavesMeasurement() throws Exception {

        String uri = "/measurements/add";
        String jsonRequest ="{\"value\": 10.0,\"raining\": ,\"sensor\":{\"name\": \"Second Sensor\"}}";

        underTest.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andExpect(status().isBadRequest());
    }
    @Test
    void shouldReturnStatusBadRequest_WhenSuppliedWith_MeasurementDto_SensorIsNull_ThenChecksIfValid_AndThen_SavesMeasurement() throws Exception {

        String uri = "/measurements/add";
        String jsonRequest ="{\"value\": 10.0,\"raining\": true ,\"sensor\":}";

        underTest.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andExpect(status().isBadRequest());
    }
    @Test
    void shouldReturnStatusBadRequest_WhenSuppliedWith_MeasurementDto_SensorDoesNotExist_ThenChecksIfValid_AndThen_SavesMeasurement() throws Exception {

        String uri = "/measurements/add";
        String jsonRequest ="{\"value\": 10.0,\"raining\": ,\"sensor\":{\"name\": \"WRONG\"}}";

        underTest.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }
    //-------------------------enrichMeasurement() Test------------------------------------
    @Test
    void shouldThrowSensorNotFoundException_WhenSuppliedWith_IncorrectSensor(){
        MeasurementController underTestController = new MeasurementController(service, sensorService, modelMapper, clock);
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(),"");
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setValue(10.0);
        measurementDto.setRaining(true);
        measurementDto.setSensor(null);

       Assertions.assertThrows(SensorNotFoundException.class, () -> underTestController.addMeasurement(measurementDto, bindingResult));

    }
}