package edu.icet.pos.controller.place_order;

import edu.icet.pos.controller.place_order.custom.PlaceOrderDetailCart;
import edu.icet.pos.model.place_order.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
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

    @FXML
    private void removeDetailMouseClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void optQuantityAction(ActionEvent actionEvent) {

    }

    private ObservableList<Integer> getQuantity(){
        ObservableList<Integer> quantities = FXCollections.observableArrayList();
        for(int i=1; i<=10; i++){
            quantities.add(i);
        }
        return quantities;
    }

    @Override
    public void setDetail(OrderDetail orderDetail) throws SQLException {
        InputStream inputStream = orderDetail.getProduct().getImage().getBinaryStream();
        Image image = new Image(inputStream);
        dspImage.setImage(image);
        dspDescription.setText(orderDetail.getProduct().getDescription());
        optQuantity.setValue(orderDetail.getQuantity());
        dspTotal.setText("Rs. "+orderDetail.getTotal());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optQuantity.setItems(getQuantity());
    }
}
