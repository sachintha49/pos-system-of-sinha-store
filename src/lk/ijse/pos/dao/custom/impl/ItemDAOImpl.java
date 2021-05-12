package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.entity.Item;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean save(Item item) throws Exception {
        String saveSql = "INSERT INTO item (code,description,unitPrice,qtyOnHand) VALUES(?,?,?,?)";
        return CrudUtil.execute(saveSql,item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public boolean update(Item item) throws Exception {
        String updateSql = "UPDATE item SET description = ?, unitPrice = ?, qtyOnHand = ? WHERE code = ?";
        return CrudUtil.execute(updateSql,item.getDescription(),item.getUnitPrice(),item.getQtyOnHand(),item.getCode());
    }

    @Override
    public ArrayList<Item> getAll() throws Exception {
        String getAllItmQry = "SELECT * FROM Item";
        ArrayList<Item> itemList = new ArrayList<>();
        ResultSet set = CrudUtil.execute(getAllItmQry);
        while (set.next()){
            itemList.add(new Item(
                    set.getString(1),
                    set.getString(2),
                    set.getDouble(3),
                    set.getInt(4)
            ));
        }
        return itemList;
    }

    @Override
    public boolean delete(String id) throws Exception {
        String delSql = "DELETE FROM item WHERE code=?";
        return CrudUtil.execute(delSql,id);
    }
}
