package com.vitalsport.profile.web;

import com.vitalsport.profile.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class UserInfoEndToEndTest extends BaseEndToEndTest {
    private static final String BASE_URL = "vitalsport/profile/user/";

    private final String baseUserJson = prepareJson("/baseUser.json");
    private final String updatedUserJson = prepareJson("/updatedUser.json");

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    public void postReturnsOkOnSuccess() {
        given().contentType(JSON).body(baseUserJson)
                .when().post(BASE_URL + "{id}", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo("User info has been saved."));
    }

    @Test
    public void postUpdatesUserInfo() {
        given().contentType(JSON).body(baseUserJson)
                .when().post(BASE_URL + "{id}", encodedUserId);
        when().get(BASE_URL + "{id}", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo(baseUserJson));

        given().contentType(JSON).body(updatedUserJson)
                .when().post(BASE_URL + "{id}", encodedUserId);
        when().get(BASE_URL + "{id}", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo(updatedUserJson));
    }

    @Test
    public void getReturnsNotFoundForAbsentUserId() {
        when().get(BASE_URL + "{id}", encodedUserId)
                .then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void getReturnsUserInfoForCorrectUser() {
        given().contentType(JSON).body(baseUserJson)
                .when().post(BASE_URL + "{id}", encodedUserId);

        when().get(BASE_URL + "{id}", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo(baseUserJson));
    }

    @Test
    public void deleteReturnsOkOnSuccess() {
        given().contentType(JSON).body(baseUserJson)
                .when().post(BASE_URL + "{id}", encodedUserId);

        when().delete(BASE_URL + "{id}", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo("Delete has been applied."));
    }

    @BeforeMethod
    private void cleanUp() {
        userInfoRepository.deleteAll();
    }
}
