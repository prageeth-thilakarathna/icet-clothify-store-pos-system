package edu.icet.pos.controller.order;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.dashboard.custom.DashboardNavPanel;
import edu.icet.pos.controller.order.custom.OrderView;
import edu.icet.pos.controller.place_order.PlaceOrderCenterController;
import edu.icet.pos.controller.place_order.custom.PlaceOrderView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements OrderView {
    @FXML
    private JFXComboBox optFilter;
    @FXML
    private Label dspCount;
    @FXML
    private Button btnPlaceOrder;
    @FXML
    private Pagination tblPagination;

    @FXML
    private void optFilterAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnPlaceOrderAction() {
        BorderPane pageBorderPane = CenterController.getInstance().getPageBorderPane();
        pageBorderPane.getChildren().removeAll(pageBorderPane.getChildren());

        HBox pageTop = CenterController.getInstance().getPageTop();
        pageBorderPane.setTop(pageTop);

        CenterController.getInstance().setPageHeader("Order", "/ Place Order");
        Hyperlink pageMainHeader = CenterController.getInstance().getPageMainHeader();

        DashboardNavPanel dashboardNavPanel = DashboardCenterController.getInstance().getFxmlLoaderNav().getController();
        pageMainHeader.setOnAction(actionEvent1 -> dashboardNavPanel.loadOrder());

        pageBorderPane.setCenter(PlaceOrderCenterController.getInstance().getParentView());
        PlaceOrderView placeOrderView = PlaceOrderCenterController.getInstance().getFxmlLoaderView().getController();
        placeOrderView.cancelView();
        placeOrderView.loadView();

        pageBorderPane.setRight(PlaceOrderCenterController.getInstance().getHBox());


    }

    @Override
    public void loadPlaceOrder() {
        btnPlaceOrderAction();
    }

    @Override
    public void authNotify() {
        if (!AuthCenterController.isUserCashier()) {
            btnPlaceOrder.setVisible(false);
        }
        if (AuthCenterController.isUserCashier()) {
            btnPlaceOrder.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
