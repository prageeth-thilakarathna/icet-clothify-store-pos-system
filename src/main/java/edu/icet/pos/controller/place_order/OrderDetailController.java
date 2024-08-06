package edu.icet.pos.controller.place_order;

import edu.icet.pos.controller.place_order.custom.PlaceOrderCard;
import edu.icet.pos.controller.place_order.custom.PlaceOrderDetailCart;
import edu.icet.pos.controller.place_order.custom.PlaceOrderView;
import edu.icet.pos.model.place_order.CartDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class OrderDetailController implements PlaceOrderDetailCart {
    @FXML
    private ComboBox<Integer> optQuantity;
    @FXML
    private ImageView dspImage;
    @FXML
    private Label dspDescription;
    @FXML
    private Label dspTotal;

    private CartDetail cartDetail;

    @FXML
    private void removeDetailMouseClicked() {
        PlaceOrderView placeOrderView = PlaceOrderCenterController.getInstance().getFxmlLoaderView().getController();
        List<PlaceOrderCard> placeOrderCardList = placeOrderView.getCardList();
        if (!placeOrderCardList.isEmpty()) {
            for (PlaceOrderCard placeOrderCard : placeOrderCardList) {
                if (Objects.equals(placeOrderCard.getProduct().getId(), cartDetail.getProduct().getId())) {
                    placeOrderCard.setBtnAddToCartActive();
                }
            }
        }
        CartController.getCART_DETAIL_LIST().remove(cartDetail);
        CartController.addToCart();
    }

    @FXML
    private void optQuantityAction() {
        double total = optQuantity.getValue() * cartDetail.getPrice();
        DecimalFormat df = new DecimalFormat("0.00");
        cartDetail.setQuantity(optQuantity.getValue());
        cartDetail.setTotal(Double.parseDouble(df.format(total)));
        CartController.addToCart();
    }

    private ObservableList<Integer> getQuantity(Integer avaQty) {
        ObservableList<Integer> quantities = FXCollections.observableArrayList();
        for (int i = 1; i <= (avaQty > 10 ? 10 : avaQty); i++) {
            quantities.add(i);
        }
        return quantities;
    }

    @Override
    public void setDetail(CartDetail cartDetail) throws SQLException {
        this.cartDetail = cartDetail;
        InputStream inputStream = cartDetail.getProduct().getImage().getBinaryStream();
        Image image = new Image(inputStream);
        dspImage.setImage(image);
        dspDescription.setText(cartDetail.getProduct().getDescription());
        optQuantity.setItems(getQuantity(cartDetail.getProduct().getQuantityOnHand()));
        optQuantity.setValue(cartDetail.getQuantity());
        DecimalFormat df = new DecimalFormat("0.00");
        dspTotal.setText("Rs. " + df.format(cartDetail.getTotal()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optQuantity.setVisibleRowCount(5);
    }
}
