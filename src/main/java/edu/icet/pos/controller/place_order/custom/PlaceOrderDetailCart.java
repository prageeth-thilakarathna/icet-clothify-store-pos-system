package edu.icet.pos.controller.place_order.custom;

import edu.icet.pos.model.place_order.OrderDetail;
import javafx.fxml.Initializable;

import java.sql.SQLException;

public interface PlaceOrderDetailCart extends Initializable {
    void setDetail(OrderDetail orderDetail) throws SQLException;
}
