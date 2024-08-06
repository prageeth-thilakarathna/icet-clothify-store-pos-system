package edu.icet.pos.controller.place_order;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.bo.custom.SubCategoryBo;
import edu.icet.pos.controller.place_order.custom.PlaceOrderCard;
import edu.icet.pos.controller.place_order.custom.PlaceOrderView;
import edu.icet.pos.model.category.Category;
import edu.icet.pos.model.place_order.CartDetail;
import edu.icet.pos.model.product.Product;
import edu.icet.pos.model.sub_category.SubCategory;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Objects;
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
    private List<PlaceOrderCard> placeOrderCardList;
    private final CategoryBo categoryBo = BoFactory.getBo(BoType.CATEGORY);
    private final SubCategoryBo subCategoryBo = BoFactory.getBo(BoType.SUB_CATEGORY);
    private String filterIDs = null;

    @FXML
    private void optCategoryAction() {
        try {
            if (optCategory.getValue() != null) {
                assert categoryBo != null;
                Category category = categoryBo.getCategoryByName(optCategory.getValue());
                setSubCategory(category);
                btnCancel.setDisable(false);
            }
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
            filterIDs = "";
            StringBuilder stringBuilder = new StringBuilder(filterIDs);
            for (SubCategory subCategory : subCategoryList) {
                if (Boolean.TRUE.equals(subCategory.getIsActive())) {
                    subCategoryArrayList.add(subCategory.getName());
                    stringBuilder.append(subCategory.getId()).append(",");
                    filterIDs = stringBuilder.toString();
                }
            }
            optSubCategory.setItems(subCategoryArrayList);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            filterIDs = stringBuilder.toString();
            loadView();
            optSubCategory.setDisable(false);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void optSubCategoryAction() {
        try {
            if (optSubCategory.getValue() != null) {
                assert categoryBo != null;
                Category category = categoryBo.getCategoryByName(optCategory.getValue());
                assert subCategoryBo != null;
                SubCategory subCategory = subCategoryBo.getSubCategoryByName(optSubCategory.getValue(), category);
                filterIDs = String.valueOf(subCategory.getId());
                loadView();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelAction() {
        filterIDs = null;
        optCategory.setValue(null);
        optCategory.setPromptText("   Select a Category");
        optSubCategory.setValue(null);
        optSubCategory.setPromptText("   Select a Sub Category");
        loadView();
        btnCancel.setDisable(true);
    }

    @Override
    public List<PlaceOrderCard> getCardList() {
        return placeOrderCardList;
    }

    @Override
    public void cancelView() {
        filterIDs = null;
        optCategory.setValue(null);
        optCategory.setPromptText("   Select a Category");
        optSubCategory.setValue(null);
        optSubCategory.setPromptText("   Select a Sub Category");
        btnCancel.setDisable(true);
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

    private int getPageCount() {
        int pageCount = 0;
        try {
            assert productBo != null;
            int productCount = productBo.getProductCount(true);

            if (filterIDs != null) {
                productCount = productBo.getProductCountByFilter(filterIDs);
            }

            if (productCount > 6) {
                int tempFirst = productCount / 6;
                int tempSecond = productCount % 6;

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

    private Node createViewPage(int pageIndex) {
        GridPane gridPane = new GridPane();
        try {
            assert productBo != null;
            List<Product> productList = productBo.getProductPerPage(true, 6, pageIndex * 6);

            if (filterIDs != null) {
                productList = productBo.getProductPerPage(filterIDs, 6, pageIndex * 6);
            }

            int row = 1;
            int column = 0;

            placeOrderCardList = new ArrayList<>();
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
            if(!CartController.getCART_DETAIL_LIST().isEmpty() && !placeOrderCardList.isEmpty()){
                cardOptimize();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return gridPane;
    }

    private void cardOptimize(){
        List<CartDetail> cartDetailList = CartController.getCART_DETAIL_LIST();
        for(PlaceOrderCard placeOrderCard : placeOrderCardList){
            for(CartDetail cartDetail : cartDetailList){
                if(Objects.equals(placeOrderCard.getProduct().getId(), cartDetail.getProduct().getId())){
                    placeOrderCard.setBtnAddToCartDisable();
                }
            }
        }
    }

    @Override
    public void loadView() {
        cardPagination.setPageCount(getPageCount());
        if (filterIDs == null) {
            optCategory.setItems(getCategory());
            optSubCategory.setDisable(true);
        }
        optCategory.setVisibleRowCount(5);
        cardPagination.setPageFactory(this::createViewPage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardPagination.setMaxPageIndicatorCount(10);
    }
}
