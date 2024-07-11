package edu.icet.pos.controller.place_order;

import edu.icet.pos.controller.place_order.custom.PlaceOrderPay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PayController implements PlaceOrderPay {
    @FXML
    private Label dspNetTotal;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnPay;

    @FXML
    private void nameKeyTyped(KeyEvent keyEvent) {
    }

    @FXML
    private void emailKeyTyped(KeyEvent keyEvent) {
    }

    @FXML
    private void btnPayAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
