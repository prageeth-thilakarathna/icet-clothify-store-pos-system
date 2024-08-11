package edu.icet.pos.controller.inventory;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.InventoryBo;
import edu.icet.pos.controller.inventory.custom.InventoryForm;
import edu.icet.pos.controller.inventory.custom.InventorySearch;
import edu.icet.pos.model.inventory.Inventory;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class SearchController implements InventorySearch {
    @FXML
    private TextField txtInventoryId;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private Label dspRegisterAt;

    private final InventoryBo inventoryBo = BoFactory.getBo(BoType.INVENTORY);
    private Inventory searchInventory;
    private InventoryForm inventoryForm;

    @FXML
    private void inventoryIdKeyTyped() {
        validateInputs();
    }

    @FXML
    private void btnSearchOnXAction() {
        try {
            assert inventoryBo != null;
            searchInventory = inventoryBo.getInventory(Integer.parseInt(txtInventoryId.getText()));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dspRegisterAt.setText(dateFormat.format(searchInventory.getRegisterAt()));

            if (inventoryForm == null) {
                inventoryForm = InventoryCenterController.getInstance().getFxmlLoaderForm().getController();
            }
            inventoryForm.loadInventoryToForm(searchInventory);
            validateInputs();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelOnXAction() {
        clearForm();
    }

    private void validateInputs() {
        btnSearch.setDisable(txtInventoryId.getLength() <= 0 || searchInventory != null);
        if (searchInventory != null) {
            btnCancel.setDisable(false);
            txtInventoryId.setDisable(true);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private void clearForm() {
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
        txtInventoryId.setDisable(false);
        txtInventoryId.setText("");
        dspRegisterAt.setText("");
        searchInventory = null;
        if (inventoryForm == null) {
            inventoryForm = InventoryCenterController.getInstance().getFxmlLoaderForm().getController();
        }
        inventoryForm.clearInventory();
    }

    @Override
    public void clearSearch() {
        clearForm();
    }

    @Override
    public void refreshSearch() {
        clearForm();
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
    }
}
