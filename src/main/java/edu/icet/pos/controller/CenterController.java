package edu.icet.pos.controller;

import edu.icet.pos.controller.custom.SuperController;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.order.OrderCenterController;
import edu.icet.pos.controller.product.ProductCenterController;
import edu.icet.pos.controller.supplier.SupplierCenterController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.Getter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CenterController {
    @Getter
    private static final CenterController instance;
    private final BorderPane pageBorderPane = new BorderPane();
    private final HBox pageTop = new HBox();
    private final Label pageHeader = new Label();
    private final VBox pageCenter = new VBox();
    private final VBox pageRight = new VBox();
    private final VBox pageBottom = new VBox();
    private final Hyperlink pageMainHeader = new Hyperlink();
    private List<SuperController> superControllerList = new ArrayList<>();

    private CenterController() {
        pageTop.setPrefWidth(911);
        pageTop.setPrefHeight(51);
        pageTop.setAlignment(Pos.BOTTOM_LEFT);

        pageHeader.setFont(new Font("System", 18));
        pageHeader.setStyle("-fx-text-fill: #2f3548; -fx-font-weight: 700;");
        pageHeader.setTranslateX(20);

        pageCenter.setPrefWidth(611);
        pageCenter.setPrefHeight(274);
        pageCenter.setAlignment(Pos.CENTER);

        pageRight.setPrefWidth(300);
        pageRight.setPrefHeight(274);
        pageRight.setAlignment(Pos.CENTER);

        pageBottom.setPrefWidth(911);
        pageBottom.setPrefHeight(273);
        pageBottom.setAlignment(Pos.CENTER);

        pageMainHeader.setStyle("-fx-text-fill: #2f3548; -fx-font-weight: 700; -fx-underline: true; -fx-font-size: 18px;");
        pageMainHeader.setTranslateX(20);
        pageMainHeader.setBorder(Border.EMPTY);
    }

    static {
        try {
            instance = new CenterController();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating CenterController singleton instance");
        }
    }

    public String encryptPassword(String password) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());

            byte[] bytes = m.digest();

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0 * 100, 16).substring(1));
            }
            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return null;
    }

    public List<SuperController> getSuperControllerList() {
        if (superControllerList.isEmpty()) {
            setSuperController();
        }
        if (!superControllerList.isEmpty()) {
            superControllerList = new ArrayList<>();
            setSuperController();
        }
        return superControllerList;
    }

    private void setSuperController() {
        superControllerList.add(DashboardCenterController.getInstance().getFxmlLoaderHeader().getController());

        FXMLLoader fxmlLoaderNav = new FXMLLoader(getClass().getResource("/view/dashboard/navPanel.fxml"));
        DashboardCenterController.getInstance().setFxmlLoaderNav(fxmlLoaderNav);

        FXMLLoader fxmlLoaderReportNav = new FXMLLoader(getClass().getResource("/view/dashboard/report-nav.fxml"));
        DashboardCenterController.getInstance().setFxmlLoaderReportNav(fxmlLoaderReportNav);

        FXMLLoader fxmlLoaderSupplierForm = new FXMLLoader(getClass().getResource("/view/supplier/form.fxml"));
        SupplierCenterController.getInstance().setFxmlLoaderForm(fxmlLoaderSupplierForm);

        FXMLLoader fxmlLoaderProductForm = new FXMLLoader(getClass().getResource("/view/product/form.fxml"));
        ProductCenterController.getInstance().setFxmlLoaderForm(fxmlLoaderProductForm);

        try {
            DashboardCenterController.getInstance().setParentNav(fxmlLoaderNav.load());
            DashboardCenterController.getInstance().setParentReportNav(fxmlLoaderReportNav.load());
            SupplierCenterController.getInstance().setParentForm(fxmlLoaderSupplierForm.load());
            ProductCenterController.getInstance().setParentForm(fxmlLoaderProductForm.load());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

        superControllerList.add(DashboardCenterController.getInstance().getFxmlLoaderNav().getController());
        superControllerList.add(DashboardCenterController.getInstance().getFxmlLoaderReportNav().getController());
        superControllerList.add(SupplierCenterController.getInstance().getFxmlLoaderForm().getController());
        superControllerList.add(ProductCenterController.getInstance().getFxmlLoaderForm().getController());
        superControllerList.add(OrderCenterController.getInstance().getFxmlLoaderView().getController());
    }

    public void defaultPageLayoutLoad(Node center, Node right, Node bottom) {
        pageBorderPane.getChildren().removeAll(pageBorderPane.getChildren());

        pageBorderPane.setTop(pageTop);

        pageBorderPane.setCenter(center);
        pageBorderPane.setRight(right);
        pageBorderPane.setBottom(bottom);
    }

    public void defaultPageLayoutLoad(Node center) {
        pageBorderPane.getChildren().removeAll(pageBorderPane.getChildren());

        pageBorderPane.setTop(pageTop);

        pageBorderPane.setCenter(center);
    }

    public void setPageHeader(String mainHeader, String subHeader){
        pageTop.getChildren().clear();

        pageMainHeader.setText(mainHeader);
        pageHeader.setText(subHeader);

        pageTop.getChildren().add(pageMainHeader);
        pageTop.getChildren().add(pageHeader);
    }
}
