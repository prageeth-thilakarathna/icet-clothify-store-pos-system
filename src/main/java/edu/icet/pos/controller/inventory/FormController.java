package edu.icet.pos.controller.inventory;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.InventoryBo;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.controller.inventory.custom.InventoryForm;
import edu.icet.pos.controller.inventory.custom.InventoryView;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.model.inventory.Inventory;
import edu.icet.pos.model.product.Product;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class FormController implements InventoryForm {
    @FXML
    private TextField txtProductId;
    @FXML
    private TextField txtQtyOnHand;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnCancel;

    private final ProductBo productBo = BoFactory.getBo(BoType.PRODUCT);
    private Product searchProduct;
    private final InventoryBo inventoryBo = BoFactory.getBo(BoType.INVENTORY);
    private InventoryView inventoryView;

    @FXML
    private void productIdKeyTyped() {
        btnSearch.setDisable(txtProductId.getLength() == 0);
        validateInputs();
    }

    @FXML
    private void qtyOnHandKeyPressed(KeyEvent keyEvent) {
        int length = txtQtyOnHand.getLength();
        int chInt = keyEvent.getCode().getCode();

        boolean condition = true;
        int count = 0;
        if (length > 0) {
            count++;
        }

        if (count == 0 && chInt == 48) {
            condition = false;
        }

        txtQtyOnHand.setEditable(length < 5 &&
                condition &&
                (chInt >= 48 && chInt <= 57) ||
                chInt == 8);
    }

    @FXML
    private void qtyOnHandKeyTyped() {
        validateInputs();
    }

    @FXML
    private void btnSearchAction() {
        try {
            assert productBo != null;
            searchProduct = productBo.getProduct(Integer.parseInt(txtProductId.getText()));
            txtProductId.setDisable(true);
            btnSearch.setDisable(true);
            validateInputs();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnRegisterAction() {
        try {
            Inventory inventory = new Inventory();
            inventory.setProduct(new ModelMapper().map(searchProduct, ProductEntity.class));
            inventory.setStock(Integer.parseInt(txtQtyOnHand.getText()));
            inventory.setRegisterAt(new Date());

            assert inventoryBo != null;
            inventoryBo.inventoryRegister(inventory);

            Integer qtyOnHand = searchProduct.getQuantityOnHand() + Integer.parseInt(txtQtyOnHand.getText());
            searchProduct.setQuantityOnHand(qtyOnHand);
            searchProduct.setModifyAt(new Date());

            assert productBo != null;
            productBo.productAvaQtyUpdate(searchProduct);
            HibernateUtil.singletonCommit();

            if (inventoryView == null) {
                inventoryView = InventoryCenterController.getInstance().getFxmlLoaderView().getController();
            }
            inventoryView.updateTbl("registration");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Inventory registration is successful.");
            alert.show();
            btnCancelOnXAction();

        } catch (Exception e) {
            HibernateUtil.singletonRollback();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } finally {
            HibernateUtil.singletonSessionClose();
        }
    }

    @FXML
    private void btnCancelOnXAction() {
        clearForm();
        validateInputs();
    }

    private void validateInputs() {
        btnRegister.setDisable(isInputEmpty() || searchProduct == null);
        btnCancel.setDisable(txtProductId.getLength() == 0 && txtQtyOnHand.getLength() == 0);
    }

    private boolean isInputEmpty() {
        return txtProductId.getLength() == 0 ||
                txtQtyOnHand.getLength() == 0;
    }

    private void clearForm() {
        txtProductId.setDisable(false);
        txtProductId.setText("");
        btnSearch.setDisable(true);
        searchProduct = null;
        txtQtyOnHand.setText("");
    }

    private void loadForm() {
        btnSearch.setDisable(true);
        btnRegister.setDisable(true);
        btnCancel.setDisable(true);
    }

    @Override
    public void clearInventory() {
        clearForm();
        txtProductId.setDisable(false);
        txtQtyOnHand.setDisable(false);
    }

    @Override
    public void loadInventoryToForm(Inventory inventory) {
        txtProductId.setText(String.valueOf(inventory.getId()));
        txtQtyOnHand.setText(String.valueOf(inventory.getStock()));
        txtProductId.setDisable(true);
        btnSearch.setDisable(true);
        txtQtyOnHand.setDisable(true);
        btnRegister.setDisable(true);
        btnCancel.setDisable(true);
    }

    @Override
    public void refreshForm() {
        clearInventory();
        loadForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadForm();
    }
}
