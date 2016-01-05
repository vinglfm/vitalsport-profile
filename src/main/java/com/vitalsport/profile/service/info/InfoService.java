package com.vitalsport.profile.service.info;

import java.io.Serializable;

public interface InfoService<K extends Serializable, T> {
    void save(K id, T bodyInfo);

    T get(K id);

    void delete(K id);
}
