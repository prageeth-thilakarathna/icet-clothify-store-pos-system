package edu.icet.pos.controller.dashboard;

import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.auth.custom.LoginPanel;
import edu.icet.pos.controller.category.CategoryCenterController;
import edu.icet.pos.controller.category.custom.CategoryView;
import edu.icet.pos.controller.dashboard.custom.DashboardNavPanel;
import edu.icet.pos.controller.employee.EmployeeCenterController;
import edu.icet.pos.controller.product.ProductCenterController;
import edu.icet.pos.controller.product.custom.ProductView;
import edu.icet.pos.controller.supplier.SupplierCenterController;
import edu.icet.pos.controller.supplier.custom.SupplierView;
import edu.icet.pos.controller.user.UserCenterController;
import edu.icet.pos.controller.user.custom.UserView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class NavPanelController implements DashboardNavPanel {
    @FXML
    private Button btnSupplier;
    @FXML
    private Button btnProduct;
    @FXML
    private Button btnInventory;
    @FXML
    private Button btnEmployee;
    @FXML
    private Button btnOrder;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox navSectionVBox;
    @FXML
    private Button btnCategory;
    @FXML
    private Button btnUser;
    @FXML
    private Button btnDashboard;

    private final LoginPanel loginPanel = AuthCenterController.getInstance().getFxmlLoaderLoginPanel().getController();
    private final UserView userView = UserCenterController.getInstance().getFxmlLoaderView().getController();
    private final CategoryView categoryView = CategoryCenterController.getInstance().getFxmlLoaderView().getController();
    private final SupplierView supplierView = SupplierCenterController.getInstance().getFxmlLoaderView().getController();
    private final ProductView productView = ProductCenterController.getInstance().getFxmlLoaderView().getController();

    @FXML
    private void btnUserAction() {
        btnUser.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageTop.getChildren().clear();
        Label pageHeader = CenterController.getInstance().getPageHeader();
        pageHeader.setText("User");
        pageTop.getChildren().add(pageHeader);

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageCenter.getChildren().clear();
        pageCenter.getChildren().add(UserCenterController.getInstance().getParentForm());

        VBox pageRight = CenterController.getInstance().getPageRight();
        pageRight.getChildren().clear();
        pageRight.getChildren().add(UserCenterController.getInstance().getParentSearch());

        VBox pageBottom = CenterController.getInstance().getPageBottom();
        pageBottom.getChildren().clear();
        pageBottom.getChildren().add(UserCenterController.getInstance().getParentView());
        userView.loadTable();
    }

    @FXML
    private void btnDashboardAction() {
        btnDashboard.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageTop.getChildren().clear();
        Label pageHeader = CenterController.getInstance().getPageHeader();
        pageHeader.setText("Dashboard");
        pageTop.getChildren().add(pageHeader);

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageCenter.getChildren().clear();

        VBox pageRight = CenterController.getInstance().getPageRight();
        pageRight.getChildren().clear();

        VBox pageBottom = CenterController.getInstance().getPageBottom();
        pageBottom.getChildren().clear();
    }

    @FXML
    private void btnCategoryAction() {
        btnCategory.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageTop.getChildren().clear();
        Label pageHeader = CenterController.getInstance().getPageHeader();
        pageHeader.setText("Category");
        pageTop.getChildren().add(pageHeader);

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageCenter.getChildren().clear();
        pageCenter.getChildren().add(CategoryCenterController.getInstance().getParentForm());

        VBox pageRight = CenterController.getInstance().getPageRight();
        pageRight.getChildren().clear();
        pageRight.getChildren().add(CategoryCenterController.getInstance().getParentSearch());

        VBox pageBottom = CenterController.getInstance().getPageBottom();
        pageBottom.getChildren().clear();
        pageBottom.getChildren().add(CategoryCenterController.getInstance().getParentView());
        categoryView.loadTable();
    }

    @FXML
    private void btnSupplierAction() {
        btnSupplier.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageTop.getChildren().clear();
        Label pageHeader = CenterController.getInstance().getPageHeader();
        pageHeader.setText("Supplier");
        pageTop.getChildren().add(pageHeader);

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageCenter.getChildren().clear();
        pageCenter.getChildren().add(SupplierCenterController.getInstance().getParentForm());

        VBox pageRight = CenterController.getInstance().getPageRight();
        pageRight.getChildren().clear();
        pageRight.getChildren().add(SupplierCenterController.getInstance().getParentSearch());

        VBox pageBottom = CenterController.getInstance().getPageBottom();
        pageBottom.getChildren().clear();
        pageBottom.getChildren().add(SupplierCenterController.getInstance().getParentView());
        supplierView.loadTable();
    }

    @FXML
    private void btnProductAction() {
        btnProduct.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageTop.getChildren().clear();
        Label pageHeader = CenterController.getInstance().getPageHeader();
        pageHeader.setText("Product");
        pageTop.getChildren().add(pageHeader);

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageCenter.getChildren().clear();
        pageCenter.getChildren().add(ProductCenterController.getInstance().getParentForm());

        VBox pageRight = CenterController.getInstance().getPageRight();
        pageRight.getChildren().clear();
        pageRight.getChildren().add(ProductCenterController.getInstance().getParentSearch());

        VBox pageBottom = CenterController.getInstance().getPageBottom();
        pageBottom.getChildren().clear();
        pageBottom.getChildren().add(ProductCenterController.getInstance().getParentView());
        productView.loadCard();
    }

    @FXML
    private void btnEmployeeAction() {
        btnEmployee.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageTop.getChildren().clear();
        Label pageHeader = CenterController.getInstance().getPageHeader();
        pageHeader.setText("Employee");
        pageTop.getChildren().add(pageHeader);

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageCenter.getChildren().clear();
        pageCenter.getChildren().add(EmployeeCenterController.getInstance().getParentForm());

        VBox pageRight = CenterController.getInstance().getPageRight();
        pageRight.getChildren().clear();
        pageRight.getChildren().add(EmployeeCenterController.getInstance().getParentSearch());

        VBox pageBottom = CenterController.getInstance().getPageBottom();
        pageBottom.getChildren().clear();
    }

    @Override
    public void loadCategory() {
        btnCategoryAction();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BorderPane borderPanePage = CenterController.getInstance().getPageBorderPane();
        borderPanePage.getChildren().removeAll(borderPanePage.getChildren());

        HBox pageTop = CenterController.getInstance().getPageTop();
        borderPanePage.setTop(pageTop);

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        borderPanePage.setCenter(pageCenter);

        VBox pageRight = CenterController.getInstance().getPageRight();
        borderPanePage.setRight(pageRight);

        VBox pageBottom = CenterController.getInstance().getPageBottom();
        borderPanePage.setBottom(pageBottom);

        if (loginPanel.getLoginUser() != null && Objects.equals(loginPanel.getLoginUser().getUserRole().getName(), "user")) {
            navSectionVBox.getChildren().remove(btnUser);
            navSectionVBox.getChildren().remove(btnCategory);
            navSectionVBox.getChildren().remove(btnEmployee);
            navSectionVBox.setPrefHeight(278);
            scrollPane.setPrefHeight(280);
        }
        btnEmployeeAction();
    }
}
