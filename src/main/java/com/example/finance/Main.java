package com.example.finance;

import com.example.finance.conf.Functions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Function class-д хэрэгжүүлэгч функцүүд байна
        Functions fnc = new Functions();
        Parent root = FXMLLoader.load(getClass().getResource("xml/SignIn.fxml"));

        Scene scene = new Scene(root, 1280,720);
        scene.getStylesheets().add(Main.class.getResource("css/style.css").toExternalForm());
        // Function::windowMaxmized
        fnc.windowMaximized(stage);

        // Logo
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("img/logo.png")));
        stage.setTitle("Finance - Системд нэвтрэх");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}