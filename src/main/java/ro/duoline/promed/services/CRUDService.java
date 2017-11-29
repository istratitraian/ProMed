package ro.duoline.promed.services;

import java.util.List;

/**
 * Created by jt on 11/14/15.
 * @param <T>
 */
public interface CRUDService<T> {
    List<T> listAll();

    T getById(Integer id);

    T saveOrUpdate(T domainObject);

    void delete(Integer id);
}
