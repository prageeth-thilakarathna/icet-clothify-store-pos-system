package edu.icet.pos.controller.supplier;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.controller.supplier.custom.SupplierForm;
import edu.icet.pos.controller.supplier.custom.SupplierSearch;
import edu.icet.pos.model.supplier.Supplier;
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

public class SearchController implements SupplierSearch {
    @FXML
    private TextField txtSupplierId;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private Label dspRegisterAt;
    @FXML
    private Label dspModifyAt;

    private Supplier searchSupplier;
    private final SupplierBo supplierBo = BoFactory.getBo(BoType.SUPPLIER);
    private SupplierForm supplierForm;

    @FXML
    private void supplierIdKeyTyped() {
        validateInputs();
    }

    @FXML
    private void btnSearchAction() {
        try {
            assert supplierBo != null;
            searchSupplier = supplierBo.getSupplier(Integer.parseInt(txtSupplierId.getText()));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dspRegisterAt.setText(dateFormat.format(searchSupplier.getRegisterAt()));
            dspModifyAt.setText(dateFormat.format(searchSupplier.getModifyAt()));

            supplierForm = SupplierCenterController.getInstance().getFxmlLoaderForm().getController();
            supplierForm.loadSupplierToForm(searchSupplier);
            validateInputs();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelAction() {
        clearForm();
    }

    private void validateInputs() {
        btnSearch.setDisable(txtSupplierId.getLength() <= 0 || searchSupplier != null);
        if (searchSupplier != null) {
            btnCancel.setDisable(false);
            txtSupplierId.setDisable(true);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private void clearForm() {
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
        txtSupplierId.setDisable(false);
        txtSupplierId.setText("");
        dspRegisterAt.setText("");
        dspModifyAt.setText("");
        searchSupplier = null;
        supplierForm = SupplierCenterController.getInstance().getFxmlLoaderForm().getController();
        supplierForm.clearSupplier();
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
