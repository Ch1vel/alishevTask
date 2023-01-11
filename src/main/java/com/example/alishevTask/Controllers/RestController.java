package com.example.alishevTask.Controllers;


import com.example.alishevTask.ExceptionHandler.MeasurementException;
import com.example.alishevTask.ExceptionHandler.SensorExistException;
import com.example.alishevTask.Model.Measurement;
import com.example.alishevTask.Model.Sensor;
import com.example.alishevTask.Service.MeasurementService;
import com.example.alishevTask.Service.SensorService;
import com.example.alishevTask.Util.ErrorData;
import com.example.alishevTask.Util.SensorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import javax.validation.Valid;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final SensorService sensorService;
    private final MeasurementService measurementService;
    private final SensorValidation sensorValidation;


    public RestController(SensorService sensorService, MeasurementService measurementService, SensorValidation sensorValidation) {
        this.sensorService = sensorService;
        this.measurementService = measurementService;
        this.sensorValidation = sensorValidation;
    }

    @PostMapping("/sensors/registration")
    public ResponseEntity<HttpStatus> addSensor(@RequestBody @Valid Sensor sensor, BindingResult bindingResult) {
        sensorValidation.validate(sensor,bindingResult);
        if(bindingResult.hasErrors()) throw new SensorExistException("Sensor already added");
        sensorService.addSensor(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/measurements/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid Measurement measurement, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) throw new MeasurementException("Incorrect measurement data");
        measurementService.addMeasurement(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/measurements")
    public ResponseEntity<List<Measurement>> showMeasurement() {
        return ResponseEntity.ok(measurementService.measurementList());
    }

    @GetMapping("/measurements/numberOfRainDays")
    public ResponseEntity<Integer> showCountOfRainyDays() {
        return ResponseEntity.ok(measurementService.findByRain(true).size());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handlerException(SensorExistException e) {
        ErrorData errorData = new ErrorData();
        errorData.setInfo(e.getMessage());
        return new ResponseEntity<>(errorData,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handlerException(MeasurementException e) {
        ErrorData errorData = new ErrorData();
        errorData.setInfo(e.getMessage());
        return new ResponseEntity<>(errorData,HttpStatus.NOT_FOUND);
    }
}
