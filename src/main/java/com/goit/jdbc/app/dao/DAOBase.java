package com.goit.jdbc.app.dao;

import java.util.List;

public interface DAOBase<E, K> {
    void add(E e);
    E getById(K id);
    List<E> getAll();
    void update(E e);
    void delete(E e);
}
