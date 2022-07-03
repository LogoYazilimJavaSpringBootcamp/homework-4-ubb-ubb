package com.homeworkfour.persistence.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface for DAO classes.
 * @param <T> Employee, however other classes may be used.
 */

public interface EmployeeDao<T>{

    T  get(long id);
    List<T> getAll();
    T save(T t);
    T update(T t, long id);
    void delete(long id);
}
