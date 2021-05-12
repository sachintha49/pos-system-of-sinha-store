package lk.ijse.pos.dto;

import java.util.ArrayList;

public class OrderDTO {
    private String orderId;
    private String customerId;
    private String date;
    private ArrayList<OrderDetailDTO> orderDetailListDTO;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String customerId, String date, ArrayList<OrderDetailDTO> orderDetailListDTO) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.date = date;
        this.orderDetailListDTO = orderDetailListDTO;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<OrderDetailDTO> getOrderDetailListDTO() {
        return orderDetailListDTO;
    }

    public void setOrderDetailListDTO(ArrayList<OrderDetailDTO> orderDetailListDTO) {
        this.orderDetailListDTO = orderDetailListDTO;
    }
}
