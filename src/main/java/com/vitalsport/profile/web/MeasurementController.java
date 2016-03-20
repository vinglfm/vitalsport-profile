package com.vitalsport.profile.web;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
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
import org.springframework.web.bind.annotation.CrossOrigin;

import static com.vitalsport.profile.common.CommonUtils.decode;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@Controller
@RequestMapping(value = "/measurements")
@CrossOrigin(origins = "*")
public class MeasurementController {

    @Autowired
    private MeasurementsService measurementsService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> saveMeasurements(@PathVariable String userId,
                                               @RequestBody Measurements measurements) {
            measurementsService.update(decode(userId), measurements);
            return noContent().build();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeasurements(@PathVariable String userId) {

            Measurements measurements = measurementsService.get(decode(userId));
            return (measurements == null ?
                    status(NOT_FOUND).body(ImmutableMap.of("data", "Measurements wasn't found for userId = " + userId))
                    : ok(measurements));
    }

}
