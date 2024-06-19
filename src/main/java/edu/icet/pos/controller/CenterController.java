package edu.icet.pos.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.Getter;

@Getter
public class CenterController {
    private static CenterController instance;
    private final BorderPane pageBorderPane = new BorderPane();
    private final HBox pageTop = new HBox();
    private final Label pageHeader = new Label();
    private final VBox pageCenter = new VBox();
    private final VBox pageRight = new VBox();
    private final VBox pageBottom = new VBox();

    private CenterController(){
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
    }

    public static CenterController getInstance(){
        if(instance==null){
            instance = new CenterController();
        }
        return instance;
    }


}
