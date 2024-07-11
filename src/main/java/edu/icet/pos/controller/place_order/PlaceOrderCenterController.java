package edu.icet.pos.controller.place_order;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class PlaceOrderCenterController {
    @Getter
    private static final PlaceOrderCenterController instance;
    private final FXMLLoader fxmlLoaderView = new FXMLLoader(getClass().getResource("/view/place-order/view.fxml"));
    private final Parent parentView;
    private final FXMLLoader fxmlLoaderCartHeader = new FXMLLoader(getClass().getResource("/view/place-order/cart-header.fxml"));
    private final Parent parentCartHeader;
    private final FXMLLoader fxmlLoaderPay = new FXMLLoader(getClass().getResource("/view/place-order/pay.fxml"));
    private final Parent parentPay;

    private PlaceOrderCenterController() throws IOException {
        parentView = fxmlLoaderView.load();
        parentCartHeader = fxmlLoaderCartHeader.load();
        parentPay = fxmlLoaderPay.load();
    }

    static {
        try {
            instance = new PlaceOrderCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating PlaceOrderCenterController singleton instance");
        }
    }
}
