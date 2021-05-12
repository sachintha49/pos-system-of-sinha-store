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
import lk.ijse.pos.business.custom.CustomerBO;
import lk.ijse.pos.business.custom.impl.CustomerBOImpl;
import lk.ijse.pos.dataController.DBAccessCode;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.view.tm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane customerUiContainer;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerSalary;
    public JFXTextField txtCustomerAddress;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerSalary;
    public TableColumn colCustomerDeleteBtn;
    public JFXButton btnSave;

    private CustomerBO customerBOImpl = BoFactory.getInstance().getBo(BoFactory.BoType.CUSTOMER);

    public void initialize() throws Exception {
        //customerBOImpl = BoFactory.getInstance().getBo(BoFactory.BoType.CUSTOMER);

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCustomerDeleteBtn.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadAllCustomer();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                txtCustomerId.setText(newValue.getId());
                txtCustomerName.setText(newValue.getName());
                txtCustomerAddress.setText(newValue.getAddress());
                txtCustomerSalary.setText(String.valueOf(newValue.getSalary()));
                btnSave.setText("Update");
            }
        });
    }

    private void loadAllCustomer() throws Exception {
        ObservableList<CustomerTM> tm = FXCollections.observableArrayList();
        for (CustomerDTO tempDto : customerBOImpl.getAll()){
            Button deleteBtn = new Button("Delete");
            tm.add(new CustomerTM(tempDto.getId(),
                    tempDto.getName(),
                    tempDto.getAddress(),
                    tempDto.getSalary(),
                    deleteBtn));

            deleteBtn.setOnAction(e->{
                try {

                    ButtonType yes = new ButtonType("YES", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.WARNING,
                            "Are you sure want to delete this customer?", yes, no);
                    alert.setTitle("Confirmation!");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.orElse(no) == yes) {
                        boolean isDeleted = customerBOImpl.delete(tempDto.getId());
                        if (isDeleted){
                            new Alert(Alert.AlertType.CONFIRMATION,"Customer has been deleted!",ButtonType.OK).show();
                            loadAllCustomer();
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

        tblCustomer.setItems(tm);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws Exception {

        if (btnSave.getText().equalsIgnoreCase("Save")){
            //save
            CustomerDTO c1 = new CustomerDTO(
                    txtCustomerId.getText(),
                    txtCustomerName.getText(),
                    txtCustomerAddress.getText(),
                    Double.parseDouble(txtCustomerSalary.getText())
            );
           // DBAccessCode code = new DBAccessCode();
            if (customerBOImpl.save(c1)){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer has been saved", ButtonType.OK).show();
                loadAllCustomer();
            }else {
                new Alert(Alert.AlertType.WARNING,"Something has gone wrong, please try again!", ButtonType.OK).show();
            }
        }else{
            //update
            CustomerDTO c1 = new CustomerDTO(
                    txtCustomerId.getText(),
                    txtCustomerName.getText(),
                    txtCustomerAddress.getText(),
                    Double.parseDouble(txtCustomerSalary.getText())
            );
            //DBAccessCode code = new DBAccessCode();
            if (customerBOImpl.update(c1)){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer has been updated", ButtonType.OK).show();
                loadAllCustomer();
            }else {
                new Alert(Alert.AlertType.WARNING,"Something has gone wrong, please try again!", ButtonType.OK).show();
            }
        }
    }

    public void btnRefreshFormOnAction(ActionEvent actionEvent) {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerSalary.clear();
        btnSave.setText("Save");
    }

    public void btnBackToDashboard(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) customerUiContainer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"))));
        stage.centerOnScreen();
    }
}
