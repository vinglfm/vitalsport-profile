package com.vitalsport.profile.service.info;

import java.io.Serializable;

public interface LatestMeasurementProvider<K extends Serializable, T> {
    T getLatest(K key);
}
