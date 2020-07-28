package com.confmanage.DAOs;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();

    T get(Integer id);

    Integer save(T obj);

    void update(T obj);

    void delete(T obj);
}
