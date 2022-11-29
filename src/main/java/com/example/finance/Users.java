package com.example.finance;

import com.example.finance.conf.Database;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
public class Users {
    private Stage stage;
    private Scene scene;

    @FXML
    private Parent root;
    @FXML
    private Button exitBtn;

    @FXML
    private ComboBox<?> filterTypeCmbx;

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
    private HBox noteBtn;

    @FXML
    private ImageView save_btn;

    @FXML
    private ComboBox<String> name_cmbx;

    @FXML
    private Button settngsBtn;

    @FXML
    private GridPane data_grdpn;

    @FXML
    void exitClick(MouseEvent event) {
    }

    @FXML
    void filterTypeClick(MouseEvent event) {

    }

    String s_id, s_name;
    @FXML
    void noteClick(MouseEvent event) {
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        String[] s_arr = this.stage.getTitle().replace(")","").split(" ");
        s_id = s_arr[0];
        // Өгөгдийг унших
        Database.getData(Integer.parseInt(s_id));
        s_name = s_arr[6];
        name_cmbx.setPromptText(s_name);
    }
    @FXML
    void settngsClick(MouseEvent event) {

    }

    @FXML
    void save_click(MouseEvent event) {
        String[] row = new String[11];
        row[0] = String.valueOf(dpckr.getValue());
        row[1] = String.valueOf(const_cbx.isSelected() ? '1' : '0');
        row[2] = constumer_txt.getText();
        row[3] = moneyplus_txt.getText();
        row[4] = moneysub_txt.getText();
        row[5] = debtplus_txt.getText();
        row[6] = debtsub_txt.getText();
        row[7] = receivableplus_txt.getText();
        row[8] = receivablesub_txt.getText();
        row[9] = income_txt.getText();
        row[10] = expense_txt.getText();

        Database.saveData(row, Integer.parseInt(s_id));
    }
}

