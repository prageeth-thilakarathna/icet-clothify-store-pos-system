package edu.icet.pos.controller.dashboard;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.auth.custom.LoginPanel;
import edu.icet.pos.controller.category.CategoryCenterController;
import edu.icet.pos.controller.category.custom.CategoryView;
import edu.icet.pos.controller.dashboard.custom.DashboardNavPanel;
import edu.icet.pos.controller.dashboard.custom.DashboardReportNav;
import edu.icet.pos.controller.employee.EmployeeCenterController;
import edu.icet.pos.controller.employee.custom.EmployeeView;
import edu.icet.pos.controller.place_order.PlaceOrderCenterController;
import edu.icet.pos.controller.place_order.custom.PlaceOrderView;
import edu.icet.pos.controller.product.ProductCenterController;
import edu.icet.pos.controller.product.custom.ProductView;
import edu.icet.pos.controller.supplier.SupplierCenterController;
import edu.icet.pos.controller.supplier.custom.SupplierView;
import edu.icet.pos.controller.user.UserCenterController;
import edu.icet.pos.controller.user.custom.UserView;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
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

    //private final LoginPanel loginPanel = AuthCenterController.getInstance().getFxmlLoaderLoginPanel().getController();
    //private final UserView userView = UserCenterController.getInstance().getFxmlLoaderView().getController();
    /*private final CategoryView categoryView = CategoryCenterController.getInstance().getFxmlLoaderView().getController();
    private final SupplierView supplierView = SupplierCenterController.getInstance().getFxmlLoaderView().getController();
    private final ProductView productView = ProductCenterController.getInstance().getFxmlLoaderView().getController();
    private final EmployeeView employeeView = EmployeeCenterController.getInstance().getFxmlLoaderView().getController();
    private final PlaceOrderView placeOrderView = PlaceOrderCenterController.getInstance().getFxmlLoaderView().getController();
    private final UserBo userBo = BoFactory.getBo(BoType.USER);*/

    @FXML
    private void btnUserAction() {
        btnUser.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnOrder.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        defaultPageLayoutLoad();
        setPageHeader("User");

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageCenter.getChildren().clear();
        pageCenter.getChildren().add(UserCenterController.getInstance().getParentForm());

        VBox pageRight = CenterController.getInstance().getPageRight();
        pageRight.getChildren().clear();
        pageRight.getChildren().add(UserCenterController.getInstance().getParentSearch());

        VBox pageBottom = CenterController.getInstance().getPageBottom();
        pageBottom.getChildren().clear();
        pageBottom.getChildren().add(UserCenterController.getInstance().getParentView());
        //userView.loadTable();
    }

    @FXML
    private void btnDashboardAction() {
        btnDashboard.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnOrder.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        pageLayoutHeaderLoad();
        setPageHeader("Dashboard");

        BorderPane dashboardBorderPane = DashboardCenterController.getInstance().getDashboardBorderPane();

        VBox dashboardNavPanel = DashboardCenterController.getInstance().getDashboardNavPanel();
        dashboardNavPanel.getChildren().clear();

        dashboardNavPanel.getChildren().add(DashboardCenterController.getInstance().getParentReportNav());

        Separator separator = new Separator(Orientation.HORIZONTAL);
        dashboardNavPanel.getChildren().add(separator);
        VBox.setMargin(separator, new Insets(0, 20, 0, 20));

        BorderPane borderPanePage = CenterController.getInstance().getPageBorderPane();
        borderPanePage.setCenter(dashboardBorderPane);

        DashboardReportNav dashboardReportNav = DashboardCenterController.getInstance().getFxmlLoaderReportNav().getController();
        dashboardReportNav.setInventory();
    }

    @FXML
    private void btnCategoryAction() {
        btnCategory.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnOrder.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        /*HBox pageTop = CenterController.getInstance().getPageTop();
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
        //categoryView.loadTable();*/
    }

    @FXML
    private void btnSupplierAction() {
        btnSupplier.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnOrder.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        /*HBox pageTop = CenterController.getInstance().getPageTop();
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
        //supplierView.loadTable();*/
    }

    @FXML
    private void btnProductAction() {
        btnProduct.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnOrder.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        /*HBox pageTop = CenterController.getInstance().getPageTop();
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
        //productView.loadCard();*/
    }

    @FXML
    private void btnEmployeeAction() {
        btnEmployee.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnOrder.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        /*HBox pageTop = CenterController.getInstance().getPageTop();
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

        ScrollPane scrollPaneView = new ScrollPane();
        scrollPaneView.setStyle("-fx-focus-color: transparent; -fx-background-color: transparent;");
        scrollPaneView.setFocusTraversable(false);
        scrollPaneView.setPrefHeight(273);
        scrollPaneView.setContent(EmployeeCenterController.getInstance().getParentView());
        pageBottom.getChildren().add(scrollPaneView);
        //employeeView.loadTable();*/
    }

    @FXML
    private void btnOrderAction() {
        btnOrder.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnProduct.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnCategory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        /*HBox pageTop = CenterController.getInstance().getPageTop();
        pageTop.getChildren().clear();
        Label pageHeader = CenterController.getInstance().getPageHeader();
        pageHeader.setText("Place Order");
        pageTop.getChildren().add(pageHeader);

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageCenter.getChildren().clear();
        pageCenter.getChildren().add(PlaceOrderCenterController.getInstance().getParentView());
        //placeOrderView.loadCard();

        VBox pageRight = CenterController.getInstance().getPageRight();
        pageRight.getChildren().clear();

        BorderPane borderPane = new BorderPane();

        HBox hBox = new HBox();
        Separator separator = new Separator(Orientation.VERTICAL);
        hBox.getChildren().add(separator);
        hBox.getChildren().add(borderPane);

        BorderPane borderPanePage = CenterController.getInstance().getPageBorderPane();
        borderPanePage.setRight(hBox);

        borderPane.setTop(PlaceOrderCenterController.getInstance().getParentCartHeader());

        ScrollPane scrollPaneCart = new ScrollPane();
        scrollPaneCart.setStyle("-fx-focus-color: transparent; -fx-background-color: transparent;");

        VBox vBox = CenterController.getInstance().getPageRight();
        vBox.setPrefWidth(279);
        vBox.setPrefHeight(0);
        vBox.setAlignment(Pos.TOP_CENTER);
        scrollPaneCart.setContent(vBox);

        borderPane.setCenter(scrollPaneCart);
        borderPane.setBottom(PlaceOrderCenterController.getInstance().getParentPay());*/
    }

    @Override
    public void loadCategory() {
        btnCategoryAction();
    }

    @Override
    public void authNotify() {
        if (AuthCenterController.isUser()) {
            navSectionVBox.getChildren().remove(btnUser);
            navSectionVBox.getChildren().remove(btnCategory);
            navSectionVBox.getChildren().remove(btnEmployee);
            navSectionVBox.setPrefHeight(278);
            scrollPane.setPrefHeight(280);
        }
    }

    private void defaultPageLayoutLoad() {
        BorderPane pageBorderPane = CenterController.getInstance().getPageBorderPane();
        pageBorderPane.getChildren().removeAll(pageBorderPane.getChildren());

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageBorderPane.setTop(pageTop);

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageBorderPane.setCenter(pageCenter);

        VBox pageRight = CenterController.getInstance().getPageRight();
        pageBorderPane.setRight(pageRight);

        VBox pageBottom = CenterController.getInstance().getPageBottom();
        pageBorderPane.setBottom(pageBottom);
    }

    private void pageLayoutHeaderLoad() {
        BorderPane pageBorderPane = CenterController.getInstance().getPageBorderPane();
        pageBorderPane.getChildren().removeAll(pageBorderPane.getChildren());

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageBorderPane.setTop(pageTop);
    }

    private void setPageHeader(String header) {
        HBox pageTop = CenterController.getInstance().getPageTop();
        pageTop.getChildren().clear();
        Label pageHeader = CenterController.getInstance().getPageHeader();
        pageHeader.setText(header);
        pageTop.getChildren().add(pageHeader);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDashboardAction();
        //BorderPane borderPanePage = CenterController.getInstance().getPageBorderPane();
        //borderPanePage.getChildren().removeAll(borderPanePage.getChildren());

        //HBox pageTop = CenterController.getInstance().getPageTop();
        //borderPanePage.setTop(pageTop);

        //VBox pageCenter = CenterController.getInstance().getPageCenter();
        //borderPanePage.setCenter(pageCenter);

        //VBox pageRight = CenterController.getInstance().getPageRight();
        //borderPanePage.setRight(pageRight);

        //VBox pageBottom = CenterController.getInstance().getPageBottom();
        //borderPanePage.setBottom(pageBottom);

        //btnDashboardAction();
    }
}
