package edu.icet.pos.controller.auth;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.layout.LayoutCenterController;
import edu.icet.pos.controller.layout.custom.Layout;
import edu.icet.pos.model.User;
import edu.icet.pos.util.BoType;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgotPasswordPanelController implements Initializable {
    @FXML
    private TextField txtEmail;
    @FXML
    private Label dspSendMessage;
    @FXML
    private Button btnSendOtp;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtEnterOtp;
    @FXML
    private PasswordField txtNewPassword;
    @FXML
    private CheckBox newPasswordCheckBox;
    @FXML
    private Label dspNewPassword;
    @FXML
    private Button btnResetPassword;

    private final Random random = new Random();
    private String otpValue;
    private final UserBo userBo = BoFactory.getBo(BoType.USER);
    private Layout layout;

    @FXML
    private void btnSendOtpAction() {
        String otp = generateOTP();
        otpValue = otp;
        sendOTP(txtEmail.getText(), otp);

        btnSendOtp.setDisable(true);
        btnCancel.setDisable(false);
        txtEmail.setDisable(true);
        txtEnterOtp.setDisable(false);
        txtNewPassword.setDisable(false);
        newPasswordCheckBox.setDisable(false);
        validateInputs();
    }

    @FXML
    private void otpKeyTyped() {
        validateInputs();
    }

    @FXML
    private void newPasswordKeyTyped() {
        if(newPasswordCheckBox.isSelected()){
            dspNewPassword.setText(txtNewPassword.getText());
        } else {
            dspNewPassword.setText("");
        }
        validateInputs();
    }

    @FXML
    private void newPasswordCheckBoxAction() {
        if(newPasswordCheckBox.isSelected()){
            dspNewPassword.setText(txtNewPassword.getText());
        } else {
            dspNewPassword.setText("");
        }
    }

    @FXML
    private void btnResetPasswordAction() {
        if(Objects.equals(otpValue, txtEnterOtp.getText())){
            try{
                assert userBo != null;
                User user = userBo.getUserByEmail(txtEmail.getText());
                user.setPassword(CenterController.getInstance().encryptPassword(txtNewPassword.getText()));
                userBo.userUpdate(user);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(txtEmail.getText()+" User changing the password was successful.");
                alert.show();
                cancel();
                backToLogin();

            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed! Please enter a valid OTP.");
            alert.show();
        }
    }

    @FXML
    private void btnBackAction() {
        backToLogin();
    }

    @FXML
    private void btnCancelAction() {
        cancel();
        validateInputs();
    }

    private String generateOTP(){
        String numbers = "0123456789";
        char[] otp = new char[4];
        for(int i = 0; i<otp.length; i++){
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return new String(otp);
    }

    private void sendOTP(String to, String otp){
        Properties prop = new Properties();

        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("developer.prageeth@gmail.com", "vorc kgzn rbck cdwo");
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("developer.prageeth@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Clothify Store - Change Password");

            String msg = "OTP - "+otp;

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            dspSendMessage.setText("Sent");

        } catch (MessagingException e) {
            dspSendMessage.setText("");
            btnSendOtp.setDisable(false);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void cancel(){
        btnSendOtp.setDisable(false);
        btnCancel.setDisable(true);
        txtEmail.setDisable(false);
        txtEnterOtp.setDisable(true);
        txtNewPassword.setDisable(true);
        newPasswordCheckBox.setDisable(true);
        btnResetPassword.setDisable(true);
        txtEmail.setText("");
        txtEnterOtp.setText("");
        txtNewPassword.setText("");
        dspNewPassword.setText("");
        otpValue = null;
        dspSendMessage.setText("");
        newPasswordCheckBox.setSelected(false);
    }

    private void validateInputs(){
        btnResetPassword.setDisable(txtEnterOtp.getLength() != 4 || txtNewPassword.getLength() < 8);
    }

    private void backToLogin(){
        if(layout ==null){
            layout = LayoutCenterController.getInstance().getFxmlLoaderLayout().getController();
        }

        BorderPane borderPane = layout.getBorderPane();
        borderPane.getChildren().remove(borderPane.getRight());

        VBox rightVBox = new VBox();
        rightVBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightVBox.setPrefWidth(585);
        rightVBox.setPrefHeight(658);
        rightVBox.getChildren().add(AuthCenterController.getInstance().getParentLoginPanel());
        borderPane.setRight(rightVBox);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel();
    }
}
