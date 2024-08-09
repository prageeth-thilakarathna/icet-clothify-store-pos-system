package edu.icet.pos.controller.order;

import edu.icet.pos.controller.order.custom.OrderCard;
import edu.icet.pos.controller.order.custom.OrderDetail;
import edu.icet.pos.model.product.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DetailController implements OrderDetail {
    @FXML
    private Pagination cardPagination;

    private List<edu.icet.pos.model.order.OrderDetail> searchOrderDetailList;

    private Node createCardPage(int pageIndex) {
        GridPane gridPane = new GridPane();
        try{
            List<edu.icet.pos.model.order.OrderDetail> orderDetails = getDetails(pageIndex);
            for(int i=0; i<orderDetails.size(); i++){
                FXMLLoader fxmlLoaderCard = new FXMLLoader(getClass().getResource("/view/order/card.fxml"));
                VBox vBox = fxmlLoaderCard.load();
                vBox.setStyle("-fx-border-color: #353c52;");
                vBox.setAlignment(Pos.CENTER);
                vBox.setPrefWidth(158);
                vBox.setPrefHeight(196);

                OrderCard orderCard = fxmlLoaderCard.getController();
                orderCard.setDetail(new ModelMapper().map(orderDetails.get(i).getProduct(), Product.class));

                gridPane.add(vBox, i, 1);
                GridPane.setMargin(vBox, new Insets(0, 10, 0, 10));
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return gridPane;
    }

    private List<edu.icet.pos.model.order.OrderDetail> getDetails(int pageIndex){
        List<edu.icet.pos.model.order.OrderDetail> details = new ArrayList<>();
        int limit = Math.min(searchOrderDetailList.size() - 1, (pageIndex * 5) + 4);
        for(int i=pageIndex*5; i<=limit; i++){
            details.add(searchOrderDetailList.get(i));
        }
        return details;
    }

    private int getPageCount(int size) {
        int pageCount = 0;
        try {
            if (size > 5) {
                int tempFirst = size / 5;
                int tempSecond = size % 5;

                if (tempSecond != 0) {
                    pageCount = tempFirst + 1;
                } else {
                    pageCount = tempFirst;
                }
            } else {
                pageCount = 1;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return pageCount;
    }

    @Override
    public void loadDetail(List<edu.icet.pos.model.order.OrderDetail> orderDetailList) {
        cardPagination.setPageCount(getPageCount(orderDetailList.size()));
        searchOrderDetailList = orderDetailList;
        cardPagination.setPageFactory(this::createCardPage);
    }

    @Override
    public void clearOrder() {
        loadDetail(new ArrayList<>());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardPagination.setMaxPageIndicatorCount(10);
        cardPagination.setPageCount(1);
    }
}
