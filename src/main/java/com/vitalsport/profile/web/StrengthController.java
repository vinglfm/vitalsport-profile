package com.vitalsport.profile.web;

import com.google.common.collect.ImmutableMap;
import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.StrengthInfo;
import com.vitalsport.profile.service.info.StrengthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.format.DateTimeParseException;

import static com.vitalsport.profile.common.CommonUtils.decode;
import static com.vitalsport.profile.common.CommonUtils.getMeasurementDate;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/strength")
@CrossOrigin(origins = "*")
public class StrengthController {

    @Autowired
    private StrengthInfoService strengthInfoService;

    @RequestMapping(value = "/{userId}/{date}", method = RequestMethod.POST)
    public ResponseEntity<?> saveStrengthInfo(@PathVariable String userId, @PathVariable String date,
                                               @RequestBody StrengthInfo strengthInfo) {
            strengthInfoService.save(prepareStrengthId(userId, date), strengthInfo);
            return noContent().build();
    }

    @RequestMapping(value = "/{userId}", method = GET)
    public ResponseEntity<?> getLatestStrengthInfo(@PathVariable String userId) {
        StrengthInfo latestInfo = strengthInfoService.getLatest(decode(userId));
        return latestInfo == null ? status(NOT_FOUND)
                .body(ImmutableMap.of("data", "Strength info wasn't found for userId = " + userId))
                : ok(latestInfo);
    }

    @RequestMapping(value = "/{userId}/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> getStrengthInfo(@PathVariable String userId, @PathVariable String date) {

            StrengthInfo strengthInfo = strengthInfoService.get(prepareStrengthId(userId, date));

            return (strengthInfo == null ?
                    status(NOT_FOUND).body(ImmutableMap.of("data","Strength info wasn't found for userId = " + userId + " and date = " + date))
                    : ok(strengthInfo));
    }

    @RequestMapping(value = "/{userId}/{date}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStrengthInfo(@PathVariable String userId, @PathVariable String date) {
            strengthInfoService.delete(prepareStrengthId(userId, date));
            return noContent().build();
    }

    private InfoId prepareStrengthId(String userId, String date) {
        return new InfoId(decode(userId), getMeasurementDate(date));
    }
}
