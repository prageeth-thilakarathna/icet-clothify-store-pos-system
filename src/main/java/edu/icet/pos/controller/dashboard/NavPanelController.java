package edu.icet.pos.controller.dashboard;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.auth.custom.LoginPanel;
import edu.icet.pos.controller.category.CategoryCenterController;
import edu.icet.pos.controller.category.custom.CategoryForm;
import edu.icet.pos.controller.category.custom.CategorySearch;
import edu.icet.pos.controller.category.custom.CategoryView;
import edu.icet.pos.controller.dashboard.custom.DashboardNavPanel;
import edu.icet.pos.controller.dashboard.custom.DashboardReportNav;
import edu.icet.pos.controller.employee.EmployeeCenterController;
import edu.icet.pos.controller.employee.custom.EmployeeForm;
import edu.icet.pos.controller.employee.custom.EmployeeSearch;
import edu.icet.pos.controller.employee.custom.EmployeeView;
import edu.icet.pos.controller.order.OrderCenterController;
import edu.icet.pos.controller.order.custom.OrderView;
import edu.icet.pos.controller.place_order.PlaceOrderCenterController;
import edu.icet.pos.controller.place_order.custom.PlaceOrderView;
import edu.icet.pos.controller.product.ProductCenterController;
import edu.icet.pos.controller.product.custom.ProductForm;
import edu.icet.pos.controller.product.custom.ProductSearch;
import edu.icet.pos.controller.product.custom.ProductView;
import edu.icet.pos.controller.supplier.SupplierCenterController;
import edu.icet.pos.controller.supplier.custom.SupplierForm;
import edu.icet.pos.controller.supplier.custom.SupplierSearch;
import edu.icet.pos.controller.supplier.custom.SupplierView;
import edu.icet.pos.controller.user.UserCenterController;
import edu.icet.pos.controller.user.custom.UserForm;
import edu.icet.pos.controller.user.custom.UserSearch;
import edu.icet.pos.controller.user.custom.UserView;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

        CenterController.getInstance().defaultPageLayoutLoad(
                UserCenterController.getInstance().getParentForm(),
                UserCenterController.getInstance().getParentSearch(),
                UserCenterController.getInstance().getParentView()
        );
        setPageHeader("User");

        UserForm userForm = UserCenterController.getInstance().getFxmlLoaderForm().getController();
        userForm.refreshForm();

        UserSearch userSearch = UserCenterController.getInstance().getFxmlLoaderSearch().getController();
        userSearch.refreshSearch();

        UserView userView = UserCenterController.getInstance().getFxmlLoaderView().getController();
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

        CenterController.getInstance().defaultPageLayoutLoad(
                CategoryCenterController.getInstance().getParentForm(),
                CategoryCenterController.getInstance().getParentSearch(),
                CategoryCenterController.getInstance().getParentView()
        );
        setPageHeader("Category");

        CategoryForm categoryForm = CategoryCenterController.getInstance().getFxmlLoaderForm().getController();
        categoryForm.refreshForm();

        CategorySearch categorySearch = CategoryCenterController.getInstance().getFxmlLoaderSearch().getController();
        categorySearch.refreshSearch();

        CategoryView categoryView = CategoryCenterController.getInstance().getFxmlLoaderView().getController();
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
        btnOrder.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        CenterController.getInstance().defaultPageLayoutLoad(
                SupplierCenterController.getInstance().getParentForm(),
                SupplierCenterController.getInstance().getParentSearch(),
                SupplierCenterController.getInstance().getParentView()
        );
        setPageHeader("Supplier");

        SupplierForm supplierForm = SupplierCenterController.getInstance().getFxmlLoaderForm().getController();
        supplierForm.refreshForm();

        SupplierSearch supplierSearch = SupplierCenterController.getInstance().getFxmlLoaderSearch().getController();
        supplierSearch.refreshSearch();

        SupplierView supplierView = SupplierCenterController.getInstance().getFxmlLoaderView().getController();
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
        btnOrder.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        CenterController.getInstance().defaultPageLayoutLoad(
                ProductCenterController.getInstance().getParentForm(),
                ProductCenterController.getInstance().getParentSearch(),
                ProductCenterController.getInstance().getParentView()
        );
        setPageHeader("Product");

        ProductForm productForm = ProductCenterController.getInstance().getFxmlLoaderForm().getController();
        productForm.refreshForm();

        ProductSearch productSearch = ProductCenterController.getInstance().getFxmlLoaderSearch().getController();
        productSearch.refreshSearch();

        ProductView productView = ProductCenterController.getInstance().getFxmlLoaderView().getController();
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
        btnOrder.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        CenterController.getInstance().defaultPageLayoutLoad(
                EmployeeCenterController.getInstance().getParentForm(),
                EmployeeCenterController.getInstance().getParentSearch(),
                EmployeeCenterController.getInstance().getParentView()
        );
        setPageHeader("Employee");

        EmployeeForm employeeForm = EmployeeCenterController.getInstance().getFxmlLoaderForm().getController();
        employeeForm.refreshForm();

        EmployeeSearch employeeSearch = EmployeeCenterController.getInstance().getFxmlLoaderSearch().getController();
        employeeSearch.refreshSearch();

        EmployeeView employeeView = EmployeeCenterController.getInstance().getFxmlLoaderView().getController();
        employeeView.loadTable();
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

        BorderPane pageBorderPane = CenterController.getInstance().getPageBorderPane();
        pageBorderPane.getChildren().removeAll(pageBorderPane.getChildren());

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageBorderPane.setTop(pageTop);

        setPageHeader("Order");

        pageBorderPane.setCenter(OrderCenterController.getInstance().getPageView());
        OrderView orderView = OrderCenterController.getInstance().getFxmlLoaderView().getController();
        orderView.loadPlaceOrder();
        //pageBorderPane.setRight(right);
        //pageBorderPane.setBottom(bottom);


    }

    @Override
    public void loadCategory() {
        btnCategoryAction();
    }

    @Override
    public void loadInitializer() {
        btnOrderAction();
    }

    @Override
    public void loadOrder() {
        btnOrderAction();
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

        if (!AuthCenterController.isUserAssistance() &&
                !AuthCenterController.isUserCashier() &&
                AuthCenterController.isUser()) {
            navSectionVBox.getChildren().remove(btnOrder);
            navSectionVBox.setPrefHeight(219);
            scrollPane.setPrefHeight(221);
        }

        if (AuthCenterController.isUserAssistance()) {
            navSectionVBox.getChildren().remove(btnOrder);
            navSectionVBox.setPrefHeight(219);
            scrollPane.setPrefHeight(221);
        }
    }

    /*private void defaultPageLayoutLoad(Node center, Node right, Node bottom) {
        BorderPane pageBorderPane = CenterController.getInstance().getPageBorderPane();
        pageBorderPane.getChildren().removeAll(pageBorderPane.getChildren());

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageBorderPane.setTop(pageTop);

        pageBorderPane.setCenter(center);
        pageBorderPane.setRight(right);
        pageBorderPane.setBottom(bottom);
    }*/

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
        //btnDashboardAction();
        //btnSupplierAction();
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
