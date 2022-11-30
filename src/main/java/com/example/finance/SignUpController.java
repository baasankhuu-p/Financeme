package com.example.finance;

import com.example.finance.conf.Database;
import com.example.finance.conf.Functions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    Functions fnc = new Functions();

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    RadioButton type_rdbtn1,type_rdbtn2;
    String s_userType = "Админ";
    @FXML
    private PasswordField confirmTxt,passTxt;

    @FXML
    private TextField
            mailTxt,
            nameTxt,
            phoneTxt;

    @FXML
    private Button signInBtn,
            signUpBtn;
    @FXML
    private Label msgLbl;

    @FXML
    public void selected1(MouseEvent event){
        s_userType = "Админ";
        type_rdbtn2.setSelected(false);
        type_rdbtn1.setSelected(true);
    }
    @FXML
    public void selected2(MouseEvent event){
        s_userType = "Хэрэглэгч";
        type_rdbtn1.setSelected(false);
        type_rdbtn2.setSelected(true);
    }


    public void msgSet(String msg,int type){
        if(type==1){
            fnc.successMsg(msgLbl,msg);
        }
        if(type==2){
            fnc.errMsg(msgLbl,msg);
        }
    }

    @FXML
    void signUpClick(MouseEvent event){

        //database signUp check code..
        if(passTxt.getText().equals(confirmTxt.getText())){

            //Хэрэглэгчийн нэрний эхний үсэг том байх
            String name = nameTxt.getText();
            name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();

            int msg = Database.signUpDB(
                    s_userType,
                    name,
                    passTxt.getText(),
                    Integer.parseInt(phoneTxt.getText()),
                    mailTxt.getText()
            );

            /* 1. Амжилттай, 2. phone эсвэл email давхцсан 3. Алдаа гарлаа*/
            switch (msg){
                case 1:
                    fnc.errMsg(msgLbl,"Админий эрхээр үүсгэх боломжгүй");
                    break;
                case 2:
                    fnc.successMsg(msgLbl,"Амжилттай");
                    fnc.clear(nameTxt, passTxt, phoneTxt, mailTxt, confirmTxt);
                    break;
                case 3:
                    fnc.errMsg(msgLbl,"Утас эсвэл мейл хаяг бүртгэгдсэн");
                    break;
                default:
                    fnc.errMsg(msgLbl,"Системд алдаа гарлаа?? ");
                    break;
            }
        }
        else{
            fnc.errMsg(msgLbl,"Нууц үг Таарахгүй байна");
        }
    }

    @FXML
    void signInClick(MouseEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("xml/SignIn.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("css/style.css").toExternalForm());

            //Function::windowMaxmized
            fnc.windowMaximized(stage);

            //Logo
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("img/logo.png")));;
            stage.setTitle("Finance - Системд нэвтрэх");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
