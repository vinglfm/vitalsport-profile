package com.vitalsport.profile.web;

import com.vitalsport.profile.model.BodyId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.service.BasicBodyInfoService;
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
public class ProfileController {

    @Autowired
    private BasicBodyInfoService basicBodyInfoService;

    @RequestMapping(value = "/body/{userId}/{date}", method = RequestMethod.POST)
    public ResponseEntity<String> saveBodyInfo(@PathVariable String userId, @PathVariable String date,
                                               @RequestBody BodyInfo bodyInfo) {
        try {
            basicBodyInfoService.saveBodyInfo(prepareBodyId(userId, date), bodyInfo);
            return ok("User body info has been saved.");
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        }
    }


    @RequestMapping(value = "/body/{userId}/{date}", method = RequestMethod.GET)
    public ResponseEntity<String> getBodyInfo(@PathVariable String userId, @PathVariable String date) {

        try {
            BodyInfo bodyInfo = basicBodyInfoService.getBodyInfo(prepareBodyId(userId, date));

            return ok(bodyInfo == null ? "" : bodyInfo.toString());
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        }
    }


    @RequestMapping(value = "/body/{userId}/{date}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBodyInfo(@PathVariable String userId, @PathVariable String date,
                                                 @RequestBody BodyInfo bodyInfo) {
        try {
            basicBodyInfoService.deleteBodyInfo(prepareBodyId(userId, date));
            return ok("User body info has been deleted");
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        }
    }

    private BodyId prepareBodyId(String userId, String date) {
        return new BodyId(decode(userId), getMeasurementDate(date));
    }
}
