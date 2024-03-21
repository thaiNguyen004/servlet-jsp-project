package org.servlet.assignment.catalog.dao;

import java.util.List;

public interface Dao <T, ID> {

    public List<T> findAll(int limit, int offset);
    public T findById(ID id);
    public T save(T entity);
    public void updateById(T patch, ID id);
}