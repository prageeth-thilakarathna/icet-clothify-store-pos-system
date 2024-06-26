package edu.icet.pos.controller.supplier;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.controller.supplier.custom.SupplierForm;
import edu.icet.pos.model.supplier.Supplier;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormController implements SupplierForm {
    @FXML
    private JFXComboBox<String> optTitle;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtAddress;
    @FXML
    private JFXComboBox<String> optStatus;
    @FXML
    private HBox userController;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnCancel;
    @FXML
    private HBox adminController;
    @FXML
    private Button btnActive;
    @FXML
    private Button btnDisable;
    @FXML
    private Button btnDelete;

    private static final String ACTIVE = "Active";
    private static final String DISABLE = "Disable";
    private final SupplierBo supplierBo = BoFactory.getBo(BoType.SUPPLIER);

    @FXML
    private void optTitleAction() {
        validateInputs();
    }

    @FXML
    private void firstNameKeyTyped() {
        validateInputs();
    }

    @FXML
    private void lastNameKeyTyped() {
        validateInputs();
    }

    @FXML
    private void contactKeyTyped() {
        validateInputs();
    }

    @FXML
    private void addressKeyTyped() {
        validateInputs();
    }

    @FXML
    private void optStatusAction() {
        validateInputs();
    }

    @FXML
    private void btnRegisterAction() {
        try{
            Supplier supplier = new Supplier();

            supplier.setTitle(optTitle.getValue());
            supplier.setFirstName(txtFirstName.getText());
            supplier.setLastName(txtLastName.getText());
            supplier.setContact(txtContact.getText());
            supplier.setAddress(txtAddress.getText());
            supplier.setRegisterAt(new Date());
            supplier.setModifyAt(new Date());
            supplier.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));

            assert supplierBo != null;
            supplierBo.supplierRegister(supplier);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(optTitle.getValue()+" "+txtFirstName.getText()+" "+txtLastName.getText()+" Supplier registration was successful.");
            alert.show();
            clearForm();

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnModifyAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnCancelAction() {
        clearForm();
    }

    @FXML
    private void btnActiveAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnDisableAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnDeleteAction(ActionEvent actionEvent) {
    }

    private void validateInputs(){
        if(
                optTitle.getValue()!=null &&
                txtFirstName.getLength()>0 &&
                txtLastName.getLength()>0 &&
                txtContact.getLength()>0 &&
                txtAddress.getLength()>0 &&
                optStatus.getValue()!=null
        ){
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
        }

        if(
                optTitle.getValue()!=null ||
                txtFirstName.getLength()>0 ||
                txtLastName.getLength()>0 ||
                txtContact.getLength()>0 ||
                txtAddress.getLength()>0 ||
                optStatus.getValue()!=null
        ){
            btnCancel.setDisable(false);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private void clearForm(){
        optTitle.setValue(null);
        optTitle.setPromptText("   Select a Title");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        optStatus.setValue(null);
        optStatus.setPromptText("   Select a Status");
        validateInputs();
    }

    private ObservableList<String> getStatus() {
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add(ACTIVE);
        statusList.add(DISABLE);
        return statusList;
    }

    private ObservableList<String> getTitles() {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Mrs");
        titles.add("Miss");
        titles.add("Ms");
        return titles;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optTitle.setItems(getTitles());
        optStatus.setItems(getStatus());
        btnRegister.setDisable(true);
        btnModify.setDisable(true);
        btnCancel.setDisable(true);
        btnActive.setDisable(true);
        btnDisable.setDisable(true);
        btnDelete.setDisable(true);
    }
}