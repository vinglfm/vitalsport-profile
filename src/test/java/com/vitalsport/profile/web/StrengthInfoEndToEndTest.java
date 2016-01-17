package com.vitalsport.profile.web;

import com.vitalsport.profile.repository.StrengthInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
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
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("date = 20130621 has an invalid format."));
    }

    @Test
    public void postReturnsOkOnSuccess() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("User strength info has been saved."));
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
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("date = 20130621 has an invalid format."));
    }

    @Test
    public void getReturnsNotFoundForAbsentUserId() {
        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void getReturnsNotFoundForCorrectUserIdAndAbsentMeasurementDate() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);

        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate2)
                .then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void getReturnsStrengthInfoForCorrectUserIdAndMeasurementDate() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);

        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo(baseStrengthJson));
    }

    @Test
    public void deleteReturnsBadRequestOnBadFormattedDate() {
        when().delete(BASE_URL + "{id}/{date}", encodedUserId, invalidFormattedDate)
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("date = 20130621 has an invalid format."));
    }

    @Test
    public void deleteReturnsOkOnSuccess() {
        given().contentType(JSON).body(baseStrengthJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);

        when().delete(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("Delete has been applied."));
    }


    @BeforeMethod
    private void cleanUp() {
        strengthInfoRepository.deleteAll();
    }
}
