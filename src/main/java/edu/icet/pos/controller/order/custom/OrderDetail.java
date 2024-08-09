package edu.icet.pos.controller.order.custom;

import javafx.fxml.Initializable;

import java.util.List;

public interface OrderDetail extends Initializable {
    void loadDetail(List<edu.icet.pos.model.order.OrderDetail> orderDetailList);

    void clearOrder();
}
