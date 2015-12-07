package com.vitalsport.profile.common;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.sql.Date.valueOf;

@Converter
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate entityValue) {
        return entityValue == null ? null : valueOf(entityValue);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbValue) {
        return dbValue == null ? null : dbValue.toLocalDate();
    }
}