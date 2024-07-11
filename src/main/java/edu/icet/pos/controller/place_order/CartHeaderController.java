package edu.icet.pos.controller.place_order;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class CartHeaderController implements Initializable {
    @FXML
    private Label dspInvoiceNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
