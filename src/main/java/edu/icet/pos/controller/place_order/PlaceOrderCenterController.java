package edu.icet.pos.controller.place_order;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private final VBox cartItem = new VBox();
    private final ScrollPane scrollPaneCart = new ScrollPane();
    private final BorderPane borderPane = new BorderPane();
    private final HBox hBox = new HBox();
    private final Separator separator = new Separator(Orientation.VERTICAL);

    private PlaceOrderCenterController() throws IOException {
        parentView = fxmlLoaderView.load();
        parentCartHeader = fxmlLoaderCartHeader.load();
        parentPay = fxmlLoaderPay.load();

        cartItem.setPrefWidth(279);
        cartItem.setPrefHeight(0);
        cartItem.setAlignment(Pos.TOP_CENTER);

        scrollPaneCart.setStyle("-fx-focus-color: transparent; -fx-background-color: transparent;");
        scrollPaneCart.setContent(cartItem);

        borderPane.setTop(parentCartHeader);
        borderPane.setCenter(scrollPaneCart);
        borderPane.setBottom(parentPay);

        hBox.getChildren().add(separator);
        hBox.getChildren().add(borderPane);
    }

    static {
        try {
            instance = new PlaceOrderCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating PlaceOrderCenterController singleton instance");
        }
    }
}
