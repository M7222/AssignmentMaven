package org.example.healthcare.repository;
import java.util.Objects;


import java.util.List;

public interface CrudRepository<T, ID> {
    T create(T entity);
    T getById(ID id);
    List<T> getAll();
    T update(ID id, T entity);
    boolean delete(ID id);

    default void requireNonNull(T entity, String message) {
        if (Objects.isNull(entity)) {
            throw new IllegalArgumentException(message);
        }
    }

}
