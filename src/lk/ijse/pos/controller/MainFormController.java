package lk.ijse.pos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class MainFormController {
    public AnchorPane dashboardUiContainer;

    public void navigateToCustomerForm(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) dashboardUiContainer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"))));
        stage.centerOnScreen();
    }

    public void navigateToItemForm(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) dashboardUiContainer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ItemForm.fxml"))));
        stage.centerOnScreen();
    }

    public void navigateToPlaceOrderForm(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) dashboardUiContainer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"))));
        stage.centerOnScreen();
    }
}
