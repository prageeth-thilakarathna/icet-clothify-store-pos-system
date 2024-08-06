package edu.icet.pos.controller.place_order;

import edu.icet.pos.controller.place_order.custom.PlaceOrderCard;
import edu.icet.pos.model.place_order.CartDetail;
import edu.icet.pos.model.product.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.InputStream;
import java.sql.SQLException;

public class CardController implements PlaceOrderCard {
    @FXML
    private Label dspOutOfStocks;
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
        CartDetail cartDetail = new CartDetail(
                this.product,
                1,
                product.getPrice(),
                product.getPrice()
        );
        CartController.getCART_DETAIL_LIST().add(cartDetail);
        CartController.addToCart();
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
        dspPrice.setText(String.valueOf(product.getPrice()));
        if(product.getQuantityOnHand()==0){
            dspOutOfStocks.setVisible(true);
            btnAddToCart.setDisable(true);
        }
    }

    @Override
    public Product getProduct() {
        return this.product;
    }

    @Override
    public void setBtnAddToCartDisable() {
        btnAddToCart.setDisable(true);
    }

    @Override
    public void setBtnAddToCartActive() {
        btnAddToCart.setDisable(false);
    }
}
