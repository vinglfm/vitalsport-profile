package com.vitalsport.profile.web;

import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.service.info.BodyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;

import static com.vitalsport.profile.common.CommonUtils.decode;
import static com.vitalsport.profile.common.CommonUtils.getMeasurementDate;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.*;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
public class BodyController {

    @Autowired
    private BodyInfoService bodyInfoService;

    @RequestMapping(value = "/body/{userId}/{date}", method = RequestMethod.POST)
    public ResponseEntity<String> saveBodyInfo(@PathVariable String userId, @PathVariable String date,
                                               @RequestBody BodyInfo bodyInfo) {
        try {
            bodyInfoService.save(prepareBodyId(userId, date), bodyInfo);

            return ok("User body info has been saved.");
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }


    @RequestMapping(value = "/body/{userId}/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> getBodyInfo(@PathVariable String userId, @PathVariable String date) {

        try {
            BodyInfo bodyInfo = bodyInfoService.get(prepareBodyId(userId, date));

            return (bodyInfo == null ?
                    status(NOT_FOUND).body("BodyInfo wasn't found for userId = " + userId + " date = " + date)
                    : ok(bodyInfo));
        } catch (DateTimeParseException exception) {
            return badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.toString());
        }
    }


    @RequestMapping(value = "/body/{userId}/{date}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBodyInfo(@PathVariable String userId, @PathVariable String date) {
        try {
            bodyInfoService.delete(prepareBodyId(userId, date));
            return ok("User body info has been deleted");
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }

//    @RequestMapping(value = "/body/{userId}/allMeasurementDates", method = GET)
//    public ResponseEntity<String> getAllMeasurementDates (@PathVariable("userId") String encodedUserId) {
//        try {
//            String userId = decode(encodedUserId);
//            log.debug("Start processing request getAllMeasurementDates for user: " + userId);
//
//            Collection<String> measurementDates = bodyInfoService.getAllMeasurementDates(userId);
//            return ok(measurementDates.toString());
//        } catch (IllegalArgumentException exception) {
//            return badRequest().body(exception.toString());
//        }
//    }
//
//    @RequestMapping(value = "/body/{userId}/measurementYears", method = GET)
//    public ResponseEntity<String> getMeasurementYears (@PathVariable("userId") String encodedUserId) {
//        try {
//            String userId = decode(encodedUserId);
//            log.debug("Start processing request getMeasurementYears for user =" + userId);
//
//            Collection<Integer> measurementYears = bodyInfoService.getMeasurementYears(userId);
//
//            return ok(measurementYears.toString());
//        } catch (IllegalArgumentException exception) {
//            return badRequest().body(exception.toString());
//        }
//    }
//
//    @RequestMapping(value = "/body/{userId}/{year}/measurementMonths", method = GET)
//    public ResponseEntity<String> getMeasurementMonths (@PathVariable("userId") String encodedUserId,
//                                          @PathVariable("year") String measurementYear) {
//        try {
//            String userId = decode(encodedUserId);
//            int year = Integer.valueOf(measurementYear);
//            log.debug(format("Start processing request getMeasurementMonths for user = %s, year = %d", userId, year));
//
//            Collection<Integer> measurementMonths = bodyInfoService.getMeasurementMonths(userId, year);
//
//            return ok(bodyInfoService.convertToMonths(measurementMonths));
//        } catch (Exception exception) {
//            return badRequest().body(exception.toString());
//        }
//    }
//    @RequestMapping(value = "/{userId}/{year}/{month}/measurementDays", method = GET)
//    public ResponseEntity<String> getMeasurementDays (@PathVariable("userId") String encodedUserId,
//                                        @PathVariable("year") String measurementYear,
//                                        @PathVariable("month") String measurementMonth) {
//        try {
//            String userId = decode(encodedUserId);
//            int year = Integer.valueOf(measurementYear);
//            int month = Integer.valueOf(measurementMonth);
//
//            log.debug(format("Start processing request getMeasurementDays for user = %s, year = %d, month = %d",
//                            userId, year, month));
//
//            Collection<Integer> measurementDays = bodyInfoService.getMeasurementDays(userId, year, month);
//
//            return ok(bodyInfoService.convertToDays(measurementDays));
//        } catch (Exception exception) {
//            return badRequest().body(exception.toString());
//        }
//    }
//
//    @RequestMapping(value = "/{encodedUserId}/measurementUnits", method = GET)
//    public ResponseEntity<String> getMeasurementUnits (@PathVariable("userId") String encodedUserId) {
//        try {
//            String userId = decode(encodedUserId);
//            log.debug("Start processing request getMeasurementUnits for user: " + userId);
//
//            Measurements measurements = bodyInfoService.getAllMeasurementUnits(userId);
//            return ok(bodyInfoService.convert(measurements));
//        } catch (Exception exception) {
//            return badRequest().body(exception.toString());
//        }
//    }

    private InfoId prepareBodyId(String userId, String date) {
        return new InfoId(decode(userId), getMeasurementDate(date));
    }
}
