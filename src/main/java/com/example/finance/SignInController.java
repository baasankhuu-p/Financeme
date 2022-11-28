package com.example.finance;

import com.example.finance.conf.Database;
import com.example.finance.conf.Functions;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.stage.*;

import java.io.*;
import java.util.logging.*;

public class SignInController {
    Functions fnc = new Functions();
    private Stage stage;
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    RadioButton type_rdbtn1,type_rdbtn2;
    String s_userType = "Админ";

    @FXML
    Label msgLbl;

    @FXML
    private PasswordField passTxt;

    @FXML
    private Label frgtLbl;

    @FXML
    private TextField nameTxt;

    @FXML
    private Button signInBtn, signUpBtn;
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

    /**
     * Хуудас солиход алдааг барьж авах
     * @param event
     * @throws IOException
     */
    @FXML
    void signInClick(MouseEvent event) throws InterruptedException {

        // Хэрэглэгч формыг бөглөх ёстой
        if(!passTxt.getText().trim().equals("") && !nameTxt.getText().equals("")){

            //Хэрэглэгч амжилттай нэвтэрсэн эсэх
            int i_msgType = Database.signInDB(
                    s_userType,
                    nameTxt.getText(),
                    passTxt.getText()
            );

            // 1. Хэрэглэгч амжилттай нэвтэрлээ, 2. Мэдээлэл буруу байна 3. Алдаа гарлаа
            switch (i_msgType){
                case 1:
                    //Хэрэглэгч амжилттай нэвтэрсэн бол хэрэглэгчийн дугаарыг буцаадаг байна
                    int i_uid = Database.returnUserId(
                            s_userType,
                            nameTxt.getText(),
                            passTxt.getText()
                    );
                    //msg хүмүүсд харуулах
                    fnc.successMsg(msgLbl,"Амжилттай");

                    //Хэрэглэгч зөв нэвтрэхэд хэрэглэгчийн Мэдээлэл хэрэгтэй
                    //Хэрэглэгчийн нэрний эхний үсэг том байх
                    String name = nameTxt.getText();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();

                    try {
                        root = FXMLLoader.load(getClass().getResource("xml/User.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        scene.getStylesheets().add(Main.class.getResource("css/style.css").toExternalForm());
                        // Function::windowMaxmized
                        fnc.windowMaximized(stage);

                        // Logo
                        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("img/logo.png")));
                        // Нэрний эхнийх нь том байх нь зохимжтой
                        stage.setTitle("" +i_uid+") "+" Finance - Сайн уу "+name);

                        stage.setScene(scene);
                        stage.show();

                        // Хэрэглэгчийн нэвтрэх мэдээллийг цэвэрлэж байна ..
                        fnc.clear(nameTxt, passTxt);

                    } catch (IOException e) {
                        // Log-руу бичиж буй байдал
                        Logger lgr = Logger.getLogger(SignInController.class.getName());
                        lgr.log(Level.SEVERE, e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    fnc.errMsg(msgLbl,"Мэдээлэл буруу байна");
                    break;
                default:
                    fnc.errMsg(msgLbl,"Системд алдаа гарлаа?? ");
                    break;
            }
        }
        else{
            fnc.errMsg(msgLbl,"Мэдээллээ гүйцэт оруулна уу");
        }
    }

    @FXML
    void signUpClick(MouseEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("xml/SignUp.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("css/style.css").toExternalForm());
            /*Function::windowMaxmized*/
            fnc.windowMaximized(stage);

            /*Logo*/
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("img/logo.png")));;
            stage.setTitle("Finance - Системд бүртгүүлэх");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
