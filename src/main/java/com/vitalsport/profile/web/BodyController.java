package com.vitalsport.profile.web;

import com.google.common.collect.ImmutableMap;
import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.service.info.BodyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.vitalsport.profile.common.CommonUtils.decode;
import static com.vitalsport.profile.common.CommonUtils.getMeasurementDate;
import static java.lang.Integer.valueOf;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@Controller
@RequestMapping(value = "/body")
@CrossOrigin(origins = "*")
public class BodyController {

    @Autowired
    private BodyInfoService bodyInfoService;

    @RequestMapping(value = "/{userId}/{date}", method = POST)
    public ResponseEntity<?> saveBodyInfo(@PathVariable String userId, @PathVariable String date,
                                               @RequestBody BodyInfo bodyInfo) {
            bodyInfoService.save(prepareBodyId(userId, date), bodyInfo);
            return noContent().build();
    }

    @RequestMapping(value = "/{userId}", method = GET)
    public ResponseEntity<?> getLatestBodyInfo(@PathVariable String userId) {
        BodyInfo latestInfo = bodyInfoService.getLatest(decode(userId));
        return latestInfo == null ? status(NOT_FOUND).body(ImmutableMap.of("data", "BodyInfo wasn't found for userId = " + userId)) :
                ok(latestInfo);
    }

    @RequestMapping(value = "/{userId}/{date}", method = GET)
    public ResponseEntity<?> getBodyInfo(@PathVariable String userId,
                                         @PathVariable String date) {
        BodyInfo bodyInfo = bodyInfoService.get(prepareBodyId(userId, date));
        return (bodyInfo == null ?
                status(NOT_FOUND).body(ImmutableMap.of("data", "BodyInfo wasn't found for userId = " + userId + " date = " + date))
                : ok(bodyInfo));
    }

    @RequestMapping(value = "/{userId}/{date}", method = DELETE)
    public ResponseEntity<?> deleteBodyInfo(@PathVariable String userId, @PathVariable String date) {
            bodyInfoService.delete(prepareBodyId(userId, date));
            return noContent().build();
    }

    @RequestMapping(value = "/{userId}/allMeasurementDates", method = GET)
    public ResponseEntity<String> getAllMeasurementDates(@PathVariable("userId") String encodedUserId) {
        return ok(bodyInfoService.getMeasurementDates(decode(encodedUserId)).toString());
    }

    @RequestMapping(value = "/{userId}/measurementYears", method = GET)
    public ResponseEntity<?> getMeasurementYears(@PathVariable("userId") String encodedUserId) {
        return ok(bodyInfoService.getMeasurementYears(decode(encodedUserId)));
    }

    @RequestMapping(value = "/{userId}/{year}/measurementMonths", method = GET)
    public ResponseEntity<?> getMeasurementMonths(@PathVariable("userId") String encodedUserId,
                                                  @PathVariable("year") String measurementYear) {
        return ok(bodyInfoService.getMeasurementMonth(decode(encodedUserId), valueOf(measurementYear)));
    }

    @RequestMapping(value = "/{userId}/{year}/{month}/measurementDays", method = GET)
    public ResponseEntity<?> getMeasurementDays(@PathVariable("userId") String encodedUserId,
                                                @PathVariable("year") String measurementYear,
                                                @PathVariable("month") String measurementMonth) {
        return ok(bodyInfoService.getMeasurementDays(decode(encodedUserId),
                valueOf(measurementYear), valueOf(measurementMonth)));
    }

    //TODO: move to infoId factory to hide it creation details like decoding
    private InfoId prepareBodyId(String userId, String date) {
        return new InfoId(decode(userId), getMeasurementDate(date));
    }
}
