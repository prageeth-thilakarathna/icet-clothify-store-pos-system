package edu.icet.pos.controller.place_order;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.bo.custom.SubCategoryBo;
import edu.icet.pos.controller.place_order.custom.PlaceOrderCard;
import edu.icet.pos.controller.place_order.custom.PlaceOrderView;
import edu.icet.pos.model.category.Category;
import edu.icet.pos.model.product.Product;
import edu.icet.pos.model.sub_category.SubCategory;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewController implements PlaceOrderView {
    @FXML
    private JFXComboBox<String> optCategory;
    @FXML
    private JFXComboBox<String> optSubCategory;
    @FXML
    private Button btnCancel;
    @FXML
    private Pagination cardPagination;

    private final ProductBo productBo = BoFactory.getBo(BoType.PRODUCT);
    private final List<PlaceOrderCard> placeOrderCardList = new ArrayList<>();
    private final CategoryBo categoryBo = BoFactory.getBo(BoType.CATEGORY);
    private final SubCategoryBo subCategoryBo = BoFactory.getBo(BoType.SUB_CATEGORY);

    @FXML
    private void optCategoryAction() {
        try {
            if (optCategory.getValue() != null) {
                assert categoryBo != null;
                Category category = categoryBo.getCategoryByName(optCategory.getValue());
                setSubCategory(category);
            }
            optSubCategory.setDisable(false);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void setSubCategory(Category category) {
        ObservableList<String> subCategoryArrayList = FXCollections.observableArrayList();
        try {
            assert subCategoryBo != null;
            List<SubCategory> subCategoryList = subCategoryBo.getSubCategoryByCategory(category);
            for (SubCategory subCategory : subCategoryList) {
                if (Boolean.TRUE.equals(subCategory.getIsActive())) {
                    subCategoryArrayList.add(subCategory.getName());
                }
            }
            optSubCategory.setItems(subCategoryArrayList);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void optSubCategoryAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnCancelAction(ActionEvent actionEvent) {
    }

    @Override
    public List<PlaceOrderCard> getCardList() {
        return placeOrderCardList;
    }

    private ObservableList<String> getCategory() {
        ObservableList<String> categoryArrayList = FXCollections.observableArrayList();
        try {
            assert categoryBo != null;
            List<Category> categoryList = categoryBo.getAllCategory();
            for (Category category : categoryList) {
                if (Boolean.TRUE.equals(category.getIsActive())) {
                    categoryArrayList.add(category.getName());
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return categoryArrayList;
    }

    private int getPageCount(){
        int pageCount = 0;
        try{
            assert productBo != null;
            int productCount = productBo.getProductCount();
            if(productCount>6){
                int tempFirst = productCount/6;
                int tempSecond = productCount%6;

                if(tempSecond!=0){
                    pageCount = tempFirst+1;
                } else {
                    pageCount = tempFirst;
                }
            } else {
                pageCount = 1;
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return pageCount;
    }

    private Node createTblPage(int pageIndex) {
        GridPane gridPane = new GridPane();
        try {
            assert productBo != null;
            List<Product> productList = productBo.getProductPerPage(6, pageIndex * 6);

            int row = 1;
            int column = 0;

            for (Product product : productList) {
                FXMLLoader fxmlLoaderCard = new FXMLLoader(getClass().getResource("/view/place-order/card.fxml"));
                VBox vBox = fxmlLoaderCard.load();
                vBox.setStyle("-fx-border-color: #353c52;");
                vBox.setAlignment(Pos.CENTER);
                vBox.setPrefWidth(176);
                vBox.setPrefHeight(196);

                PlaceOrderCard placeOrderCard = fxmlLoaderCard.getController();
                placeOrderCard.setDetail(product);
                placeOrderCardList.add(placeOrderCard);

                gridPane.add(vBox, column++, row);
                GridPane.setMargin(vBox, new Insets(20, 0, 0, 20));

                if (column > 2) {
                    column = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return gridPane;
    }

    @Override
    public void loadView(){
        cardPagination.setPageFactory(this::createTblPage);
        optCategory.setItems(getCategory());
        optCategory.setVisibleRowCount(5);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardPagination.setMaxPageIndicatorCount(10);
        cardPagination.setPageCount(getPageCount());
    }
}
