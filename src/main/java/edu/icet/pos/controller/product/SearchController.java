package edu.icet.pos.controller.product;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.controller.product.custom.ProductForm;
import edu.icet.pos.controller.product.custom.ProductSearch;
import edu.icet.pos.model.product.Product;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class SearchController implements ProductSearch {
    @FXML
    private TextField txtProductId;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private Label dspRegisterAt;
    @FXML
    private Label dspModifyAt;
    @FXML
    private ImageView dspImage;

    private final ProductBo productBo = BoFactory.getBo(BoType.PRODUCT);
    private Product searchProduct;
    private ProductForm productForm;

    @FXML
    private void productIdKeyTyped() {
        validateInputs();
    }

    @FXML
    private void btnSearchAction() {
        try{
            assert productBo != null;
            searchProduct = productBo.getProduct(Integer.parseInt(txtProductId.getText()));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dspRegisterAt.setText(dateFormat.format(searchProduct.getRegisterAt()));
            dspModifyAt.setText(dateFormat.format(searchProduct.getModifyAt()));

            InputStream inputStream = searchProduct.getImage().getBinaryStream();
            Image image = new Image(inputStream);
            dspImage.setImage(image);

            if (productForm==null){
                productForm = ProductCenterController.getInstance().getFxmlLoaderForm().getController();
            }
            productForm.loadProductToForm(searchProduct);
            validateInputs();

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelAction() {
        clearForm();
    }

    private void validateInputs(){
        btnSearch.setDisable(txtProductId.getLength() <= 0 || searchProduct != null);
        if(searchProduct!=null){
            btnCancel.setDisable(false);
            txtProductId.setDisable(true);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private void clearForm(){
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
        txtProductId.setDisable(false);
        txtProductId.setText("");
        dspRegisterAt.setText("");
        dspModifyAt.setText("");
        searchProduct = null;
        dspImage.setImage(null);
        productForm.clearProduct();
    }

    @Override
    public void clearSearch() {
        clearForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
    }
}
