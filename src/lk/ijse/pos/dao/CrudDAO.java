package lk.ijse.pos.dao;

import lk.ijse.pos.entity.Item;

import java.util.ArrayList;

public interface CrudDAO<T,ID> {
    public boolean save(T t) throws Exception;
    public boolean update(T t) throws Exception;
    public ArrayList<T> getAll() throws Exception;
    public boolean delete(String id) throws Exception;
}
