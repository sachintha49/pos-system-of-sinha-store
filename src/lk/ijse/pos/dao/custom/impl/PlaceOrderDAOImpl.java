package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.PlaceOrderDAO;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.entity.Order;
import lk.ijse.pos.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        String getIdQry = "SELECT COUNT(id) FROM orders";
        ResultSet orderId = CrudUtil.execute(getIdQry);
        String PlaceOrderId = null;
        while (orderId.next()){
            PlaceOrderId = orderId.getString(1);
        }
        return PlaceOrderId;
    }

    @Override
    public boolean save(Order order) throws Exception {
        try {
            boolean itmUpdated = false;
            boolean isAdded = false;
            CrudUtil.getConnection().setAutoCommit(false);
            String insertOrderSql = "INSERT INTO orders (id,date,customerId) VALUES(?,?,?)";
            boolean orderIsAdded = CrudUtil.execute(insertOrderSql,order.getOrderId(),order.getDate(),order.getCustomerId());
            if (orderIsAdded){
                String insertOrderDetailSql = "INSERT INTO orderdetail (orderId,itemCode,qty,unitPrice) VALUES (?,?,?,?)";
                for (OrderDetail orderDetail : order.getOrderDetailList()){
                    isAdded = CrudUtil.execute(insertOrderDetailSql,orderDetail.getOrderId(),
                            orderDetail.getItemCode(),
                            orderDetail.getQty(),
                            orderDetail.getUnitPrice());
                }

                if (isAdded){
                    String updateItmQry = "UPDATE item SET qtyOnHand = qtyOnHand - ? WHERE code = ?";
                    for (OrderDetail orderDetail : order.getOrderDetailList()){
                        itmUpdated = CrudUtil.execute(updateItmQry,orderDetail.getQty(),orderDetail.getItemCode());
                    }
                    if (itmUpdated){
                        CrudUtil.getConnection().commit();
                        return true;
                    }
                }
            }
            CrudUtil.getConnection().rollback();
            return false;
        }finally {
            CrudUtil.getConnection().setAutoCommit(true);
        }
    }

    @Override
    public boolean update(Order order) throws Exception {
        return false;
    }

    @Override
    public ArrayList<Order> getAll() throws Exception {
        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }
}
