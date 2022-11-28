package com.example.finance.conf;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.stage.*;

public class Functions {

    /**
     * Тухайн цонхыг дэлгэцэндээ дүүргэж харуулах функц
     * @param stage
     */
    public void windowMaximized(Stage stage){
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
    }

    /**
     * цэвэрлэх функц Overriding
     * clear(txt, txt ,txt ,txt ,txt)
     * clear(txt ,txt)
     */
    public void clear(TextField name, PasswordField pass, TextField phone, TextField mail, PasswordField confirmPass){
        name.setText("");
        pass.setText("");
        phone.setText("");
        mail.setText("");
        confirmPass.setText("");
    }

    public void clear(TextField name, PasswordField pass){
        name.setText("");
        pass.setText("");
    }

    /**
     * Label nameщ => тухайн мсж өгөх гэж буй control
     * String msg => тайлбарыг бичиж өгнө
     * clearmsg-> нэвтэрсний дараа form-n мэдээллийг цэвэрлэх хэрэгтэй
     * */
    public void clearmsg(Label name){
        name.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-text-fill:rgba(0, 0, 0, 0)");
    }

    /* *
     * Label name => тухайн мсж өгөх гэж буй control
     * String msg => тайлбарыг бичиж өгнө
     * successMsg-> амжилттай мэдээлл оруулсаныг харуулах
     * */
    public void successMsg(Label name, String s_msg){
        clearmsg(name);
        name.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: rgba(68, 250, 87, 0.8); -fx-font-size: 18px ; -fx-font-weight: bold");
        name.setText(s_msg);
    }

    /**
     * Label name => тухайн мсж өгөх гэж буй control
     * String msg => тайлбарыг бичиж өгнө
     * successMsg-> алдаатай мэдээлл оруулсаныг харуулах
     * */
    public void errMsg(Label name, String s_msg)
    {
        clearmsg(name);
        name.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-text-fill:rgba(192, 33, 33, 0.5); -fx-font-size: 18px ; -fx-font-weight: bold");
        name.setText(s_msg);
    }
}