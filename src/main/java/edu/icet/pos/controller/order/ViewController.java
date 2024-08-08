package edu.icet.pos.controller.order;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.bo.custom.OrderDetailBo;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.dashboard.custom.DashboardNavPanel;
import edu.icet.pos.controller.order.custom.OrderTable;
import edu.icet.pos.controller.order.custom.OrderView;
import edu.icet.pos.controller.place_order.PlaceOrderCenterController;
import edu.icet.pos.controller.place_order.custom.PlaceOrderView;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.model.order.Order;
import edu.icet.pos.model.order.OrderDetail;
import edu.icet.pos.model.order.TblOrderView;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
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

    private final OrderBo orderBo = BoFactory.getBo(BoType.ORDER);
    private final OrderDetailBo orderDetailBo = BoFactory.getBo(BoType.ORDER_DETAIL);
    private OrderTable orderTable;
    private final UserBo userBo = BoFactory.getBo(BoType.USER);

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

    private ObservableList<TblOrderView> getOrderTblPerPage(int pageIndex) {
        ObservableList<TblOrderView> orderViewObservableList = FXCollections.observableArrayList();
        try {
            assert orderBo != null;
            List<Order> orderList = orderBo.getOrderPerPage(pageIndex * 5);

            for (Order order : orderList) {
                assert orderDetailBo != null;
                List<OrderDetail> orderDetailList = orderDetailBo.getOrderDetail(order);

                double total = 0.0;
                for (OrderDetail orderDetail : orderDetailList) {
                    total += orderDetail.getTotal();
                }

                DecimalFormat df = new DecimalFormat("0.00");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                orderViewObservableList.add(
                        TblOrderView.builder()
                                .id(String.valueOf(order.getId()))
                                .customerName(order.getCustomerName())
                                .employeeName(order.getEmployeeName())
                                .paymentType(order.getPaymentType())
                                .netTotal(df.format(total))
                                .registerAt(dateFormat.format(order.getRegisterAt()))
                                .returnStatus(order.getReturnAt() == null ? "Not" : "Returned")
                                .build()
                );
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return orderViewObservableList;
    }

    private Node createTblPage(int pageIndex) {
        if (orderTable == null) {
            orderTable = OrderCenterController.getInstance().getFxmlLoaderTable().getController();
        }
        TableView<TblOrderView> tableView = orderTable.getTable();
        tableView.setItems(getOrderTblPerPage(pageIndex));
        return tableView;
    }

    @Override
    public void loadPlaceOrder() {
        //btnPlaceOrderAction();
    }

    @Override
    public void loadTable() {
        orderCountUpdate();
        tblPagination.setPageCount(getPageCount());
        tblPagination.setPageFactory(this::createTblPage);
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

    private int getPageCount() {
        int pageCount = 0;
        try {
            assert orderBo != null;
            int orderCount = orderBo.getOrderCount();
            if (orderCount > 5) {
                int tempFirst = orderCount / 5;
                int tempSecond = orderCount % 5;

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

    private void orderCountUpdate() {
        try {
            assert orderBo != null;
            dspCount.setText(String.valueOf(orderBo.getOrderCount()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderCountUpdate();
        tblPagination.setMaxPageIndicatorCount(10);
        tblPagination.setPageCount(getPageCount());
    }
}
