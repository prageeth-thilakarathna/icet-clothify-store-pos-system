package edu.icet.pos.controller.place_order;

import edu.icet.pos.controller.place_order.custom.PlaceOrderCard;
import edu.icet.pos.controller.place_order.custom.PlaceOrderCartHeader;
import edu.icet.pos.controller.place_order.custom.PlaceOrderPay;
import edu.icet.pos.controller.place_order.custom.PlaceOrderView;
import edu.icet.pos.model.place_order.CartDetail;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Setter
public class CartHeaderController implements PlaceOrderCartHeader {
    @FXML
    private Button btnCancel;

    @FXML
    private void btnCancelOnAction() {
        List<CartDetail> cartDetailList = CartController.getCART_DETAIL_LIST();
        PlaceOrderPay placeOrderPay = PlaceOrderCenterController.getInstance().getFxmlLoaderPay().getController();
        PlaceOrderView placeOrderView = PlaceOrderCenterController.getInstance().getFxmlLoaderView().getController();
        List<PlaceOrderCard> placeOrderCardList = placeOrderView.getCardList();
        if (!placeOrderCardList.isEmpty()) {
            for (PlaceOrderCard placeOrderCard : placeOrderCardList) {
                for (CartDetail detail : cartDetailList) {
                    if (Objects.equals(placeOrderCard.getProduct().getId(), detail.getProduct().getId())) {
                        placeOrderCard.setBtnAddToCartActive();
                    }
                }
            }
        }
        if (!cartDetailList.isEmpty()) {
            cartDetailList.subList(0, cartDetailList.size()).clear();
            CartController.addToCart();
        }
        placeOrderPay.cancelInputs();
    }

    @Override
    public void btnCancelOptimize() {
        List<CartDetail> cartDetailList = CartController.getCART_DETAIL_LIST();
        PlaceOrderPay placeOrderPay = PlaceOrderCenterController.getInstance().getFxmlLoaderPay().getController();
        if (!cartDetailList.isEmpty() || !placeOrderPay.isEmpty()) {
            btnCancel.setDisable(false);
        }
        if (cartDetailList.isEmpty() && placeOrderPay.isEmpty()) {
            btnCancel.setDisable(true);
        }
    }

    @Override
    public void cancelForm() {
        btnCancelOnAction();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnCancel.setDisable(true);
    }
}
