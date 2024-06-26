package edu.icet.pos.controller.sub_category;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.SubCategoryBo;
import edu.icet.pos.controller.sub_category.custom.SubCategoryForm;
import edu.icet.pos.controller.sub_category.custom.SubCategorySearch;
import edu.icet.pos.model.sub_category.SubCategory;
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

public class SearchController implements SubCategorySearch {
    @FXML
    private TextField txtSubCategoryId;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private Label dspRegisterAt;
    @FXML
    private Label dspModifyAt;

    private SubCategory searchSubCategory;
    private final SubCategoryBo subCategoryBo = BoFactory.getBo(BoType.SUB_CATEGORY);
    private SubCategoryForm subCategoryForm;

    @FXML
    private void subCategoryIdKeyTyped() {
        validateInputs();
    }

    @FXML
    private void btnSearchAction() {
        try{
            assert subCategoryBo != null;
            SubCategory subCategory = subCategoryBo.getSubCategory(Integer.parseInt(txtSubCategoryId.getText()));
            searchSubCategory = subCategory;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dspRegisterAt.setText(dateFormat.format(subCategory.getRegisterAt()));
            dspModifyAt.setText(dateFormat.format(subCategory.getModifyAt()));

            if(subCategoryForm==null){
                subCategoryForm = SubCategoryCenterController.getInstance().getFxmlLoaderForm().getController();
            }
            subCategoryForm.loadSubCategoryToForm(subCategory);
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
        btnSearch.setDisable(txtSubCategoryId.getLength() <= 0 || searchSubCategory != null);
        if(searchSubCategory!=null){
            btnCancel.setDisable(false);
            txtSubCategoryId.setDisable(true);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private void clearForm(){
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
        txtSubCategoryId.setDisable(false);
        txtSubCategoryId.setText("");
        dspRegisterAt.setText("");
        dspModifyAt.setText("");
        searchSubCategory = null;
        subCategoryForm.clearSubCategory();
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
