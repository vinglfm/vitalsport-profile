package com.vitalsport.profile.web;

import org.testng.annotations.Test;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;

public class BodyInfoEndToEndTest extends BaseEndToEndTest {

    @Test
    public void saveBodyInfo() throws IOException {
        String json = prepareJson("/body.json");

        given().contentType(JSON).body(json)
        .when().post("vitalsport/profile/body/{id}/{date}", "dmluZ2xmbUBnbWFpbC5jb20=", "2013-06-21")
                .then().statusCode(SC_OK);
    }

}
