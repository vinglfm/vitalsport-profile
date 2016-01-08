package com.vitalsport.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vitalsport.profile.common.LocalDatePersistenceConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class InfoId implements Serializable {

    private String userId;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate date;
}
