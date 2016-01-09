package com.vitalsport.profile.web;

import com.vitalsport.profile.common.CommonUtils;
import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.service.info.BodyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;

import static com.vitalsport.profile.common.CommonUtils.decode;
import static com.vitalsport.profile.common.CommonUtils.getMeasurementDate;
import static java.lang.Integer.valueOf;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
public class BodyController {

    @Autowired
    private BodyInfoService bodyInfoService;

    @RequestMapping(value = "/body/{userId}/{date}", method = POST)
    public ResponseEntity<String> saveBodyInfo(@PathVariable String userId, @PathVariable String date,
                                               @RequestBody BodyInfo bodyInfo) {
        try {
            bodyInfoService.save(prepareBodyId(userId, date), bodyInfo);

            return ok("User body info has been saved.");
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/body/{userId}/{date}", method = GET)
    public ResponseEntity<?> getBodyInfo(@PathVariable String userId, @PathVariable String date) {

        try {
            BodyInfo bodyInfo = bodyInfoService.get(prepareBodyId(userId, date));

            return (bodyInfo == null ?
                    status(NOT_FOUND).body("BodyInfo wasn't found for userId = " + userId + " date = " + date)
                    : ok(bodyInfo));
        } catch (DateTimeParseException exception) {
            return badRequest()
                    .body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest()
                    .body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/body/{userId}/{date}", method = DELETE)
    public ResponseEntity<String> deleteBodyInfo(@PathVariable String userId, @PathVariable String date) {
        try {
            bodyInfoService.delete(prepareBodyId(userId, date));
            return ok("User body info has been deleted");
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/body/{userId}/allMeasurementDates", method = GET)
    public ResponseEntity<?> getAllMeasurementDates (@PathVariable("userId") String encodedUserId) {
        try {
            return ok(bodyInfoService.getMeasurementDates(decode(encodedUserId)));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/body/{userId}/measurementYears", method = GET)
    public ResponseEntity<?> getMeasurementYears (@PathVariable("userId") String encodedUserId) {
        try {
            return ok(bodyInfoService.getMeasurementYears(decode(encodedUserId)));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/body/{userId}/{year}/measurementMonths", method = GET)
    public ResponseEntity<?> getMeasurementMonths (@PathVariable("userId") String encodedUserId,
                                          @PathVariable("year") String measurementYear) {
        try {
            return ok(bodyInfoService.getMeasurementMonth(decode(encodedUserId), valueOf(measurementYear)));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/body/{userId}/{year}/{month}/measurementDays", method = GET)
    public ResponseEntity<?> getMeasurementDays (@PathVariable("userId") String encodedUserId,
                                        @PathVariable("year") String measurementYear,
                                        @PathVariable("month") String measurementMonth) {
        try {
            return ok(bodyInfoService.getMeasurementDays(decode(encodedUserId),
                            valueOf(measurementYear), valueOf(measurementMonth)));
        } catch (Exception exception) {
            return badRequest().body(exception.toString());
        }
    }

    private InfoId prepareBodyId(String userId, String date) {
        return new InfoId(decode(userId), getMeasurementDate(date));
    }

    public static void main(String[] args) {
        System.out.print(CommonUtils.encode("vinglfm1@gmail.com"));
    }
}
