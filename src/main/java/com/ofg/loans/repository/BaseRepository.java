package com.ofg.loans.repository;

import java.util.Collection;

/**
 * Created by zhabba on 18.03.16.
 */
public interface BaseRepository<T> {
    T save(T entity);
    T get(Long id);
    T update(Long id, T entity);
    T delete(Long id);
    Collection<T> getAll();
}
