package com.vitalsport.profile.service;

import com.vitalsport.profile.model.InfoId;

import java.time.LocalDate;

import static java.time.LocalDate.parse;

public abstract class BaseServiceTest {

    protected static final String userId1 = "userId1";
    protected static final String userId2 = "userId2";

    protected static final String date1 = "2015-12-16";
    protected static final LocalDate localDate1 = parse(date1);
    protected static final String date2 = "2015-12-19";
    protected static final LocalDate localDate2 = parse(date2);
    protected static final String date3 = "2015-10-25";
    protected static final LocalDate localDate3 = parse(date3);
    protected static final String date4 = "2014-06-05";
    protected static final LocalDate localDate4 = parse(date4);

    protected static InfoId prepareInfoId(String userId, LocalDate localDate) {
        return new InfoId(userId, localDate);
    }
}
