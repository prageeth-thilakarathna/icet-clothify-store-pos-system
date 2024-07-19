package edu.icet.pos.controller.place_order.custom;

import javafx.fxml.Initializable;

import java.util.List;

public interface PlaceOrderView extends Initializable {
    void loadView();

    List<PlaceOrderCard> getCardList();
}
