package com.vitalsport.profile.web;

import com.vitalsport.profile.repository.BodyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class BodyInfoEndToEndTest extends BaseEndToEndTest {
    private static final String BASE_URL = "vitalsport/profile/body/";

    private final String bodyJson = prepareJson("/body.json");

    @Autowired
    private BodyInfoRepository bodyInfoRepository;

    @Test
    public void postReturnsBadRequestOnNonValidDate() {
        given().contentType(JSON).body(bodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, invalidFormattedDate)
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("date = 20130621 has an invalid format."));
    }

    @Test
    public void postReturnsStatusOkOnSuccess() {
        given().contentType(JSON).body(bodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
    }

    @Test
    public void getReturnsBadRequestOnNonValidDate() {
        when().get(BASE_URL + "{id}/{date}", encodedUserId, invalidFormattedDate)
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("date = 20130621 has an invalid format."));
    }

    @Test
    public void getReturnsNotFoundForAbsentUserId() {
        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate)
                .then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void getReturnsNotFoundForCorrectUserIdAndAbsentMeasurementDate() {
        given().contentType(JSON).body(bodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate);

        when().get(BASE_URL + "{id}/{date}", encodedUserId, otherMeasurementDate)
                .then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void getReturnsBodyInfoForCorrectUserIdAndMeasurementDate() throws IOException {
        given().contentType(JSON).body(bodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate)
                .then().statusCode(SC_OK);

        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate)
                .then().statusCode(SC_OK);
    }

    @Test
    public void deleteReturnsBadRequestOnNonValidDate() {
        when().delete(BASE_URL + "{id}/{date}", encodedUserId, invalidFormattedDate)
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("date = 20130621 has an invalid format."));
    }

    @Test
    public void deleteReturnsStatusOkOnSuccess() {
        given().contentType(JSON).body(bodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate);

        when().delete(BASE_URL + "{id}/{date}", encodedUserId, measurementDate)
                .then().statusCode(SC_OK).body(equalTo("Delete has been applied."));
    }

    @BeforeMethod
    private void cleanUp() {
        bodyInfoRepository.deleteAll();
    }
}
