package com.vitalsport.profile.web;

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
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Controller
@RequestMapping(value = "/strength")
@CrossOrigin(origins = "*")
public class StrengthController {

    @Autowired
    private StrengthInfoService strengthInfoService;

    @RequestMapping(value = "/{userId}/{date}", method = RequestMethod.POST)
    public ResponseEntity<String> saveStrengthInfo(@PathVariable String userId, @PathVariable String date,
                                               @RequestBody StrengthInfo strengthInfo) {
        try {
            strengthInfoService.save(prepareStrengthId(userId, date), strengthInfo);
            return ok("User strength info has been saved.");
        } catch (DateTimeParseException exception) {
            return badRequest().body(format("date = %s has an invalid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> getStrengthInfo(@PathVariable String userId, @PathVariable String date) {

        try {
            StrengthInfo strengthInfo = strengthInfoService.get(prepareStrengthId(userId, date));

            return (strengthInfo == null ?
                    status(NOT_FOUND).body("Strength info wasn't found for userId = " + userId + " and date = " + date)
                    : ok(strengthInfo));
        } catch (DateTimeParseException exception) {
            return badRequest().body(format("date = %s has an invalid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/{date}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStrengthInfo(@PathVariable String userId, @PathVariable String date) {
        try {
            strengthInfoService.delete(prepareStrengthId(userId, date));
            return ok("Delete has been applied.");
        } catch (DateTimeParseException exception) {
            return badRequest().body(format("date = %s has an invalid format.", date));
        } catch (EmptyResultDataAccessException exception) {
            return status(NOT_FOUND).body(String.format("Strength info for userId = %s and date = %s wasn't found.", userId, date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    private InfoId prepareStrengthId(String userId, String date) {
        return new InfoId(decode(userId), getMeasurementDate(date));
    }
}
