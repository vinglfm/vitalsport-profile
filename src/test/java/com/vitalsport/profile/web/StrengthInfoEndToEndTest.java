package com.vitalsport.profile.web;

import com.vitalsport.profile.repository.StrengthInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class StrengthInfoEndToEndTest extends BaseEndToEndTest {
    private static final String BASE_URL = "vitalsport/profile/strength/";

    private final String baseStrengthJson = prepareJson("/baseStrength.json");
    private final String updatedStrengthJson = prepareJson("/updatedStrength.json");

    @Autowired
    private StrengthInfoRepository strengthInfoRepository;

    @Test
    public void postReturnsBadRequestOnInvalidDate() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, invalidFormattedDate)
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("{\"logref\":\"Text '20130621' could not be parsed at index 0\",\"message\":\"DateTimeParseException\",\"links\":[]}"));
    }

    @Test
    public void postReturnsOkOnSuccess() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_NO_CONTENT);
    }

    @Test
    public void postUpdatesStrengthInfo() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);
        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo(baseStrengthJson));

        given().contentType(JSON).body(updatedStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);
        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo(updatedStrengthJson));
    }

    @Test
    public void getReturnsBadRequestOnInvalidDate() {
        when().get(BASE_URL + "{id}/{date}", encodedUserId, invalidFormattedDate)
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("{\"logref\":\"Text '20130621' could not be parsed at index 0\",\"message\":\"DateTimeParseException\",\"links\":[]}"));
    }

    @Test
    public void getReturnsNotFoundForAbsentUserId() {
        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_NOT_FOUND).body(equalTo("{\"data\":\"Strength info wasn't found for userId = dmluZ2xmbUBnbWFpbC5jb20= and date = 2013-06-15\"}"));
    }

    @Test
    public void getReturnsNotFoundForCorrectUserIdAndAbsentMeasurementDate() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);

        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate2)
                .then().statusCode(SC_NOT_FOUND).body(equalTo("{\"data\":\"Strength info wasn't found for userId = dmluZ2xmbUBnbWFpbC5jb20= and date = 2013-07-23\"}"));
    }

    @Test
    public void getReturnsStrengthInfoForCorrectUserIdAndMeasurementDate() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);

        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo(baseStrengthJson));
    }

    @Test
    public void getLatestStrengthInfoForAbsentUserId() {
        when().get(BASE_URL + "{id}", encodedUserId)
                .then().statusCode(SC_NOT_FOUND).body(equalTo("{\"data\":\"Strength info wasn't found for userId = dmluZ2xmbUBnbWFpbC5jb20=\"}"));
    }

    @Test
    public void getLatestStrengthInfoForCorrectUserId() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);

        when().get(BASE_URL + "{id}", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo(baseStrengthJson));
    }

    @Test
    public void deleteReturnsBadRequestOnBadFormattedDate() {
        when().delete(BASE_URL + "{id}/{date}", encodedUserId, invalidFormattedDate)
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("{\"logref\":\"Text '20130621' could not be parsed at index 0\",\"message\":\"DateTimeParseException\",\"links\":[]}"));
    }

    @Test
    public void deleteReturnsOkOnSuccess() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);

        when().delete(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_NO_CONTENT);
    }


    @BeforeMethod
    private void cleanUp() {
        strengthInfoRepository.deleteAll();
    }
}
