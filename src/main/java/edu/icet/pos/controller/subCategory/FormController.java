package edu.icet.pos.controller.subCategory;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.bo.custom.SubCategoryBo;
import edu.icet.pos.controller.subCategory.custom.SubCategoryForm;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.model.category.Category;
import edu.icet.pos.model.sub_category.SubCategory;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormController implements SubCategoryForm {
    @FXML
    private TextField txtName;
    @FXML
    private JFXComboBox<String> optCategory;
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
    private final SubCategoryBo subCategoryBo = BoFactory.getBo(BoType.SUB_CATEGORY);

    @FXML
    private void nameKeyTyped(KeyEvent keyEvent) {
        validateInputs();
    }

    @FXML
    private void optCategoryAction(ActionEvent actionEvent) {
        validateInputs();
    }

    @FXML
    private void optStatusAction(ActionEvent actionEvent) {
        validateInputs();
    }

    @FXML
    private void btnRegisterAction() {
        if(!doesSubCategoryAlreadyExist()){
            try{
                SubCategory subCategory = new SubCategory();
                subCategory.setName(txtName.getText());
                assert categoryBo != null;
                subCategory.setCategory(new ModelMapper().map(categoryBo.getCategoryByName(optCategory.getValue()), CategoryEntity.class));
                subCategory.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));
                subCategory.setRegisterAt(new Date());
                subCategory.setModifyAt(new Date());

                assert subCategoryBo != null;
                subCategoryBo.subCategoryRegister(subCategory);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(txtName.getText()+" Sub Category registration was successful.");
                alert.show();
                clearForm();

            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed! Your entered sub category name already exists.");
            alert.show();
        }

    }

    @FXML
    private void btnModifyAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnDeleteAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnCancelAction(ActionEvent actionEvent) {
        clearForm();
    }

    private boolean doesSubCategoryAlreadyExist(){
        try{
            assert subCategoryBo != null;
            SubCategory subCategory = subCategoryBo.getSubCategoryByName(txtName.getText());
            if(subCategory!=null){
                return true;
            }
        } catch (Exception e){
            if(Objects.equals(e.getMessage(), "No result found for query [SELECT a FROM SubCategoryEntity a WHERE name='" + txtName.getText() + "']")){
                return false;
            }
        }
        return true;
    }

    private void clearForm(){
        txtName.setText("");
        optCategory.setValue(null);
        optCategory.setPromptText("   Select a Category");
        optStatus.setValue(null);
        optStatus.setPromptText("   Select a Status");
        validateInputs();
    }

    private void validateInputs(){
        if(txtName.getLength()>0 && optCategory.getValue()!=null && optStatus.getValue()!=null){
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
        }

        if(txtName.getLength()>0 || optCategory.getValue()!=null || optStatus.getValue()!=null){
            btnCancel.setDisable(false);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private ObservableList<String> getCategory(){
        ObservableList<String> categories = FXCollections.observableArrayList();
        try{
            assert categoryBo != null;
            List<Category> categoryList = categoryBo.getAllCategory();
            for(Category category : categoryList){
                categories.add(category.getName());
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return categories;
    }

    private ObservableList<String> getStatus(){
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add(ACTIVE);
        statusList.add(DISABLE);
        return statusList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optCategory.setItems(getCategory());
        optCategory.setVisibleRowCount(5);
        optStatus.setItems(getStatus());
        btnRegister.setDisable(true);
        btnCancel.setDisable(true);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
    }
}
