package com.vitalsport.profile.web;

import com.vitalsport.profile.repository.MeasurementRepository;
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

public class MeasurementEndToEndTest extends BaseEndToEndTest {
    private static final String BASE_URL = "vitalsport/profile/measurements/";
    private static final String BASE_USER_URL = "vitalsport/profile/user/";

    private final String baseMeasurementsJson = prepareJson("/baseMeasurements.json");
    private final String updatedMeasurementsJson = prepareJson("/updatedMeasurements.json");
    private final String baseUserJson = prepareJson("/baseUser.json");

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private MeasurementRepository measurementsRepository;

    @Test
    public void getReturnsDefaultMeasurements() {
        given().contentType(JSON).body(baseUserJson)
                .when().post(BASE_USER_URL + "{userId}", encodedUserId);
        when().get(BASE_URL + "{userId}", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo(baseMeasurementsJson));
    }

    @Test
    public void putReturnsOkOnSuccess() {
        given().contentType(JSON).body(baseMeasurementsJson)
                .when().put(BASE_URL + "{userId}", encodedUserId)
                .then().statusCode(SC_OK);
    }

    @Test
    public void putUpdatesMeasurements() {
        given().contentType(JSON).body(baseMeasurementsJson)
                .when().put(BASE_URL + "{userId}", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo("User measurements has been saved."));
        given().contentType(JSON).body(updatedMeasurementsJson)
                .when().put(BASE_URL + "{userId}", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo("User measurements has been saved."));
        when().get(BASE_URL + "{userId}", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo(updatedMeasurementsJson));
    }

    @Test(enabled = false)
    public void putNotInsertMeasurements() {

    }

    @Test
    public void getReturnsNotFoundForAbsentUser() {
        when().get(BASE_URL + "{userId}", encodedUserId)
                .then().statusCode(SC_NOT_FOUND);
    }

    @BeforeMethod
    private void cleanUp() {
        userInfoRepository.deleteAll();
        measurementsRepository.deleteAll();
    }
}
