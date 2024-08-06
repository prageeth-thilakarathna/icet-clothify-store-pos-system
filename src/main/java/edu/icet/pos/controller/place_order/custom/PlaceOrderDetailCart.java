package edu.icet.pos.controller.place_order.custom;

import edu.icet.pos.model.place_order.CartDetail;
import javafx.fxml.Initializable;

import java.sql.SQLException;

public interface PlaceOrderDetailCart extends Initializable {
    void setDetail(CartDetail cartDetail) throws SQLException;
}
