package com.vitalsport.profile.web;

import com.vitalsport.profile.model.Measurements;
import com.vitalsport.profile.service.measurements.MeasurementsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.vitalsport.profile.common.CommonUtils.decode;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
public class MeasurementController {

    @Autowired
    private MeasurementsService measurementsService;

    @RequestMapping(value = "/measurements/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<String> saveMeasurements(@PathVariable String userId,
                                               @RequestBody Measurements measurements) {
        try {
            measurementsService.update(decode(userId), measurements);
            return ok("User measurements has been saved.");
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/measurements/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeasurements(@PathVariable String userId) {

        try {
            Measurements measurements = measurementsService.get(decode(userId));
            return (measurements == null ?
                    status(NOT_FOUND).body("Measurements wasn't found for userId = " + userId )
                    : ok(measurements));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

}
