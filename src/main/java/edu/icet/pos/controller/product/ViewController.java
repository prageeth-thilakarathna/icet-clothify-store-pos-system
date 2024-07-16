package edu.icet.pos.controller.product;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.controller.product.custom.ProductCard;
import edu.icet.pos.controller.product.custom.ProductView;
import edu.icet.pos.model.product.Product;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewController implements ProductView {
    @FXML
    private Label dspCount;
    @FXML
    private Pagination cardPagination;

    private final ProductBo productBo = BoFactory.getBo(BoType.PRODUCT);
    private static final String DELETION = "deletion";

    private int getCurrentPageIndex(int pageIndex, String name) {
        if (Objects.equals(name, "registration")) {
            return getPageCount() - 1;
        } else if ("modification".equals(name)) {
            return pageIndex;
        } else if (DELETION.equals(name) && pageIndex == getPageCount()) {
            return pageIndex - 1;
        } else if (DELETION.equals(name) && pageIndex < getPageCount()) {
            return pageIndex;
        } else if (DELETION.equals(name) && (pageIndex + 1) == getPageCount()) {
            return pageIndex;
        } else {
            return 0;
        }
    }

    private int getPageCount() {
        int pageCount = 0;
        try {
            assert productBo != null;
            int productCount = productBo.getProductCount();
            if (productCount > 5) {
                int tempFirst = productCount / 5;
                int tempSecond = productCount % 5;

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

    private void productCountUpdate() {
        try {
            assert productBo != null;
            dspCount.setText(String.valueOf(productBo.getProductCount()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private Node createCardPage(int pageIndex) {
        GridPane gridPane = new GridPane();
        try {
            assert productBo != null;
            List<Product> productList = productBo.getProductPerPage(5, pageIndex * 5);

            for (int i = 0; i < productList.size(); i++) {
                FXMLLoader fxmlLoaderCard = new FXMLLoader(getClass().getResource("/view/product/card.fxml"));
                VBox vBox = fxmlLoaderCard.load();
                vBox.setStyle("-fx-border-color: #353c52;");
                vBox.setAlignment(Pos.CENTER);
                vBox.setPrefWidth(158);
                vBox.setPrefHeight(196);

                ProductCard productCard = fxmlLoaderCard.getController();
                productCard.setDetail(productList.get(i));

                gridPane.add(vBox, i, 1);
                GridPane.setMargin(vBox, new Insets(0, 10, 0, 10));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return gridPane;
    }

    @Override
    public void loadCard() {
        cardPagination.setPageFactory(this::createCardPage);
    }

    @Override
    public void updatePanel(String name) {
        int pageIndex = cardPagination.getCurrentPageIndex();
        cardPagination.setPageCount(getPageCount());
        cardPagination.setPageFactory(this::createCardPage);
        cardPagination.setCurrentPageIndex(getCurrentPageIndex(pageIndex, name));
        productCountUpdate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productCountUpdate();
        cardPagination.setMaxPageIndicatorCount(10);
        cardPagination.setPageCount(getPageCount());
    }
}
