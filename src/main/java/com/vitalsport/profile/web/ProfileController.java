package com.vitalsport.profile.web;

import com.vitalsport.profile.common.CommonUtils;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.service.BasicBodyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.vitalsport.profile.common.CommonUtils.getMeasurementDate;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

    @Autowired
    private BasicBodyInfoService basicBodyInfoService;

    @RequestMapping(value = "/body/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> saveBodyInfo(@PathVariable String userId, @RequestBody BodyInfo bodyInfo) {
        basicBodyInfoService.saveBodyInfo(userId, bodyInfo);
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/body/{userId}/{date}", method = RequestMethod.GET)
    public ResponseEntity<BodyInfo> getBodyInfo(@PathVariable String userId, @PathVariable String date) {
        BodyInfo bodyInfo = basicBodyInfoService.getBodyInfo(userId, getMeasurementDate(date));
        return ResponseEntity.ok(bodyInfo);
    }

    @RequestMapping(value = "/body/{userId}/{date}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateBodyInfo(@PathVariable String userId, @PathVariable String date,
                                                 @RequestBody BodyInfo bodyInfo) {
        basicBodyInfoService.updateBodyInfo(userId, getMeasurementDate(date));
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/body/{userId}/{date}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBodyInfo(@PathVariable String userId, @PathVariable String date,
                                                 @RequestBody BodyInfo bodyInfo) {
        basicBodyInfoService.deleteBodyInfo(userId, getMeasurementDate(date));
        return ResponseEntity.ok("");
    }
}
