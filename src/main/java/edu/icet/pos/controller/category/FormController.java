package edu.icet.pos.controller.category;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.controller.category.custom.CategoryForm;
import edu.icet.pos.model.Category;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormController implements CategoryForm {
    @FXML
    private TextField txtName;
    @FXML
    private JFXComboBox<String> optStatus;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCancel;

    private static final String ACTIVE = "Active";
    private static final String DISABLE = "Disable";
    private final CategoryBo categoryBo = BoFactory.getBo(BoType.CATEGORY);

    @FXML
    private void btnSubCategoryAction(ActionEvent actionEvent) {
    }

    @FXML
    private void nameKeyTyped(KeyEvent keyEvent) {
        validateInputs();
    }

    @FXML
    private void optStatusAction(ActionEvent keyEvent) {
        validateInputs();
    }

    @FXML
    private void btnRegisterAction(ActionEvent actionEvent) {
        if(!doesUserAlreadyExist()){
            try{
                Category category = new Category();
                category.setName(txtName.getText());
                category.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));
                category.setRegisterAt(new Date());
                category.setModifyAt(new Date());

                assert categoryBo != null;
                categoryBo.categoryRegister(category);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(txtName.getText()+" Category registration was successful.");
                alert.show();
                clearForm();

            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed! Your entered category name already exists.");
            alert.show();
        }
    }

    private boolean doesUserAlreadyExist(){
        try{
            assert categoryBo != null;
            Category category = categoryBo.getCategoryByName(txtName.getText());
            if(category!=null){
                return true;
            }
        } catch (Exception e){
            if(Objects.equals(e.getMessage(), "No result found for query [SELECT a FROM CategoryEntity a WHERE name='" + txtName.getText() + "']")){
                return false;
            }
        }
        return true;
    }

    @FXML
    private void btnModifyAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnDeleteAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnCancelAction() {
        clearForm();
    }

    private void validateInputs(){
        if(txtName.getLength()>0 && optStatus.getValue()!=null){
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
        }

        if(txtName.getLength()>0 || optStatus.getValue()!=null){
            btnCancel.setDisable(false);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private void clearForm(){
        txtName.setText("");
        optStatus.setValue(null);
        optStatus.setPromptText("   Select a Status");
        btnRegister.setDisable(true);
        btnCancel.setDisable(true);
    }

    private ObservableList<String> getStatus(){
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add(ACTIVE);
        statusList.add(DISABLE);
        return statusList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optStatus.setItems(getStatus());

        btnRegister.setDisable(true);
        btnCancel.setDisable(true);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
    }
}
