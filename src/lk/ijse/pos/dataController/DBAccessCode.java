package lk.ijse.pos.dataController;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.dto.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class DBAccessCode {
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        //save customer

        /*PreparedStatement preStmt = DBConnection.getInstance().getConnection().prepareStatement(saveSql);
        preStmt.setString(1,dto.getId());
        preStmt.setString(2,dto.getName());
        preStmt.setString(3,dto.getAddress());
        preStmt.setDouble(4,dto.getSalary());*/
        String saveSql = "INSERT INTO customer(id,name,address,salary) VALUES(?,?,?,?)";
        return CrudUtil.execute(saveSql,dto.getId(),dto.getName(),dto.getAddress(),dto.getSalary());
    }

    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        //update customer

        /*PreparedStatement preStmt = DBConnection.getInstance().getConnection().prepareStatement(updateSql);
        preStmt.setString(1,dto.getName());
        preStmt.setString(2,dto.getAddress());
        preStmt.setDouble(3,dto.getSalary());
        preStmt.setString(4,dto.getId());*/
        String updateSql = "UPDATE customer SET name=?,address=?,salary=? WHERE id=?";
        return CrudUtil.execute(updateSql,dto.getName(),dto.getAddress(),dto.getSalary(),dto.getId());
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws ClassNotFoundException, SQLException {
                String selectSql = "SELECT * FROM customer";
        ResultSet dataSet = CrudUtil.execute(selectSql);
        ArrayList<CustomerDTO> list = new ArrayList<>();
        while (dataSet.next()){
            list.add(new CustomerDTO(
                    dataSet.getString(1),
                    dataSet.getString(2),
                    dataSet.getString(3),
                    dataSet.getDouble(4)
            ));
        }
        return list;
    }

    public boolean deleteCustomer(String id) throws ClassNotFoundException, SQLException {
        String deleteSql = "DELETE FROM customer WHERE id=?";

        return CrudUtil.execute(deleteSql,id);
    }
}
