package com.vitalsport.profile.web;

import com.vitalsport.profile.model.UserInfo;
import com.vitalsport.profile.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping(value = "/profile")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/info/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> saveUserInfo(@PathVariable String userId,
                                                   @RequestBody UserInfo userInfo) {
        try {
            userInfoService.save(userId, userInfo);
            return ok("User info has been saved.");
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }


    @RequestMapping(value = "/info/{userId}", method = RequestMethod.GET)
    public ResponseEntity<String> getUserInfo(@PathVariable String userId) {

        try {
            UserInfo strengthInfo = userInfoService.get(userId);

            return ok(strengthInfo == null ? "" : strengthInfo.toString());
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }


    @RequestMapping(value = "/info/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserInfo(@PathVariable String userId) {
        try {
            userInfoService.delete(userId);
            return ok("User info has been deleted");
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }

}
