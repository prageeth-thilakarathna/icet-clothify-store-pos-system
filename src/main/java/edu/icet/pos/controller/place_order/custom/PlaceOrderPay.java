package edu.icet.pos.controller.place_order.custom;

import javafx.fxml.Initializable;

public interface PlaceOrderPay extends Initializable {
    boolean isEmpty();

    void cancelInputs();

    void setNetTotal();

    void validateInputs();
}
