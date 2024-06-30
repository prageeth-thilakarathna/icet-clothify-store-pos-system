package edu.icet.pos.controller.product;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.*;
import edu.icet.pos.controller.product.custom.ProductForm;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.entity.SubCategoryEntity;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.model.category.Category;
import edu.icet.pos.model.inventory.Inventory;
import edu.icet.pos.model.product.Product;
import edu.icet.pos.model.sub_category.SubCategory;
import edu.icet.pos.model.supplier.Supplier;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.modelmapper.ModelMapper;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormController implements ProductForm {
    @FXML
    private JFXComboBox<String> optCategory;
    @FXML
    private JFXComboBox<String> optSubCategory;
    @FXML
    private JFXComboBox<String> optSupplier;
    @FXML
    private TextField txtDescription;
    @FXML
    private JFXComboBox<String> optSize;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQuantityOnHand;
    @FXML
    private Button btnChooseImage;
    @FXML
    private TextField dspImageName;
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

    private final CategoryBo categoryBo = BoFactory.getBo(BoType.CATEGORY);
    private final SupplierBo supplierBo = BoFactory.getBo(BoType.SUPPLIER);
    private final SubCategoryBo subCategoryBo = BoFactory.getBo(BoType.SUB_CATEGORY);
    private static final String ACTIVE = "Active";
    private static final String DISABLE = "Disable";
    private final ProductBo productBo = BoFactory.getBo(BoType.PRODUCT);
    private final InventoryBo inventoryBo = BoFactory.getBo(BoType.INVENTORY);
    private File selectImage;

    @FXML
    private void optCategoryAction() {
        try{
            if(optCategory.getValue()!=null){
                assert categoryBo != null;
                Category category = categoryBo.getCategoryByName(optCategory.getValue());
                setSubCategory(category);
            }
            optSubCategory.setDisable(false);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } finally {
            validateInputs();
        }
    }

    private void setSubCategory(Category category){
        ObservableList<String> subCategoryArrayList = FXCollections.observableArrayList();
        try{
            assert subCategoryBo != null;
            List<SubCategory> subCategoryList = subCategoryBo.getSubCategoryByCategory(category);
            for(SubCategory subCategory : subCategoryList){
                subCategoryArrayList.add(subCategory.getName());
            }
            optSubCategory.setItems(subCategoryArrayList);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void optSubCategoryAction() {
        validateInputs();
    }

    @FXML
    private void optSupplierAction() {
        validateInputs();
    }

    @FXML
    private void descriptionKeyTyped() {
        validateInputs();
    }

    @FXML
    private void optSizeAction(ActionEvent actionEvent) {
        validateInputs();
    }

    @FXML
    private void priceKeyPressed(KeyEvent keyEvent) {

    }

    @FXML
    private void priceKeyTyped() {
        validateInputs();
    }

    @FXML
    private void quantityOnHandKeyPressed() {

    }

    @FXML
    private void quantityOnHandKeyTyped(KeyEvent keyEvent) {
        validateInputs();
    }

    @FXML
    private void btnChooseImageAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        Stage stage = new Stage();
        selectImage = fileChooser.showOpenDialog(stage);
        dspImageName.setText(selectImage.getName());
        Tooltip tooltip = new Tooltip(selectImage.getName());
        dspImageName.setTooltip(tooltip);
    }

    @FXML
    private void dspImageNameKeyTyped() {
        validateInputs();
    }

    @FXML
    private void optStatusAction() {
        validateInputs();
    }

    @FXML
    private void btnRegisterAction() {
        try{
            Product product = new Product();

            assert subCategoryBo != null;
            product.setSubCategory(new ModelMapper().map(subCategoryBo.getSubCategoryByName(optSubCategory.getValue()), SubCategoryEntity.class));
            int supplierId = Integer.parseInt(optSupplier.getValue().split("\\s")[0]);
            assert supplierBo != null;
            product.setSupplier(new ModelMapper().map(supplierBo.getSupplier(supplierId), SupplierEntity.class));
            product.setDescription(txtDescription.getText());
            product.setSize(optSize.getValue());
            product.setPrice(Double.parseDouble(txtPrice.getText()));
            product.setQuantityOnHand(Integer.parseInt(txtQuantityOnHand.getText()));

            FileInputStream fileInputStream = new FileInputStream(selectImage);
            byte[] bytes = new byte[(int) selectImage.length()];
            fileInputStream.read(bytes);

            product.setImage(new SerialBlob(bytes));

            product.setRegisterAt(new Date());
            product.setModifyAt(new Date());
            product.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));

            assert productBo != null;
            Product productRes = productBo.productRegister(product);
            qtyOnHandRegister(productRes);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Product and Quantity On Hand registration was successful.");
            alert.show();
            clearForm();

        } catch(Exception e){
            HibernateUtil.singletonRollback();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } finally {
            HibernateUtil.singletonSessionClose();
        }
    }

    private void qtyOnHandRegister(Product product){
        Inventory inventory = new Inventory();

        inventory.setProduct(new ModelMapper().map(product, ProductEntity.class));
        inventory.setStock(product.getQuantityOnHand());
        inventory.setRegisterAt(new Date());
        inventory.setModifyAt(new Date());
        inventory.setIsActive(true);

        assert inventoryBo != null;
        inventoryBo.qtyOnHandRegister(inventory);
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

    private void validateInputs() {
        if (!isInputEmpty()) {
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
        }

        btnCancel.setDisable(optCategory.getValue() == null &&
                optSubCategory.getValue() == null &&
                optSupplier.getValue() == null &&
                txtDescription.getLength() <= 0 &&
                optSize.getValue() == null &&
                txtPrice.getLength() <= 0 &&
                txtQuantityOnHand.getLength() <= 0 &&
                dspImageName.getLength() <= 4 &&
                optStatus.getValue() == null);
    }

    private boolean isInputEmpty() {
        if (
                optCategory.getValue() != null &&
                        optSubCategory.getValue() != null &&
                        optSupplier.getValue() != null &&
                        txtDescription.getLength() > 0 &&
                        optSize.getValue() != null &&
                        txtPrice.getLength() > 0 &&
                        txtQuantityOnHand.getLength() > 0 &&
                        dspImageName.getLength() > 4 &&
                        optStatus.getValue() != null
        ) {
            return false;
        } else {
            return true;
        }
    }

    private void clearForm(){
        optCategory.setValue(null);
        optCategory.setPromptText("   Select a Category");
        optSubCategory.setItems(null);
        optSubCategory.setPromptText("   Select a Sub Category");
        optSubCategory.setDisable(true);
        optSupplier.setValue(null);
        optSupplier.setPromptText("   Select a Supplier");
        txtDescription.setText("");
        optSize.setValue(null);
        optSize.setPromptText("   Select a Size");
        txtPrice.setText("");
        txtQuantityOnHand.setText("");
        dspImageName.setText("");
        optStatus.setValue("");
        optStatus.setPromptText("   Select a Status");
        btnRegister.setDisable(true);
        btnCancel.setDisable(true);
    }

    private ObservableList<String> getCategory(){
        ObservableList<String> categoryArrayList = FXCollections.observableArrayList();
        try{
            assert categoryBo != null;
            List<Category> categoryList = categoryBo.getAllCategory();
            for(Category category : categoryList){
                categoryArrayList.add(category.getName());
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return categoryArrayList;
    }

    private ObservableList<String> getSupplier(){
        ObservableList<String> supplierArrayList = FXCollections.observableArrayList();
        try{
            assert supplierBo != null;
            List<Supplier> supplierList = supplierBo.getAllSupplier();
            for(Supplier supplier : supplierList) {
                supplierArrayList.add(supplier.getId() + " - " + supplier.getTitle() + ". " + supplier.getFirstName() + " " + supplier.getLastName());
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return supplierArrayList;
    }

    private ObservableList<String> getStatus() {
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add(ACTIVE);
        statusList.add(DISABLE);
        return statusList;
    }

    private ObservableList<String> getSize(){
        ObservableList<String> sizeArrayList = FXCollections.observableArrayList();
        sizeArrayList.add("XXS");
        sizeArrayList.add("XS");
        sizeArrayList.add("S");
        sizeArrayList.add("M");
        sizeArrayList.add("L");
        sizeArrayList.add("XL");
        sizeArrayList.add("XXL");
        sizeArrayList.add("3X L");
        sizeArrayList.add("4X L");
        sizeArrayList.add("5X L");
        return sizeArrayList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optCategory.setItems(getCategory());
        optCategory.setVisibleRowCount(5);
        optSubCategory.setDisable(true);
        optSubCategory.setVisibleRowCount(5);
        optSupplier.setItems(getSupplier());
        optSupplier.setVisibleRowCount(5);
        btnRegister.setDisable(true);
        btnModify.setDisable(true);
        btnCancel.setDisable(true);
        btnActive.setDisable(true);
        btnDisable.setDisable(true);
        btnDelete.setDisable(true);
        optStatus.setItems(getStatus());
        optSize.setItems(getSize());
        optSize.setVisibleRowCount(5);
    }
}
