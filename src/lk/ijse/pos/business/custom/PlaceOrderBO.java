package lk.ijse.pos.business.custom;

import lk.ijse.pos.business.CrudBO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends CrudBO<OrderDTO,String> {
    public String getItemCode() throws SQLException, ClassNotFoundException;

}
