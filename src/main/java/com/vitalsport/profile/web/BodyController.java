package com.vitalsport.profile.web;

import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.service.info.BodyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.Collection;

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
@RequestMapping(value = "/body")
public class BodyController {

    @Autowired
    private BodyInfoService bodyInfoService;

    @RequestMapping(value = "/{userId}/{date}", method = POST)
    public ResponseEntity<String> saveBodyInfo(@PathVariable String userId, @PathVariable String date,
                                               @RequestBody BodyInfo bodyInfo) {
        try {
            bodyInfoService.save(prepareBodyId(userId, date), bodyInfo);

            return ok("User body info has been saved.");
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has an invalid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}", method = GET)
    public ResponseEntity<?> getLatestBodyInfo(@PathVariable String userId) {

        try {
            BodyInfo bodyInfo = bodyInfoService.getLatest(decode(userId));

            return ok(bodyInfo);
        } catch (IllegalArgumentException exception) {
            return badRequest()
                    .body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/{date}", method = GET)
    public ResponseEntity<?> getBodyInfo(@PathVariable String userId,
                                         @PathVariable String date) {

        try {
            BodyInfo bodyInfo = bodyInfoService.get(prepareBodyId(userId, date));

            return (bodyInfo == null ?
                    status(NOT_FOUND).body("BodyInfo wasn't found for userId = " + userId + " date = " + date)
                    : ok(bodyInfo));
        } catch (DateTimeParseException exception) {
            return badRequest()
                    .body(String.format("date = %s has an invalid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest()
                    .body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/{date}", method = DELETE)
    public ResponseEntity<String> deleteBodyInfo(@PathVariable String userId, @PathVariable String date) {
        try {
            bodyInfoService.delete(prepareBodyId(userId, date));
            return ok("Delete has been applied.");
        } catch (EmptyResultDataAccessException exception) {
            return status(NOT_FOUND).body(String.format("Body info for userId = %s and date = %s wasn't found.", userId, date));
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has an invalid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/allMeasurementDates", method = GET)
    public ResponseEntity<String> getAllMeasurementDates (@PathVariable("userId") String encodedUserId) {
        try {
            Collection<String> measurementDates = bodyInfoService.getMeasurementDates(decode(encodedUserId));
            return ok(measurementDates.toString());
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/measurementYears", method = GET)
    public ResponseEntity<?> getMeasurementYears (@PathVariable("userId") String encodedUserId) {
        try {
            return ok(bodyInfoService.getMeasurementYears(decode(encodedUserId)));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/{year}/measurementMonths", method = GET)
    public ResponseEntity<?> getMeasurementMonths (@PathVariable("userId") String encodedUserId,
                                          @PathVariable("year") String measurementYear) {
        try {
            return ok(bodyInfoService.getMeasurementMonth(decode(encodedUserId), valueOf(measurementYear)));
        } catch (NumberFormatException exception) {
            return badRequest().body(String.format("Year = %s has an invalid format.", measurementYear));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/{year}/{month}/measurementDays", method = GET)
    public ResponseEntity<?> getMeasurementDays (@PathVariable("userId") String encodedUserId,
                                        @PathVariable("year") String measurementYear,
                                        @PathVariable("month") String measurementMonth) {
        try {
            return ok(bodyInfoService.getMeasurementDays(decode(encodedUserId),
                            valueOf(measurementYear), valueOf(measurementMonth)));
        } catch (NumberFormatException exception) {
            return badRequest().body(String.format("Year = %s or Month = % has an invalid format.", measurementYear, measurementMonth));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }

    //TODO: move to infoId factory to hide it creation details like decoding
    private InfoId prepareBodyId(String userId, String date) {
        return new InfoId(decode(userId), getMeasurementDate(date));
    }
}
