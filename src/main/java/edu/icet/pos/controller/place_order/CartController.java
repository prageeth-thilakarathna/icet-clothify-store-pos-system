package edu.icet.pos.controller.place_order;

import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.place_order.custom.PlaceOrderDetailCart;
import edu.icet.pos.model.place_order.OrderDetail;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class CartController {
    private static CartController instance;
    private final List<OrderDetail> orderDetailList = new ArrayList<>();

    private CartController() {
    }

    public static CartController getInstance() {
        if (instance == null) {
            instance = new CartController();
        }
        return instance;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        orderDetailList.add(orderDetail);
        CenterController.getInstance().getPageRight().getChildren().clear();
        addToCart();
    }

    private void addToCart() {
        VBox vBox = PlaceOrderCenterController.getInstance().getCartItem();
        double height = ((73 * orderDetailList.size()) + (11 * orderDetailList.size())) - (double) 11;
        vBox.setPrefHeight(height);

        try {
            for (int i = 0; i < orderDetailList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/place-order/order-detail.fxml"));
                Parent parent = fxmlLoader.load();

                PlaceOrderDetailCart placeOrderDetailCart = fxmlLoader.getController();
                placeOrderDetailCart.setDetail(orderDetailList.get(i));

                if(i>0){
                    Separator separator = new Separator(Orientation.HORIZONTAL);
                    vBox.getChildren().add(separator);
                    VBox.setMargin(separator, new Insets(5, 10, 0, 10));
                }

                vBox.getChildren().add(parent);
                VBox.setMargin(parent, new Insets(5, 10, 0, 10));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
