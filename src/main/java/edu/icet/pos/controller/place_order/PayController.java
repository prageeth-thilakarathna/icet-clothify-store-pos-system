package edu.icet.pos.controller.place_order;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.bo.custom.OrderDetailBo;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.place_order.custom.PlaceOrderCartHeader;
import edu.icet.pos.controller.place_order.custom.PlaceOrderPay;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.model.order.Order;
import edu.icet.pos.model.order.OrderDetail;
import edu.icet.pos.model.place_order.CartDetail;
import edu.icet.pos.model.product.Product;
import edu.icet.pos.model.report.InvoiceItem;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.HibernateUtil;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class PayController implements PlaceOrderPay {
    @FXML
    private JFXComboBox<String> optPaymentType;
    @FXML
    private Label dspNetTotal;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnPay;

    private final OrderBo orderBo = BoFactory.getBo(BoType.ORDER);
    private final OrderDetailBo orderDetailBo = BoFactory.getBo(BoType.ORDER_DETAIL);
    private final ProductBo productBo = BoFactory.getBo(BoType.PRODUCT);

    @FXML
    private void nameKeyTyped() {
        validateInputs();
    }

    @FXML
    private void emailKeyTyped() {
        validateInputs();
    }

    @FXML
    private void optPaymentTypeAction() {
        validateInputs();
    }

    @FXML
    private void btnPayAction() {
        Order order = new Order();
        Employee employee;
        Order resOrder = null;
        try{
            employee = AuthCenterController.getInstance().getEmployee();
            order.setEmployee(new ModelMapper().map(employee, EmployeeEntity.class));
            order.setEmployeeName(
                    employee.getTitle() +
                            ". " +
                            employee.getFirstName() +
                            employee.getLastName()
            );
            order.setCustomerName(txtName.getText());
            order.setCustomerEMail(txtEmail.getText());
            order.setPaymentType(optPaymentType.getValue());
            order.setRegisterAt(new Date());
            order.setReturnAt(null);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

        try {
            assert orderBo != null;
            resOrder = orderBo.orderRegister(order);
            orderDetailRegister(resOrder);
            HibernateUtil.singletonCommit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(resOrder.getId() + " Order is successful.");
            alert.show();
        } catch (Exception e) {
            HibernateUtil.singletonRollback();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } finally {
            HibernateUtil.singletonSessionClose();
        }

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("report/invoice.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(getInvoiceData());
            Map<String, Object> parameters = new HashMap<>();
            assert resOrder != null;
            parameters.put("INVOICE_ID", "INV"+resOrder.getId());
            parameters.put("NET_TOTAL", getTotal());
            parameters.put("CUSTOMER_NAME", txtName.getText());
            parameters.put("EMAIL", txtEmail.getText());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            File invoicePDF = File.createTempFile("invoice", ".pdf");

            try (OutputStream outputStream = new FileOutputStream(invoicePDF)) {
                outputStream.write(bytes);
            }
            sendReceipt(txtName.getText(), txtEmail.getText(), invoicePDF);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("# INV"+resOrder.getId() + " Invoice was sent successfully.");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } finally {
            PlaceOrderCartHeader placeOrderCartHeader =
                    PlaceOrderCenterController.getInstance().getFxmlLoaderCartHeader().getController();
            placeOrderCartHeader.cancelForm();
        }
    }

    private String getTotal(){
        List<CartDetail> cartDetailList = CartController.getCART_DETAIL_LIST();
        DecimalFormat df = new DecimalFormat("0.00");
        double total = 0.0;
        for(CartDetail cartDetail : cartDetailList){
            total += cartDetail.getTotal();
        }
        return df.format(total);
    }

    private void sendReceipt(String customerName, String to, File invoicePDF) {
        Properties prop = new Properties();

        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("prageeth.c.thilakarathna@gmail.com", "ilxg tiiq jmmr ifku");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("prageeth.c.thilakarathna@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Clothify Store - Invoice");

            Multipart multipart = getMultipart(customerName, invoicePDF);

            message.setContent(multipart);
            Transport.send(message);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private static Multipart getMultipart(String customerName, File invoicePDF) throws MessagingException, IOException {
        String msg = "Thanks, " + customerName + "; for your purchases." +
                " Your invoice is attached below.";

        MimeBodyPart mimeBodyMSG = new MimeBodyPart();
        mimeBodyMSG.setContent(msg, "text/html; charset=utf-8");

        MimeBodyPart mimeBodyATH = new MimeBodyPart();
        mimeBodyATH.attachFile(invoicePDF);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyMSG);
        multipart.addBodyPart(mimeBodyATH);
        return multipart;
    }

    private List<InvoiceItem> getInvoiceData() {
        List<CartDetail> cartDetailList = CartController.getCART_DETAIL_LIST();
        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");
        for (CartDetail cartDetail : cartDetailList) {
            InvoiceItem invoiceItem = new InvoiceItem(
                    cartDetail.getProduct().getDescription(),
                    String.valueOf(cartDetail.getQuantity()),
                    df.format(cartDetail.getPrice()),
                    df.format(cartDetail.getTotal())
            );
            invoiceItemList.add(invoiceItem);
        }
        return invoiceItemList;
    }

    private void orderDetailRegister(Order order) {
        List<CartDetail> cartDetailList = CartController.getCART_DETAIL_LIST();
        for (CartDetail cartDetail : cartDetailList) {
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrders(new ModelMapper().map(order, OrderEntity.class));
            orderDetail.setProduct(new ModelMapper().map(cartDetail.getProduct(), ProductEntity.class));
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(cartDetail.getPrice());
            orderDetail.setTotal(cartDetail.getTotal());

            assert orderDetailBo != null;
            orderDetailBo.orderDetailRegister(orderDetail);

            assert productBo != null;
            productBo.productAvaQtyUpdate(getAvaQty(cartDetail));
        }
    }

    private Product getAvaQty(CartDetail cartDetail) {
        Integer avaQty = cartDetail.getProduct().getQuantityOnHand();
        cartDetail.getProduct().setQuantityOnHand(
                avaQty - cartDetail.getQuantity()
        );
        return cartDetail.getProduct();
    }

    @Override
    public void validateInputs() {
        if (txtName.getLength() > 0 &&
                txtEmail.getLength() > 0 &&
                optPaymentType.getValue() != null &&
                !CartController.getCART_DETAIL_LIST().isEmpty()
        ) {
            btnPay.setDisable(false);
        }
        if (txtName.getLength() == 0 ||
                txtEmail.getLength() == 0 ||
                optPaymentType.getValue() == null ||
                CartController.getCART_DETAIL_LIST().isEmpty()
        ) {
            btnPay.setDisable(true);
        }

        PlaceOrderCartHeader placeOrderCartHeader =
                PlaceOrderCenterController.getInstance().getFxmlLoaderCartHeader().getController();
        placeOrderCartHeader.btnCancelOptimize();
    }

    private ObservableList<String> getPaymentTypes() {
        ObservableList<String> paymentTypes = FXCollections.observableArrayList();
        paymentTypes.add("Card");
        paymentTypes.add("Cash");
        return paymentTypes;
    }

    @Override
    public boolean isEmpty() {
        return txtName.getLength() <= 0 &&
                txtEmail.getLength() <= 0 &&
                optPaymentType.getValue() == null;
    }

    @Override
    public void cancelInputs() {
        txtName.setText("");
        txtEmail.setText("");
        optPaymentType.setValue(null);
        optPaymentType.setPromptText("   Payment Type");
        dspNetTotal.setText("Rs. 0.00");
        validateInputs();
    }

    @Override
    public void setNetTotal() {
        List<CartDetail> cartDetailList = CartController.getCART_DETAIL_LIST();
        double netTotal = 0.0;
        DecimalFormat df = new DecimalFormat("0.00");
        for (CartDetail cartDetail : cartDetailList) {
            netTotal += cartDetail.getTotal();
        }
        dspNetTotal.setText("Rs. " + df.format(netTotal));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optPaymentType.setItems(getPaymentTypes());
        btnPay.setDisable(true);
        dspNetTotal.setText("Rs. 0.00");
    }
}
