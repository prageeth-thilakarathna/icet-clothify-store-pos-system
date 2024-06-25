package edu.icet.pos.controller.category;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.controller.category.custom.CategoryForm;
import edu.icet.pos.controller.category.custom.CategorySearch;
import edu.icet.pos.model.category.Category;
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

public class SearchController implements CategorySearch {
    @FXML
    private TextField txtCategoryId;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private Label dspRegisterAt;
    @FXML
    private Label dspModifyAt;

    private final CategoryBo categoryBo = BoFactory.getBo(BoType.CATEGORY);
    private CategoryForm categoryForm;
    private Category searchCategory;

    @FXML
    private void btnSearchAction() {
        try{
            assert categoryBo != null;
            Category category = categoryBo.getCategory(Integer.parseInt(txtCategoryId.getText()));
            searchCategory = category;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dspRegisterAt.setText(dateFormat.format(category.getRegisterAt()));
            dspModifyAt.setText(dateFormat.format(category.getModifyAt()));

            if(categoryForm ==null){
                categoryForm = CategoryCenterController.getInstance().getFxmlLoaderForm().getController();
            }
            categoryForm.loadCategoryToForm(category);
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

    @FXML
    private void categoryIdKeyTyped() {
        validateInputs();
    }

    private void validateInputs(){
        btnSearch.setDisable(txtCategoryId.getLength() <= 0 || searchCategory != null);
        if(searchCategory!=null){
            btnCancel.setDisable(false);
            txtCategoryId.setDisable(true);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private void clearForm(){
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
        txtCategoryId.setDisable(false);
        txtCategoryId.setText("");
        dspRegisterAt.setText("");
        dspModifyAt.setText("");
        searchCategory = null;
        categoryForm.clearCategory();
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
