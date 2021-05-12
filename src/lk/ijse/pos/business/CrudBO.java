package lk.ijse.pos.business;

import lk.ijse.pos.dto.ItemDTO;

import java.util.ArrayList;

public interface CrudBO<T,String> {
    public boolean save(T t) throws Exception;
    public boolean delete(String t) throws Exception;
    public boolean update(T t) throws Exception;
    public ArrayList<T> getAll() throws Exception;
}
