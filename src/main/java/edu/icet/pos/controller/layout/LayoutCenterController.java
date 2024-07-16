package edu.icet.pos.controller.layout;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class LayoutCenterController {
    @Getter
    private static final LayoutCenterController instance;
    private final FXMLLoader fxmlLoaderLayout = new FXMLLoader(getClass().getResource("/view/layout/layout.fxml"));
    private final Parent parentLayout;

    private LayoutCenterController() throws IOException {
        parentLayout = fxmlLoaderLayout.load();
    }

    static {
        try {
            instance = new LayoutCenterController();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating LayoutCenterController singleton instance");
        }
    }
}
