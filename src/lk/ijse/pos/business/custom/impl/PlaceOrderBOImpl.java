package lk.ijse.pos.business.custom.impl;

import lk.ijse.pos.business.custom.PlaceOrderBO;
import lk.ijse.pos.dao.DaoFactory;
import lk.ijse.pos.dao.custom.PlaceOrderDAO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.entity.Order;
import lk.ijse.pos.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
PlaceOrderDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.ORDER);

    @Override
    public String getItemCode() throws SQLException, ClassNotFoundException {
        String orderId = dao.getOrderId();
        return orderId;
    }

    @Override
    public boolean save(OrderDTO orderDTO) throws Exception {
        ArrayList<OrderDetail> ordDetailList = new ArrayList<>();
        for (OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetailListDTO()){
            ordDetailList.add(new OrderDetail(orderDetailDTO.getOrderId(),
                    orderDetailDTO.getItemCode(),
                    orderDetailDTO.getQty(),
                    orderDetailDTO.getUnitPrice()));
        }
        return dao.save(new Order(orderDTO.getOrderId(),
                            orderDTO.getCustomerId(),
                            orderDTO.getDate(),
                            ordDetailList));
    }

    @Override
    public boolean delete(String t) throws Exception {
        return false;
    }

    @Override
    public boolean update(OrderDTO orderDTO) throws Exception {
        return false;
    }

    @Override
    public ArrayList<OrderDTO> getAll() throws Exception {
        return null;
    }
}
