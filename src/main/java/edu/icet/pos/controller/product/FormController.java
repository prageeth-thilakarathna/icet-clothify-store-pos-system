package edu.icet.pos.controller.product;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.*;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.product.custom.ProductForm;
import edu.icet.pos.controller.product.custom.ProductSearch;
import edu.icet.pos.controller.product.custom.ProductView;
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
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.modelmapper.ModelMapper;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormController implements ProductForm {
    @FXML
    private VBox vBox;
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
    private Product searchProduct;
    private Image searchImage;
    private ProductSearch productSearch;
    private ProductView productView;
    private static final String MODIFICATION = "modification";

    @FXML
    private void optCategoryAction() {
        try {
            if (optCategory.getValue() != null) {
                assert categoryBo != null;
                Category category = categoryBo.getCategoryByName(optCategory.getValue());
                setSubCategory(category);
            }
            optSubCategory.setDisable(false);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } finally {
            validateInputs();
        }
    }

    private void setSubCategory(Category category) {
        ObservableList<String> subCategoryArrayList = FXCollections.observableArrayList();
        try {
            assert subCategoryBo != null;
            List<SubCategory> subCategoryList = subCategoryBo.getSubCategoryByCategory(category);
            for (SubCategory subCategory : subCategoryList) {
                if (Boolean.TRUE.equals(subCategory.getIsActive())) {
                    subCategoryArrayList.add(subCategory.getName());
                }
                if (searchProduct != null &&
                        (Objects.equals(subCategory.getName(), searchProduct.getSubCategory().getName()) &&
                                !searchProduct.getSubCategory().getIsActive())) {
                    subCategoryArrayList.add(subCategory.getName());
                }
            }
            optSubCategory.setItems(subCategoryArrayList);
        } catch (Exception e) {
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
    private void optSizeAction() {
        validateInputs();
    }

    @FXML
    private void priceKeyPressed(KeyEvent keyEvent) {
        int length = txtPrice.getLength();
        String ch = keyEvent.getCode().getChar();

        boolean condition = true;
        int count = 0;
        if (length > 0) {
            count++;
        }

        if (count == 0 && ch.equals("0")) {
            condition = false;
        }

        txtPrice.setEditable(length < 8 &&
                condition &&
                (ch.charAt(0) >= '0' && ch.charAt(0) <= '9') ||
                keyEvent.getCode().getCode() == 8 ||
                keyEvent.getCode().getCode() == 46);
    }

    @FXML
    private void priceKeyTyped() {
        validateInputs();
    }

    @FXML
    private void quantityOnHandKeyPressed(KeyEvent keyEvent) {
        int length = txtQuantityOnHand.getLength();
        int chInt = keyEvent.getCode().getCode();

        boolean condition = true;
        int count = 0;
        if (length > 0) {
            count++;
        }

        if (count == 0 && chInt == 48) {
            condition = false;
        }

        txtQuantityOnHand.setEditable(length < 5 &&
                condition &&
                (chInt >= 48 && chInt <= 57) ||
                chInt == 8);
    }

    @FXML
    private void quantityOnHandKeyTyped() {
        validateInputs();
    }

    @FXML
    private void btnChooseImageAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );
        Stage stage = new Stage();
        selectImage = fileChooser.showOpenDialog(stage);
        dspImageName.setText(selectImage.getName());
        Tooltip tooltip = new Tooltip(selectImage.getName());
        dspImageName.setTooltip(tooltip);
        validateInputs();
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
        try {
            Product product = new Product();

            assert subCategoryBo != null;
            assert categoryBo != null;
            product.setSubCategory(
                    new ModelMapper().map(subCategoryBo.getSubCategoryByName(optSubCategory.getValue(),
                                    categoryBo.getCategoryByName(optCategory.getValue())),
                            SubCategoryEntity.class)
            );
            int supplierId = Integer.parseInt(optSupplier.getValue().split("\\s")[0]);
            assert supplierBo != null;
            product.setSupplier(new ModelMapper().map(supplierBo.getSupplier(supplierId), SupplierEntity.class));
            product.setDescription(txtDescription.getText());
            product.setSize(optSize.getValue());
            product.setPrice(Double.parseDouble(txtPrice.getText()));
            product.setQuantityOnHand(Integer.parseInt(txtQuantityOnHand.getText()));

            try (FileInputStream fileInputStream = new FileInputStream(selectImage)) {
                byte[] bytes = new byte[(int) selectImage.length()];
                int res = fileInputStream.read(bytes);
                if (res != -1) {
                    product.setImage(new SerialBlob(bytes));
                }
            }

            product.setRegisterAt(new Date());
            product.setModifyAt(new Date());
            product.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));

            assert productBo != null;
            Product productRes = productBo.productRegister(product);
            qtyOnHandRegister(productRes);
            if (productView == null) {
                productView = ProductCenterController.getInstance().getFxmlLoaderView().getController();
            }
            productView.updatePanel("registration");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Product and Quantity On Hand registration was successful.");
            alert.show();
            clearForm();

        } catch (Exception e) {
            HibernateUtil.singletonRollback();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } finally {
            HibernateUtil.singletonSessionClose();
        }
    }

    private void qtyOnHandRegister(Product product) {
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
    private void btnModifyAction() {
        try {
            Product product = searchProduct;

            assert subCategoryBo != null;
            assert categoryBo != null;
            product.setSubCategory(
                    new ModelMapper().map(subCategoryBo.getSubCategoryByName(optSubCategory.getValue(),
                                    categoryBo.getCategoryByName(optCategory.getValue())),
                            SubCategoryEntity.class)
            );
            int supplierId = Integer.parseInt(optSupplier.getValue().split("\\s")[0]);
            assert supplierBo != null;
            product.setSupplier(new ModelMapper().map(supplierBo.getSupplier(supplierId), SupplierEntity.class));
            product.setDescription(txtDescription.getText());
            product.setSize(optSize.getValue());
            product.setPrice(Double.parseDouble(txtPrice.getText()));

            if (selectImage != null) {
                try (FileInputStream fileInputStream = new FileInputStream(selectImage)) {
                    byte[] bytes = new byte[(int) selectImage.length()];
                    int res = fileInputStream.read(bytes);
                    if (res != -1) {
                        product.setImage(new SerialBlob(bytes));
                    }
                }
            }
            product.setModifyAt(new Date());
            product.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));

            assert productBo != null;
            productBo.productUpdate(product);
            if (productView == null) {
                productView = ProductCenterController.getInstance().getFxmlLoaderView().getController();
            }
            productView.updatePanel(MODIFICATION);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(product.getId() + " Product modification was successful.");
            alert.show();
            clearProduct();
            if (productSearch == null) {
                productSearch = ProductCenterController.getInstance().getFxmlLoaderSearch().getController();
            }
            productSearch.clearSearch();

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

    @FXML
    private void btnActiveAction() {
        try {
            Product product = searchProduct;
            product.setIsActive(true);
            product.setModifyAt(new Date());

            assert productBo != null;
            productBo.productUpdate(product);
            if (productView == null) {
                productView = ProductCenterController.getInstance().getFxmlLoaderView().getController();
            }
            productView.updatePanel(MODIFICATION);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(product.getId() + " Product activation was successful.");
            alert.show();
            clearProduct();
            if (productSearch == null) {
                productSearch = ProductCenterController.getInstance().getFxmlLoaderSearch().getController();
            }
            productSearch.clearSearch();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnDisableAction() {
        try {
            Product product = searchProduct;
            product.setIsActive(false);
            product.setModifyAt(new Date());

            assert productBo != null;
            productBo.productUpdate(product);
            if (productView == null) {
                productView = ProductCenterController.getInstance().getFxmlLoaderView().getController();
            }
            productView.updatePanel(MODIFICATION);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(product.getId() + " Product disable was successful.");
            alert.show();
            clearProduct();
            if (productSearch == null) {
                productSearch = ProductCenterController.getInstance().getFxmlLoaderSearch().getController();
            }
            productSearch.clearSearch();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnDeleteAction() {
        try {
            assert productBo != null;
            productBo.productDelete(searchProduct);
            if (productView == null) {
                productView = ProductCenterController.getInstance().getFxmlLoaderView().getController();
            }
            productView.updatePanel("deletion");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(searchProduct.getId() + " Product deletion was successful.");
            alert.show();
            clearProduct();
            if (productSearch == null) {
                productSearch = ProductCenterController.getInstance().getFxmlLoaderSearch().getController();
            }
            productSearch.clearSearch();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void validateInputs() {
        if (!isInputEmpty() && searchProduct == null) {
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
            if (searchProduct != null) {
                validateModify();
            } else {
                btnModify.setDisable(true);
            }
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
        return optCategory.getValue() == null ||
                optSubCategory.getValue() == null ||
                optSupplier.getValue() == null ||
                txtDescription.getLength() <= 0 ||
                optSize.getValue() == null ||
                txtPrice.getLength() <= 0 ||
                txtQuantityOnHand.getLength() <= 0 ||
                (selectImage == null && searchImage == null) ||
                optStatus.getValue() == null;
    }

    private void validateModify() {
        if (!Objects.equals(searchProduct.getSubCategory().getName(), optSubCategory.getValue())) {
            btnModify.setDisable(isInputEmpty());
        } else if (optSupplier.getValue() != null &&
                (searchProduct.getSupplier().getId() !=
                        Integer.parseInt(optSupplier.getValue().split("\\s")[0]))) {
            btnModify.setDisable(isInputEmpty());
        } else if (!Objects.equals(searchProduct.getDescription(), txtDescription.getText())) {
            btnModify.setDisable(isInputEmpty());
        } else if (!Objects.equals(searchProduct.getSize(), optSize.getValue())) {
            btnModify.setDisable(isInputEmpty());
        } else if (!Objects.equals(String.valueOf(searchProduct.getPrice()), txtPrice.getText())) {
            btnModify.setDisable(isInputEmpty());
        } else if (!Objects.equals(String.valueOf(searchProduct.getQuantityOnHand()), txtQuantityOnHand.getText())) {
            btnModify.setDisable(isInputEmpty());
        } else if (selectImage != null) {
            btnModify.setDisable(isInputEmpty());
        } else if (Boolean.TRUE.equals(searchProduct.getIsActive()) ?
                Objects.equals(optStatus.getValue(), DISABLE) : Objects.equals(optStatus.getValue(), ACTIVE)) {
            btnModify.setDisable(isInputEmpty());
        } else {
            btnModify.setDisable(true);
        }
    }

    private void clearForm() {
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
        selectImage = null;
    }

    private ObservableList<String> getCategory() {
        ObservableList<String> categoryArrayList = FXCollections.observableArrayList();
        try {
            assert categoryBo != null;
            List<Category> categoryList = categoryBo.getAllCategory();
            for (Category category : categoryList) {
                if (Boolean.TRUE.equals(category.getIsActive())) {
                    categoryArrayList.add(category.getName());
                }
                if (searchProduct != null &&
                        (Objects.equals(category.getName(), searchProduct.getSubCategory().getCategory().getName()) &&
                                !searchProduct.getSubCategory().getCategory().getIsActive())) {
                    categoryArrayList.add(category.getName());
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return categoryArrayList;
    }

    private ObservableList<String> getSupplier() {
        ObservableList<String> supplierArrayList = FXCollections.observableArrayList();
        try {
            assert supplierBo != null;
            List<Supplier> supplierList = supplierBo.getAllSupplier();
            for (Supplier supplier : supplierList) {
                if (Boolean.TRUE.equals(supplier.getIsActive())) {
                    supplierArrayList.add(supplier.getId() +
                            " - " + supplier.getTitle() + ". " +
                            supplier.getFirstName() + " " + supplier.getLastName());
                }
                if (searchProduct != null &&
                        (Objects.equals(supplier.getId(), searchProduct.getSupplier().getId()) &&
                                !searchProduct.getSupplier().getIsActive())) {
                    supplierArrayList.add(supplier.getId() +
                            " - " + supplier.getTitle() + ". " +
                            supplier.getFirstName() + " " + supplier.getLastName());
                }
            }
        } catch (Exception e) {
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

    private ObservableList<String> getSize() {
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
    public void loadProductToForm(Product product) {
        searchProduct = product;
        loadForm();
        btnDelete.setDisable(false);

        if (Boolean.TRUE.equals(product.getIsActive())) {
            btnDisable.setDisable(false);
        } else {
            btnActive.setDisable(false);
        }

        optCategory.setValue(product.getSubCategory().getCategory().getName());
        optSubCategory.setValue(product.getSubCategory().getName());
        optSupplier.setValue(product.getSupplier().getId() +
                " - " + product.getSupplier().getTitle() + ". " +
                product.getSupplier().getFirstName() + " " + product.getSupplier().getLastName());
        txtDescription.setText(product.getDescription());
        optSize.setValue(product.getSize());
        txtPrice.setText(String.valueOf(product.getPrice()));
        txtQuantityOnHand.setText(String.valueOf(product.getQuantityOnHand()));

        try {
            InputStream inputStream = product.getImage().getBinaryStream();
            searchImage = new Image(inputStream);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        optStatus.setValue(Boolean.TRUE.equals(product.getIsActive()) ? ACTIVE : DISABLE);
        selectImage = null;
        dspImageName.setText("");
        txtQuantityOnHand.setDisable(true);
        validateInputs();
        authNotify();
    }

    @Override
    public void clearProduct() {
        searchProduct = null;
        clearForm();
        btnDelete.setDisable(true);
        searchImage = null;
        btnActive.setDisable(true);
        btnDisable.setDisable(true);
        txtQuantityOnHand.setDisable(false);
        loadForm();
        authNotify();
    }

    @Override
    public void refreshForm() {
        clearProduct();
        loadForm();
    }

    private void loadForm() {
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

    @Override
    public void authNotify() {
        if (AuthCenterController.isUser()) {
            vBox.getChildren().remove(adminController);
            vBox.setPrefHeight(477);
        }

        if (AuthCenterController.isAdmin()) {
            vBox.getChildren().remove(userController);
            vBox.setPrefHeight(477);
            txtDisable();
        }

        if (AuthCenterController.isUserCashier()) {
            userController.getChildren().remove(btnRegister);
            userController.getChildren().remove(btnModify);
            txtDisable();
        }
    }

    private void txtDisable() {
        optCategory.setDisable(true);
        optSubCategory.setDisable(true);
        optSupplier.setDisable(true);
        txtDescription.setDisable(true);
        optSize.setDisable(true);
        txtPrice.setDisable(true);
        txtQuantityOnHand.setDisable(true);
        btnChooseImage.setDisable(true);
        dspImageName.setDisable(true);
        optStatus.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadForm();
    }
}
