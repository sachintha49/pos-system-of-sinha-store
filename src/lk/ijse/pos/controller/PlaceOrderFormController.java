package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.business.BoFactory;
import lk.ijse.pos.business.custom.CustomerBO;
import lk.ijse.pos.business.custom.ItemBO;
import lk.ijse.pos.business.custom.PlaceOrderBO;
import lk.ijse.pos.business.custom.impl.CustomerBOImpl;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.view.tm.CustomerTM;
import lk.ijse.pos.view.tm.PlaceOrderTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlaceOrderFormController {
    public AnchorPane anchorPanePlaceOrderForm;
    public Label lblOrderId;
    public Label lblOrderDate;
    public JFXComboBox cmbCustomerId;
    public JFXComboBox cmbItemCode;
    public JFXTextField txtItemDescription;
    public JFXTextField txtCustomerName;
    public JFXTextField txtItemQtyOnHand;
    public JFXTextField txtItemUnitPrice;
    public JFXTextField txtItemQtyCustomerNeed;
    public JFXTextField txtCustomerAddress;
    public TableView<PlaceOrderTM> tblPlaceOrder;
    public TableColumn colItemCode;
    public TableColumn colItemDescription;
    public TableColumn colQtyCusNeed;
    public TableColumn colItemUnitPrice;
    public TableColumn colPerItemTotal;
    public Label lblAllTotalAmount;

    ArrayList<CustomerDTO> allCustomer = new ArrayList<>();
    ArrayList<ItemDTO> allItem = new ArrayList<>();

    ObservableList<PlaceOrderTM> tableTm = FXCollections.observableArrayList();

    private CustomerBO customerBOImpl = BoFactory.getInstance().getBo(BoFactory.BoType.CUSTOMER);
    private ItemBO itemBOImpl = BoFactory.getInstance().getBo(BoFactory.BoType.ITEM);
    private PlaceOrderBO placeOrderBO = BoFactory.getInstance().getBo(BoFactory.BoType.ORDER);

    public void initialize() throws Exception {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQtyCusNeed.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colPerItemTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        formatDate();
        getAllCustomer();
        getAllItem();
        makeOrderId();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            for (CustomerDTO cusTemp : allCustomer){
                if (newValue == cusTemp.getId()){
                    txtCustomerName.setText(cusTemp.getName());
                    txtCustomerAddress.setText(cusTemp.getAddress());
                }
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            for (ItemDTO itmTemp : allItem){
                if (newValue == itmTemp.getCode()){
                    txtItemDescription.setText(itmTemp.getDescription());
                    txtItemQtyOnHand.setText(String.valueOf(itmTemp.getQtyOnHand()));
                    txtItemUnitPrice.setText(String.valueOf(itmTemp.getUnitPrice()));
                }
            }
        });

        /*tblPlaceOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnRemoveItemsFromTableOnAction(newValue.getCode());
        });*/
    }

    private void makeOrderId() throws SQLException, ClassNotFoundException {
        String itmCode = null;
        int ordCountFromOrderTable = Integer.parseInt(placeOrderBO.getItemCode());
        ordCountFromOrderTable += 1;
        if (ordCountFromOrderTable < 10){
            itmCode = "P00" + ordCountFromOrderTable;
        } else if (ordCountFromOrderTable < 100){
            itmCode = "P0" + ordCountFromOrderTable;
        }
        lblOrderId.setText(itmCode);
        System.out.println(lblOrderId.getText());
    }

    private void getAllItem() throws Exception {
        ObservableList<String> itemList = FXCollections.observableArrayList();
        for (ItemDTO itmDTO : itemBOImpl.getAll()){
            itemList.add(itmDTO.getCode());
            allItem.add(itmDTO);
        }
        cmbItemCode.setItems(itemList);
    }

    private void getAllCustomer() throws Exception {
        ObservableList<String> customerList = FXCollections.observableArrayList();
        for (CustomerDTO cusDTO : customerBOImpl.getAll()){
            customerList.add(cusDTO.getId());
            allCustomer.add(cusDTO);
        }
        cmbCustomerId.setItems(customerList);
    }

    private void formatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        lblOrderDate.setText(sdf.format(new Date()));

    }

    public void btnAddItemToTableOnAction(ActionEvent actionEvent) {
        if (Integer.parseInt(txtItemQtyCustomerNeed.getText()) > Integer.parseInt(txtItemQtyOnHand.getText())){
            System.out.println("Wrong quantity");
            return;
        }

        double unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        int cusNeedQty = Integer.parseInt(txtItemQtyCustomerNeed.getText());
        double totalPrice = unitPrice * cusNeedQty;

        int rowIndex = isAlreadyExists(cmbItemCode.getValue().toString());

        if (rowIndex == -1) {
            tableTm.add(new PlaceOrderTM(cmbItemCode.getValue().toString(),
                    txtItemDescription.getText(),
                    txtItemQtyCustomerNeed.getText(),
                    txtItemUnitPrice.getText(),
                    String.valueOf(totalPrice)));

            tblPlaceOrder.setItems(tableTm);
        }else {
                String qty = tableTm.get(rowIndex).getQty();
                int totalQty = Integer.parseInt(qty) + Integer.parseInt(txtItemQtyCustomerNeed.getText());
            System.out.println(totalQty);
            double totalTempPrice = totalQty * unitPrice;
            if (totalQty > Integer.parseInt(txtItemQtyOnHand.getText())){
                return;
            }
           // tableTm.get(rowIndex).setQty(String.valueOf(totalQty));

            for (PlaceOrderTM temp : tableTm){
                if (temp.getCode().equals(cmbItemCode.getValue().toString())){
                    tableTm.get(rowIndex).setQty(String.valueOf(totalQty));
                    tableTm.get(rowIndex).setTotal(String.valueOf(totalTempPrice));
                    tblPlaceOrder.refresh();
                }
            }

        }
        placeOrderTotalGenerate();
        txtItemQtyCustomerNeed.clear();
    }

    public void placeOrderTotalGenerate(){
        double totalPriceDisplay = 0;
        for (PlaceOrderTM ttlPrice : tableTm){
            totalPriceDisplay += Double.parseDouble(ttlPrice.getTotal());
        }
        lblAllTotalAmount.setText(String.valueOf(totalPriceDisplay));
    }

    private int isAlreadyExists(String itemCode) {
        /*for (PlaceOrderTM tm : tableTm){
            if (itemCode.equals(tm.getCode())){
                return 1;
            }
        }*/
        for (int i = 0; i < tableTm.size(); i++) {
            if (itemCode.equals(tableTm.get(i).getCode())){
                return i;
            }

        }
        return -1;
    }

    public void btnRemoveItemsFromTableOnAction(ActionEvent actionEvent) {
        try {
            String itmCode = tblPlaceOrder.getSelectionModel().selectedItemProperty().get().getCode();
                for (int i = 0; i < tableTm.size(); i++) {
                    if (itmCode.equals(tableTm.get(i).getCode())){
                        tableTm.remove(tableTm.get(i));
                        tblPlaceOrder.refresh();
                        placeOrderTotalGenerate();
                    }
                }
        }catch (Exception e){
            new Alert(Alert.AlertType.WARNING,
                    "Please select a row to make the delete function!", ButtonType.OK).show();
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws Exception {
        String ordId = lblOrderId.getText();
        String customerId = cmbCustomerId.getValue().toString();
        String date = lblOrderDate.getText();
        ArrayList<OrderDetailDTO> orderListDto = new ArrayList<>();

        for (PlaceOrderTM placeOrderTM : tableTm){
            orderListDto.add(new OrderDetailDTO(ordId,
                    placeOrderTM.getCode(),
                    Integer.parseInt(placeOrderTM.getQty()),
                    Double.parseDouble(placeOrderTM.getUnitPrice())));
        }

        OrderDTO orderDTO = new OrderDTO(ordId,customerId,date,orderListDto);
        boolean isAdded = placeOrderBO.save(orderDTO);
        if (isAdded){
            new Alert(Alert.AlertType.WARNING,
                    "Order has been completed", ButtonType.OK).show();
        }
    }

    public void btnBackToMainForm(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) anchorPanePlaceOrderForm.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"))));
        stage.centerOnScreen();
    }
}
