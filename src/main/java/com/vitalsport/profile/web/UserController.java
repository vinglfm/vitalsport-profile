package com.vitalsport.profile.web;

import com.vitalsport.profile.model.UserInfo;
import com.vitalsport.profile.service.info.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.vitalsport.profile.common.CommonUtils.decode;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> saveUserInfo(@PathVariable String userId,
                                                   @RequestBody UserInfo userInfo) {
        try {
            userInfoService.save(decode(userId), userInfo);
            return ok("User info has been saved.");
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInfo(@PathVariable String userId) {

        try {
            UserInfo userInfo = userInfoService.get(decode(userId));

            return userInfo == null ?
                    status(NOT_FOUND).body("User info wasn't found for userId = " + userId)
                    : ok(userInfo);
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserInfo(@PathVariable String userId) {
        try {
            userInfoService.delete(decode(userId));
            return ok("Delete has been applied.");
        } catch (EmptyResultDataAccessException exception) {
            return status(NOT_FOUND).body(String.format("User info for userId = %s wasn't found.", userId));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.getMessage());
        }
    }

}
