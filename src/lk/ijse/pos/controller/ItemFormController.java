package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.pos.business.custom.ItemBO;
import lk.ijse.pos.dataController.DBAccessCode;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.view.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ItemFormController {
    public JFXTextField txtUnitPrice;
    public JFXTextField txtItemCode;
    public JFXTextField txtAtyOnHand;
    public JFXTextField txtItemDescription;
    public TableView<ItemTM> tableItem;
    public TableColumn colItemCode;
    public TableColumn colItemDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colItemDeleteButton;
    public JFXButton btnSaveItemForm;
    public AnchorPane rootItem;

    private ItemBO itemBoImpl = BoFactory.getInstance().getBo(BoFactory.BoType.ITEM);

    public void initialize() throws Exception {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colItemDeleteButton.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadAllItems();

        tableItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                txtItemCode.setText(newValue.getCode());
                txtItemDescription.setText(newValue.getDescription());
                txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
                txtAtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
                btnSaveItemForm.setText("Update");
            }
        });
    }

    private void loadAllItems() throws Exception {
        ObservableList<ItemTM> tmList = FXCollections.observableArrayList();
        for (ItemDTO tempDto : itemBoImpl.getAll()){
            Button btn = new Button("Delete");
            tmList.add(new ItemTM(tempDto.getCode(),
                    tempDto.getDescription(),
                    tempDto.getUnitPrice(),
                    tempDto.getQtyOnHand(),
                    btn)
            );

            btn.setOnAction(event -> {
                try {
                    ButtonType yes = new ButtonType("YES", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.WARNING,
                            "Are you sure want to delete this Item?", yes, no);
                    alert.setTitle("Confirmation!");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(no) == yes) {
                        boolean isDeleted = itemBoImpl.delete(tempDto.getCode());
                        if (isDeleted){
                            new Alert(Alert.AlertType.CONFIRMATION,"Item has been deleted!",ButtonType.OK).show();
                            loadAllItems();
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Something has gone wrong, Please try again!",ButtonType.OK).show();
                        }
                    }

                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }
        tableItem.setItems(tmList);
    }

    public void btnBackToDashboard(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) rootItem.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"))));
        stage.centerOnScreen();
    }

    public void btnAddNewItemOnAction(ActionEvent actionEvent) {
        txtItemCode.clear();
        txtAtyOnHand.clear();
        txtItemDescription.clear();
        txtUnitPrice.clear();
        btnSaveItemForm.setText("Save");
        tableItem.getSelectionModel().clearSelection();
    }

    public void btnSaveItemOnAction(ActionEvent actionEvent) throws Exception {
        if (btnSaveItemForm.getText().equalsIgnoreCase("Save")){
            //Save operation goes here...
            ItemDTO itmDto = new ItemDTO(
                    txtItemCode.getText(),
                    txtItemDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtAtyOnHand.getText()));
            if (itemBoImpl.save(itmDto)){
                new Alert(Alert.AlertType.CONFIRMATION,"Item has been saved", ButtonType.OK).show();
                loadAllItems();
                txtItemCode.clear();
                txtItemDescription.clear();
                txtUnitPrice.clear();
                txtAtyOnHand.clear();
            }else {
                new Alert(Alert.AlertType.WARNING,"Something has gone wrong, please try again!", ButtonType.OK).show();
            }
        }else{
            //Update operation goes here...
            ItemDTO itmDtoUpdate = new ItemDTO(
                    txtItemCode.getText(),
                    txtItemDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtAtyOnHand.getText()));
            if (itemBoImpl.update(itmDtoUpdate)){
                new Alert(Alert.AlertType.CONFIRMATION,"Item has been Updated", ButtonType.OK).show();
                loadAllItems();
                txtItemCode.clear();
                txtItemDescription.clear();
                txtUnitPrice.clear();
                txtAtyOnHand.clear();
            }else {
                new Alert(Alert.AlertType.WARNING,"Something has gone wrong, please try again!", ButtonType.OK).show();
            }
        }
    }
}
