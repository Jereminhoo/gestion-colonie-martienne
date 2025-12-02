package be.condorcet.gestioncoloniemartienne.dao;

import java.util.List;

public interface GenericDAO<T, ID> {

    void create(T entity);

    T findById(ID id);

    T update(T entity);

    void delete(T entity);

    List<T> findAll();
}
