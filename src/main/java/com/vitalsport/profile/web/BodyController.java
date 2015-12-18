package com.vitalsport.profile.web;

import com.vitalsport.profile.model.MeasurementId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.service.BodyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.format.DateTimeParseException;

import static com.vitalsport.profile.common.CommonUtils.decode;
import static com.vitalsport.profile.common.CommonUtils.getMeasurementDate;
import static org.springframework.http.ResponseEntity.*;

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
    public ResponseEntity<String> getBodyInfo(@PathVariable String userId, @PathVariable String date) {

        try {
            BodyInfo bodyInfo = bodyInfoService.get(prepareBodyId(userId, date));

            return ok(bodyInfo == null ? "" : bodyInfo.toString());
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }


    @RequestMapping(value = "/body/{userId}/{date}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBodyInfo(@PathVariable String userId, @PathVariable String date,
                                                 @RequestBody BodyInfo bodyInfo) {
        try {
            bodyInfoService.delete(prepareBodyId(userId, date));
            return ok("User body info has been deleted");
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }

    private MeasurementId prepareBodyId(String userId, String date) {
        return new MeasurementId(decode(userId), getMeasurementDate(date));
    }
}
