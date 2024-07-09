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

    private PlaceOrderCenterController() throws IOException {
        parentView = fxmlLoaderView.load();
    }

    static {
        try {
            instance = new PlaceOrderCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating PlaceOrderCenterController singleton instance");
        }
    }
}
