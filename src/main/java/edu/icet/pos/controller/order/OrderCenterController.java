package edu.icet.pos.controller.order;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
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
    private final FXMLLoader fxmlLoaderTable = new FXMLLoader(getClass().getResource("/view/order/table.fxml"));
    private final Parent parentTable;
    private final BorderPane orderDetailPane = new BorderPane();
    private final VBox pageDetail = new VBox();
    private final ScrollPane detailScroll = new ScrollPane();
    private final FXMLLoader fxmlLoaderSearch = new FXMLLoader(getClass().getResource("/view/order/search.fxml"));
    private final Parent parentSearch;
    private final FXMLLoader fxmlLoaderDetail = new FXMLLoader(getClass().getResource("/view/order/detail.fxml"));
    private final Parent parentDetail;

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

        parentTable = fxmlLoaderTable.load();

        pageDetail.setPrefWidth(911);
        pageDetail.setPrefHeight(273);
        pageDetail.setAlignment(Pos.CENTER);

        detailScroll.setPrefWidth(911);
        detailScroll.setPrefHeight(273);
        detailScroll.setStyle("-fx-focus-color: transparent; -fx-background-color: transparent;");

        parentSearch = fxmlLoaderSearch.load();
        parentDetail = fxmlLoaderDetail.load();

        orderDetailPane.setTop(parentSearch);
        orderDetailPane.setCenter(parentDetail);

        detailScroll.setContent(orderDetailPane);
        pageDetail.getChildren().add(detailScroll);
    }

    static {
        try {
            instance = new OrderCenterController();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating OrderCenterController singleton instance");
        }
    }
}
