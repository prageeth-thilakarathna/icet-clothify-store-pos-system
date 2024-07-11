package edu.icet.pos.controller.place_order;

import edu.icet.pos.controller.place_order.custom.PlaceOrderCard;
import edu.icet.pos.model.place_order.OrderDetail;
import edu.icet.pos.model.product.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CardController implements PlaceOrderCard {
    @FXML
    private ImageView dspImage;
    @FXML
    private Label dspId;
    @FXML
    private Label dspDescription;
    @FXML
    private Label dspPrice;
    @FXML
    private Button btnAddToCart;

    private Product product;

    @FXML
    private void cardMouseClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void btnAddToCartAction() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(this.product);
        orderDetail.setQuantity(1);
        orderDetail.setTotal(product.getPrice());
        CartController.getInstance().setOrderDetail(orderDetail);
        btnAddToCart.setDisable(true);
    }

    @Override
    public void setDetail(Product product) throws SQLException {
        this.product = product;
        dspId.setText(String.valueOf(product.getId()));
        InputStream inputStream = product.getImage().getBinaryStream();
        Image image = new Image(inputStream);
        dspImage.setImage(image);
        dspDescription.setText(product.getDescription());
    }

    @Override
    public Product getProduct() {
        return this.product;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
