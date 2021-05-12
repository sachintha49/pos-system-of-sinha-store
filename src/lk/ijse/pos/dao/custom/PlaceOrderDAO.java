package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.Order;

import java.sql.SQLException;

public interface PlaceOrderDAO extends CrudDAO<Order,String> {
    public String getOrderId() throws SQLException, ClassNotFoundException;
}
