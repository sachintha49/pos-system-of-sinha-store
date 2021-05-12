package lk.ijse.pos.entity;

import lk.ijse.pos.dto.OrderDetailDTO;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String customerId;
    private String date;
    private ArrayList<OrderDetail> orderDetailList;

    public Order() {
    }

    public Order(String orderId, String customerId, String date, ArrayList<OrderDetail> orderDetailList) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.date = date;
        this.orderDetailList = orderDetailList;
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

    public ArrayList<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(ArrayList<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", date='" + date + '\'' +
                ", orderDetailList=" + orderDetailList +
                '}';
    }
}
