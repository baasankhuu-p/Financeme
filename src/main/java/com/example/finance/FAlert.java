package com.example.finance;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class FAlert {
    public void errAlert(){

    }

    public void sucAlert(){

    }

    public void warAlert(){

    }

    public void infoAlert(String msg, String iconUrl, String btnTxt){
        //Цонхонд зураг оруулах
        Image img = new Image(iconUrl);
        ImageView imgvw = new ImageView(img);
        //Цонхоо үүсгэх
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(msg);
        //css - ээ холбож байна
        alert.getDialogPane().getStylesheets().add(Main.class.getResource("css/alert.css").toExternalForm());

        //Хэрэглэгчийн товч
        ButtonType btn = new ButtonType(btnTxt);
        alert.getDialogPane().getStyleClass().add("infoAlert");

        alert.setHeight(377);
        alert.setWidth(406);
        alert.setGraphic(imgvw);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(btn);
        alert.showAndWait();
    }
}
