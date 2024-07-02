package edu.icet.pos.controller.product;

import edu.icet.pos.controller.product.custom.ProductCard;
import edu.icet.pos.model.product.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CardController implements ProductCard {
    @FXML
    private ImageView dspImage;
    @FXML
    private Label dspId;
    @FXML
    private Label dspDescription;

    private Product product;

    @FXML
    private void cardMouseClicked(MouseEvent mouseEvent) {
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
