package com.vitalsport.profile.repository;

import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.model.InfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;

public interface BodyInfoRepository extends JpaRepository<BodyInfo, InfoId> {

    @Query("SELECT bi.id.date FROM BodyInfo bi WHERE bi.id.userId = ?1")
    Collection<String> findMeasurementDates(String userId);

    @Query("SELECT DISTINCT EXTRACT (year FROM bi.id.date) as year FROM BodyInfo bi " +
            "WHERE bi.id.userId = ?1 ORDER BY year ASC")
    Collection<Integer> findMeasurementYears(String userId);

    @Query("SELECT DISTINCT EXTRACT (month FROM bi.id.date) as month FROM BodyInfo bi " +
            "WHERE bi.id.userId = ?1 AND bi.id.date >= ?2 AND bi.id.date < ?3 ORDER BY month ASC")
    Collection<Integer> findMeasurementMonth(String userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT EXTRACT (day FROM bi.id.date) as day FROM BodyInfo bi " +
            "WHERE bi.id.userId = ?1 AND bi.id.date >= ?2 AND bi.id.date < ?3 ORDER BY day ASC")
    Collection<Integer> findMeasurementDays(String userId, LocalDate startDate, LocalDate endDate);
}
