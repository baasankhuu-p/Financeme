package com.example.finance;

import com.example.finance.conf.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Users {
    FAlert alert = new FAlert();
    Functions fnc = new Functions();
    private Stage stage;

    @FXML
    private Parent root;
    @FXML
    private Button exitBtn;

    @FXML
    private ComboBox<String> filterTypeCmbx;

    @FXML
    private ComboBox<String> filterOnecmbx;

    @FXML
    private DatePicker dpckr;

    @FXML
    private CheckBox const_cbx;

    @FXML
    private TextField constumer_txt,
            debtplus_txt,
            debtsub_txt,
            expense_txt,
            income_txt,
            moneyplus_txt,
            moneysub_txt,
            receivableplus_txt,
            receivablesub_txt;
    @FXML
    Label msgLbl;

    @FXML
    private HBox noteBtn,
    inputHbx;

    @FXML
    private ImageView save_btn;

    @FXML
    private ComboBox<String> name_cmbx;

    @FXML
    private Button settngsBtn;

    @FXML
    private GridPane data_grdpn;

    String s_name;
    int s_id = 0;

    @FXML
    public void initialize() {

        /*filter type*/
        filterTypeCmbx.getItems().addAll(
                "Жил",
                "Сар",
                "Өдөр"
        );
        for (int i = 2024; i > 1990; i--) {
            filterOnecmbx.getItems().add(String.valueOf(i));
        }

        if(s_id==0){
            inputHbx.setDisable(true);
            fnc.errMsg(msgLbl,"Та тэмдэглэл товчийг дарах хэрэгтэй");
        }
    }

    @FXML
    void exitClick(MouseEvent event) {

    }

    @FXML
    void filterTypeAction(ActionEvent event) {

        switch (filterTypeCmbx.getSelectionModel().getSelectedItem()){
            case "Жил":
                filterOnecmbx.setPromptText(String.valueOf(2022));
                filterOnecmbx.getItems().clear();
                for (int i = 2022; i > 1990; i--) {
                    filterOnecmbx.getItems().add(String.valueOf(i));
                }
                break;
            case "Сар":
                filterOnecmbx.setPromptText(String.valueOf(12));
                filterOnecmbx.getItems().clear();
                for (int i = 12; i >= 1; i--) {
                    filterOnecmbx.getItems().add(String.valueOf(i));
                }
                break;
            case "Өдөр":
                filterOnecmbx.setPromptText(String.valueOf(30));
                filterOnecmbx.getItems().clear();
                for (int i = 30; i >= 1; i--) {
                    filterOnecmbx.getItems().add(String.valueOf(i));
                }
                break;
        }
    }
    @FXML
    void noteClick(MouseEvent event) {

        if(s_id>0){
            inputHbx.setDisable(false);
            fnc.clearmsg(msgLbl);
        }

        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        String[] s_arr = this.stage.getTitle().replace(")","").split(" ");
        s_id = Integer.parseInt(s_arr[0]);
        // Өгөгдийг унших
        Database.getData(s_id);
        s_name = s_arr[6];
        name_cmbx.setPromptText(s_name);
    }
    @FXML
    void settngsClick(MouseEvent event) {

    }

    @FXML
    void save_click(MouseEvent event) {
        String[] row = new String[11];
        int activeCnt = 0,passiveCnt = 0;
        if(!String.valueOf(dpckr.getValue()).equals("null") && !constumer_txt.getText().equals("")){
            fnc.clearmsg(msgLbl);
            row[0] = String.valueOf(dpckr.getValue());
            row[1] = String.valueOf(const_cbx.isSelected() ? '1' : '0');
            row[2] = constumer_txt.getText();
            row[3] = fnc.issetTxtFnc(moneyplus_txt).getText();
            row[4] = fnc.issetTxtFnc(moneysub_txt).getText();
            row[5] = fnc.issetTxtFnc(debtplus_txt).getText();
            row[6] = fnc.issetTxtFnc(debtsub_txt).getText();
            row[7] = fnc.issetTxtFnc(receivableplus_txt).getText();
            row[8] = fnc.issetTxtFnc(receivablesub_txt).getText();
            row[9] = fnc.issetTxtFnc(income_txt).getText();
            row[10] = fnc.issetTxtFnc(expense_txt).getText();
            for (int i = 3; i < 7; i++) {
                if(Integer.parseInt(row[i])>0){
                    activeCnt++;
                }
            }
            for (int i = 7; i < row.length; i++) {
                if(Integer.parseInt(row[i])>0){
                    passiveCnt++;
                }
            }
            if(activeCnt==1 && passiveCnt == 1){
                int msg = Database.saveData(row, s_id);
                switch (msg){
                    case 1:
                        fnc.successMsg(msgLbl,"Нэмэгдлээ");
                        break;
                    case 2:
                        fnc.errMsg(msgLbl,"Алдаа гарлаа");
                        break;
                }
            }
            else{
                fnc.errMsg(msgLbl,"Та мэдээллээ үнэн зөв оруулна уу Ж/нь: Цалин 1,200,000 буух бол Мөнгө өсөх хэсэгт болон орлого хэсэгт бүртгэх ёстой");
            }
        }
        else{
            fnc.errMsg(msgLbl,"Цаг болон харилцагчаа заавал оруулах шаардлагатай !");
        }
    }
}