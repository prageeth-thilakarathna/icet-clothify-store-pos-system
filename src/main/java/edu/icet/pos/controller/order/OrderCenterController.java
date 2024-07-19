package edu.icet.pos.controller.order;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.io.IOException;

@Getter
public class OrderCenterController {
    @Getter
    private static final OrderCenterController instance;
    private final FXMLLoader fxmlLoaderView = new FXMLLoader(getClass().getResource("/view/order/view.fxml"));
    private final Parent parentView;
    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox pageView = new VBox();

    private OrderCenterController() throws IOException {
        parentView = fxmlLoaderView.load();
        scrollPane.setStyle("-fx-focus-color: transparent; -fx-background-color: transparent;");

        pageView.setPrefWidth(911);
        pageView.setPrefHeight(274);
        pageView.setAlignment(Pos.TOP_LEFT);

        scrollPane.setPrefWidth(911);
        scrollPane.setPrefHeight(274);

        scrollPane.setContent(parentView);
        pageView.getChildren().add(scrollPane);
    }

    static {
        try{
            instance = new OrderCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating OrderCenterController singleton instance");
        }
    }
}
