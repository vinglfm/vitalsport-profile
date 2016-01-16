package com.vitalsport.profile.web;

import com.vitalsport.profile.repository.BodyInfoRepository;
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

public class BodyInfoEndToEndTest extends BaseEndToEndTest {
    private static final String BASE_URL = "vitalsport/profile/body/";

    private final String baseBodyJson = prepareJson("/baseBody.json");
    private final String updatedBodyJson = prepareJson("/updatedBody.json");

    @Autowired
    private BodyInfoRepository bodyInfoRepository;

    @Test
    public void postReturnsBadRequestOnInvalidDate() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, invalidFormattedDate)
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("date = 20130621 has an invalid format."));
    }

    @Test
    public void postReturnsOkOnSuccess() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
    }

    @Test
    public void postUpdatesBodyInfo() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);
        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo(baseBodyJson));

        given().contentType(JSON).body(updatedBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);
        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo(updatedBodyJson));
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
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);

        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate2)
                .then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void getReturnsBodyInfoForCorrectUserIdAndMeasurementDate() throws IOException {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);

        when().get(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo(baseBodyJson));
    }

    @Test
    public void deleteReturnsBadRequestOnBadFormattedDate() {
        when().delete(BASE_URL + "{id}/{date}", encodedUserId, invalidFormattedDate)
                .then().statusCode(SC_BAD_REQUEST).body(equalTo("date = 20130621 has an invalid format."));
    }

    @Test
    public void deleteReturnsOkOnSuccess() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1);

        when().delete(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("Delete has been applied."));
    }

    @Test
    public void getAllMeasurementDatesReturnsEmptyResultForAbsentUserId() {
        when().get(BASE_URL + "{userId}/allMeasurementDates", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo("[]"));
    }

    @Test
    public void getAllMeasurementDatesReturnsDatesForValidUser() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate2)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        when().get(BASE_URL + "{userId}/allMeasurementDates", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo("[" + measurementDate1 + ", " + measurementDate2 + "]"));
    }

    @Test
    public void getAllMeasurementYearsReturnsEmptyResultForAbsentUserId() {
        when().get(BASE_URL + "{userId}/measurementYears", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo("[]"));
    }

    @Test
    public void getAllMeasurementYearsReturnsYearsForValidUser() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate2)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        when().get(BASE_URL + "{userId}/measurementYears", encodedUserId)
                .then().statusCode(SC_OK).body(equalTo("[" + year +"]"));
    }

    @Test
    public void getMeasurementMonthsReturnsBadRequestForBadFormattedYear() {
        String badFormattedYear = "asd";
        when().get(BASE_URL + "{userId}/{year}/measurementMonths", encodedUserId, badFormattedYear)
                .then().statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void getMeasurementMonthsReturnsEmptyResultForUserIdAndAbsentYear() {
        when().get(BASE_URL + "{userId}/{year}/measurementMonths", encodedUserId, otherYear)
                .then().statusCode(SC_OK).body(equalTo("[]"));
    }

    @Test
    public void getMeasurementMonthsReturnsEmptyResultForAbsentUserId() {
        when().get(BASE_URL + "{userId}/{year}/measurementMonths", encodedUserId, year)
                .then().statusCode(SC_OK).body(equalTo("[]"));
    }

    @Test
    public void getMeasurementMonthsReturnsMonthsForValidInputData() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate2)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        when().get(BASE_URL + "{userId}/{year}/measurementMonths", encodedUserId, year)
                .then().statusCode(SC_OK).body(equalTo("[" + month1 + "," + month2 + "]"));
    }

    @Test
    public void getMeasurementMonthsNotReturnsDuplicateMonth() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate2)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, localMeasurementDate2.plusDays(1).toString())
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        when().get(BASE_URL + "{userId}/{year}/measurementMonths", encodedUserId, year)
                .then().statusCode(SC_OK).body(equalTo("[" + month1 + "," + month2 + "]"));
    }

    @Test
    public void getMeasurementDaysReturnsEmptyResultForCorrectUserIdAndYearWithAbsentMonth() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate2)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        when().get(BASE_URL + "{userId}/{year}/{month}/measurementDays", encodedUserId, year, otherMonth)
                .then().statusCode(SC_OK).body(equalTo("[]"));
    }

    @Test
    public void getMeasurementDaysReturnsEmptyResultForCorrectUserIdAndWithAbsentYear() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate2)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        when().get(BASE_URL + "{userId}/{year}/{month}/measurementDays", encodedUserId, otherYear, month1)
                .then().statusCode(SC_OK).body(equalTo("[]"));
    }

    @Test
    public void getMeasurementDaysReturnsEmptyResultForAbsentUserId() {
        when().get(BASE_URL + "{userId}/{year}/{month}/measurementDays", encodedUserId, year, month1)
                .then().statusCode(SC_OK).body(equalTo("[]"));
    }

    @Test
    public void getMeasurementDaysReturnsCorrectDayResultForValidInputData() {
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate1)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        given().contentType(JSON).body(baseBodyJson)
                .when().post(BASE_URL + "{id}/{date}", encodedUserId, measurementDate2)
                .then().statusCode(SC_OK).body(equalTo("User body info has been saved."));
        when().get(BASE_URL + "{userId}/{year}/{month}/measurementDays", encodedUserId, year, month1)
                .then().statusCode(SC_OK).body(equalTo("[" + day1 + "]"));
    }

    @BeforeMethod
    private void cleanUp() {
        bodyInfoRepository.deleteAll();
    }
}
