package edu.icet.pos.controller.place_order;

import edu.icet.pos.controller.place_order.custom.PlaceOrderCartHeader;
import edu.icet.pos.controller.place_order.custom.PlaceOrderDetailCart;
import edu.icet.pos.controller.place_order.custom.PlaceOrderPay;
import edu.icet.pos.model.place_order.CartDetail;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CartController {
    @Getter
    private static final List<CartDetail> CART_DETAIL_LIST = new ArrayList<>();

    private CartController() {
    }

    public static void addToCart() {
        PlaceOrderCenterController.getInstance().getCartItem().getChildren().clear();
        VBox cartItem = PlaceOrderCenterController.getInstance().getCartItem();
        double height = ((73 * CART_DETAIL_LIST.size()) + (11 * CART_DETAIL_LIST.size())) - (double) 11;
        cartItem.setPrefHeight(height);

        try {
            for (int i = 0; i < CART_DETAIL_LIST.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(CartController.class.getResource("/view/place-order/order-detail.fxml"));
                Parent parent = fxmlLoader.load();

                PlaceOrderDetailCart placeOrderDetailCart = fxmlLoader.getController();
                placeOrderDetailCart.setDetail(CART_DETAIL_LIST.get(i));

                if (i > 0) {
                    Separator separator = new Separator(Orientation.HORIZONTAL);
                    cartItem.getChildren().add(separator);
                    VBox.setMargin(separator, new Insets(5, 10, 0, 10));
                }

                cartItem.getChildren().add(parent);
                VBox.setMargin(parent, new Insets(5, 10, 0, 10));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        PlaceOrderCartHeader placeOrderCartHeader =
                PlaceOrderCenterController.getInstance().getFxmlLoaderCartHeader().getController();
        placeOrderCartHeader.btnCancelOptimize();

        PlaceOrderPay placeOrderPay =
                PlaceOrderCenterController.getInstance().getFxmlLoaderPay().getController();
        placeOrderPay.setNetTotal();
        placeOrderPay.validateInputs();
    }
}
