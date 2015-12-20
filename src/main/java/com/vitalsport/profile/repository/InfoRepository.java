package com.vitalsport.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface InfoRepository<K extends Serializable, T> extends JpaRepository<T, K> {
}
