package com.vitalsport.profile.service;

import com.vitalsport.profile.model.InfoId;

import java.time.LocalDate;

public abstract class BaseServiceTest {
    protected static final LocalDate localDate = LocalDate.parse("2015-12-16");
    protected static final String userId = "userId";

    protected static InfoId prepareInfoId(LocalDate localDate, String userId) {
        return new InfoId(userId, localDate);
    }


}
