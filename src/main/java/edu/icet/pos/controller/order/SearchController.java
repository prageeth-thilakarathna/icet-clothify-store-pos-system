package edu.icet.pos.controller.order;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.bo.custom.OrderDetailBo;
import edu.icet.pos.controller.order.custom.OrderSearch;
import edu.icet.pos.controller.order.custom.OrderView;
import edu.icet.pos.model.order.Order;
import edu.icet.pos.model.order.OrderDetail;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements OrderSearch {
    @FXML
    private Label dspCustomerName;
    @FXML
    private Label dspCustomerEMail;
    @FXML
    private Label dspPaymentType;
    @FXML
    private Label dspEmployeeId;
    @FXML
    private Label dspEmployeeName;
    @FXML
    private Label dspTotal;
    @FXML
    private Label dspReturnStatus;
    @FXML
    private Label dspRegisterAt;
    @FXML
    private Label dspReturnAt;
    @FXML
    private TextField txtOrderId;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnReturn;

    private final OrderBo orderBo = BoFactory.getBo(BoType.ORDER);
    private Order searchOrder;
    private final OrderDetailBo orderDetailBo = BoFactory.getBo(BoType.ORDER_DETAIL);
    private edu.icet.pos.controller.order.custom.OrderDetail orderDetailSection;
    private OrderView orderView;

    @FXML
    private void orderIdKeyTyped() {
        validateInputs();
    }

    @FXML
    private void btnSearchAction() {
        try {
            assert orderBo != null;
            searchOrder = orderBo.getOrder(Integer.parseInt(txtOrderId.getText()));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            assert orderDetailBo != null;
            List<OrderDetail> orderDetailList = orderDetailBo.getOrderDetail(searchOrder);
            DecimalFormat df = new DecimalFormat("0.00");

            double total = 0.0;
            for (OrderDetail orderDetail : orderDetailList) {
                total += orderDetail.getTotal();
            }

            dspCustomerName.setText(searchOrder.getCustomerName());
            dspCustomerEMail.setText(searchOrder.getCustomerEMail());
            dspPaymentType.setText(searchOrder.getPaymentType());
            dspEmployeeId.setText(String.valueOf(searchOrder.getEmployee().getId()));
            dspEmployeeName.setText(searchOrder.getEmployeeName());
            dspTotal.setText("Rs. " + df.format(total));
            dspRegisterAt.setText(dateFormat.format(searchOrder.getRegisterAt()));
            dspReturnStatus.setText(searchOrder.getReturnAt() == null ? "Not" : "Returned");
            dspReturnAt.setText(searchOrder.getReturnAt() == null ? "-" : dateFormat.format(searchOrder.getReturnAt()));

            if (orderDetailSection == null) {
                orderDetailSection = OrderCenterController.getInstance().getFxmlLoaderDetail().getController();
            }
            orderDetailSection.loadDetail(orderDetailList);
            validateInputs();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelOnAction() {
        clearForm();
    }

    @FXML
    private void btnReturnAction() {
        try {
            Order order = searchOrder;
            order.setReturnAt(new Date());
            assert orderBo != null;
            orderBo.orderReturn(searchOrder);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(order.getId() + " Order return is successful.");
            alert.show();
            btnSearchAction();
            if (orderView == null) {
                orderView = OrderCenterController.getInstance().getFxmlLoaderView().getController();
            }
            orderView.updatePanel("modification");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void validateInputs() {
        btnSearch.setDisable(txtOrderId.getLength() == 0 || searchOrder != null);
        if (searchOrder != null) {
            btnCancel.setDisable(false);
            btnReturn.setDisable(searchOrder.getReturnAt() != null);
            txtOrderId.setDisable(true);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private void clearForm() {
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
        btnReturn.setDisable(true);
        txtOrderId.setDisable(false);
        txtOrderId.setText("");
        dspRegisterAt.setText("");
        dspCustomerName.setText("");
        dspCustomerEMail.setText("");
        dspPaymentType.setText("");
        dspEmployeeId.setText("");
        dspEmployeeName.setText("");
        dspTotal.setText("");
        dspRegisterAt.setText("");
        dspReturnStatus.setText("");
        dspReturnAt.setText("");
        searchOrder = null;
        if (orderDetailSection == null) {
            orderDetailSection = OrderCenterController.getInstance().getFxmlLoaderDetail().getController();
        }
        orderDetailSection.clearOrder();
    }

    @Override
    public void refreshSearch() {
        clearForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
        btnReturn.setDisable(true);
    }
}
