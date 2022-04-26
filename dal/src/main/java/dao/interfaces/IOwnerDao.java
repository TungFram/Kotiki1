package dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IOwnerDao<E, Id extends Serializable> {
    
    public void persist(E entity);

    public void update(E entity);

    public E findById(Id id);

    public List<E> findAll();

    public void delete(E entity);

    public void deleteAll();}
