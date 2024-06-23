package edu.icet.pos.controller.auth;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class AuthCenterController {
    @Getter
    private static final AuthCenterController instance;
    private final FXMLLoader fxmlLoaderLogin = new FXMLLoader(getClass().getResource("/view/auth/loginPanel.fxml"));
    private final Parent parentLoginPanel;
    private final Parent parentBanner;
    private final FXMLLoader fxmlLoaderForgotPassword = new FXMLLoader(getClass().getResource("/view/auth/forgotPasswordPanel.fxml"));
    private final Parent parentForgotPassword;

    private AuthCenterController() throws IOException {
        parentLoginPanel = fxmlLoaderLogin.load();
        parentBanner = new FXMLLoader(getClass().getResource("/view/auth/banner.fxml")).load();
        parentForgotPassword = fxmlLoaderForgotPassword.load();
    }

    static {
        try{
            instance = new AuthCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating AuthCenterController singleton instance");
        }
    }
}
