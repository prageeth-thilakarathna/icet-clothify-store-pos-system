package edu.icet.pos.controller.category;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.category.custom.CategoryForm;
import edu.icet.pos.controller.category.custom.CategorySearch;
import edu.icet.pos.controller.category.custom.CategoryView;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.dashboard.custom.DashboardNavPanel;
import edu.icet.pos.controller.sub_category.SubCategoryCenterController;
import edu.icet.pos.controller.sub_category.custom.SubCategoryForm;
import edu.icet.pos.controller.sub_category.custom.SubCategorySearch;
import edu.icet.pos.controller.sub_category.custom.SubCategoryView;
import edu.icet.pos.model.category.Category;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private Category searchCategory;
    private CategorySearch categorySearch;
    private CategoryView categoryView;

    @FXML
    private void btnSubCategoryAction() {
        CenterController.getInstance().setPageHeader("Category", "/ Sub Category");
        Hyperlink pageMainHeader = CenterController.getInstance().getPageMainHeader();

        DashboardNavPanel dashboardNavPanel = DashboardCenterController.getInstance().getFxmlLoaderNav().getController();
        pageMainHeader.setOnAction(actionEvent1 -> dashboardNavPanel.loadCategory());

        CenterController.getInstance().defaultPageLayoutLoad(
                SubCategoryCenterController.getInstance().getParentForm(),
                SubCategoryCenterController.getInstance().getParentSearch(),
                SubCategoryCenterController.getInstance().getParentView()
        );

        SubCategoryForm subCategoryForm = SubCategoryCenterController.getInstance().getFxmlLoaderForm().getController();
        subCategoryForm.refreshForm();

        SubCategorySearch subCategorySearch = SubCategoryCenterController.getInstance().getFxmlLoaderSearch().getController();
        subCategorySearch.refreshSearch();

        SubCategoryView subCategoryView = SubCategoryCenterController.getInstance().getFxmlLoaderView().getController();
        subCategoryView.loadTable();
    }

    @FXML
    private void nameKeyTyped() {
        validateInputs();
    }

    @FXML
    private void optStatusAction() {
        validateInputs();
    }

    @FXML
    private void btnRegisterAction() {
        if (!doesCategoryAlreadyExist()) {
            try {
                Category category = new Category();
                category.setName(txtName.getText());
                category.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));
                category.setRegisterAt(new Date());
                category.setModifyAt(new Date());

                assert categoryBo != null;
                categoryBo.categoryRegister(category);
                if (categoryView == null) {
                    categoryView = CategoryCenterController.getInstance().getFxmlLoaderView().getController();
                }
                categoryView.updateTbl("registration");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(txtName.getText() + " Category registration was successful.");
                alert.show();
                clearForm();

            } catch (Exception e) {
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

    private boolean doesCategoryAlreadyExist() {
        try {
            assert categoryBo != null;
            Category category = categoryBo.getCategoryByName(txtName.getText());
            if (category != null) {
                return true;
            }
        } catch (Exception e) {
            if (Objects.equals(e.getMessage(), "No result found for query [SELECT a FROM CategoryEntity a WHERE name='" + txtName.getText() + "']")) {
                return false;
            }
        }
        return true;
    }

    @FXML
    private void btnModifyAction() {
        try {
            Category category = searchCategory;

            category.setName(txtName.getText());
            category.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));
            category.setModifyAt(new Date());

            assert categoryBo != null;
            categoryBo.categoryUpdate(category);
            if (categoryView == null) {
                categoryView = CategoryCenterController.getInstance().getFxmlLoaderView().getController();
            }
            categoryView.updateTbl("modification");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("The Category ID = " + searchCategory.getId() + " modification was successful.");
            alert.show();
            searchCategory = null;
            clearForm();
            if (categorySearch == null) {
                categorySearch = CategoryCenterController.getInstance().getFxmlLoaderSearch().getController();
            }
            categorySearch.clearSearch();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnDeleteAction() {
        try {
            assert categoryBo != null;
            categoryBo.categoryDelete(searchCategory);
            if (categoryView == null) {
                categoryView = CategoryCenterController.getInstance().getFxmlLoaderView().getController();
            }
            categoryView.updateTbl("deletion");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("The Category ID = " + searchCategory.getId() + " deletion was successful.");
            alert.show();
            searchCategory = null;
            clearForm();
            if (categorySearch == null) {
                categorySearch = CategoryCenterController.getInstance().getFxmlLoaderSearch().getController();
            }
            categorySearch.clearSearch();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelOnAction() {
        clearForm();
    }

    @Override
    public void loadCategoryToForm(Category category) {
        searchCategory = category;
        btnDelete.setDisable(false);
        txtName.setText(category.getName());
        optStatus.setValue(Boolean.TRUE.equals(category.getIsActive()) ? ACTIVE : DISABLE);
        validateModify();
    }

    @Override
    public void clearCategory() {
        searchCategory = null;
        clearForm();
        txtName.setDisable(false);
        btnDelete.setDisable(true);
    }

    @Override
    public void refreshForm() {
        clearCategory();
        loadForm();
    }

    private void validateInputs() {
        if (txtName.getLength() > 0 && optStatus.getValue() != null && searchCategory == null) {
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
            if (searchCategory != null) {
                validateModify();
            }
        }
        btnCancel.setDisable(txtName.getLength() <= 0 && optStatus.getValue() == null);
    }

    private void validateModify() {
        if (!Objects.equals(searchCategory.getName(), txtName.getText())) {
            btnModify.setDisable(txtName.getLength() <= 0 || optStatus.getValue() == null);
        } else if (Boolean.TRUE.equals(searchCategory.getIsActive()) ? Objects.equals(optStatus.getValue(), DISABLE) : Objects.equals(optStatus.getValue(), ACTIVE)) {
            btnModify.setDisable(txtName.getLength() <= 0 || optStatus.getValue() == null);
        } else {
            btnModify.setDisable(true);
        }
    }

    private void clearForm() {
        txtName.setText("");
        optStatus.setValue(null);
        optStatus.setPromptText("   Select a Status");
        btnRegister.setDisable(true);
        btnCancel.setDisable(true);
        btnModify.setDisable(true);
    }

    private ObservableList<String> getStatus() {
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add(ACTIVE);
        statusList.add(DISABLE);
        return statusList;
    }

    private void loadForm() {
        optStatus.setItems(getStatus());
        btnRegister.setDisable(true);
        btnCancel.setDisable(true);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadForm();
    }
}
