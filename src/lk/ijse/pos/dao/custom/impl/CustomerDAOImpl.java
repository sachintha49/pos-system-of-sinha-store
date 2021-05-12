package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.entity.Customer;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer customer) throws Exception {
        String saveSql = "INSERT INTO customer(id,name,address,salary) VALUES(?,?,?,?)";
        return CrudUtil.execute(saveSql,customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary());
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        String updateSql = "UPDATE customer SET name=?,address=?,salary=? WHERE id=?";
        return CrudUtil.execute(updateSql,customer.getName(),customer.getAddress(),customer.getSalary(),customer.getId());
    }

    @Override
    public ArrayList<Customer> getAll() throws Exception {
        String selectSql = "SELECT * FROM customer";
        ResultSet dataSet = CrudUtil.execute(selectSql);
        ArrayList<Customer> list = new ArrayList<>();
        while (dataSet.next()){
            list.add(new Customer(
                    dataSet.getString(1),
                    dataSet.getString(2),
                    dataSet.getString(3),
                    dataSet.getDouble(4)
            ));
        }
        return list;
    }

    @Override
    public boolean delete(String id) throws Exception {
        String deleteSql = "DELETE FROM customer WHERE id=?";
        return CrudUtil.execute(deleteSql,id);
    }
}
