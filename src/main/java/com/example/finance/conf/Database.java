package com.example.finance.conf;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    //url-> postgresq өгөгдлийн санг холбох зам
    static String url = "jdbc:postgresql://localhost:5432/Finance";

    //Ашиглаж буй Postgresql-н нэр
    static String db = "postgres";

    //Ашиглаж буй Postgresql-н нууц үг
    static String postgrePas = "12345678";

    /*
     * SignUpDB функц нь хэрэглэгчийг бүртгэн
     * String userType, => Тухайн системд бүртгүүлэхдээ Админ эсвэл хэрэглэгч гэсэн 2 төрлөөр сонгоно
     * String name, => Хэрэглэгчийн нэр
     * String password, => Хэрэглэгчийн нууц үг
     * int phone, => Хэрэглэгчийн Утас
     * String email => Хэрэглэгчийн и-мейл хаяг
     * */
    public static int signUpDB(String s_userType, String s_name, String s_password, int i_phone, String s_email) {
        String s_query = "insert into users(usertype, name, phone, email, password) values(?, ?, ?, ?, ?)";
        String s_mail = "select count(email) from users where email = ?";
        String s_phone = "select count(phone) from users where phone = ?";
        String s_type = "select count(usertype) from users where usertype = ?";

        try (Connection con = DriverManager.getConnection(url, db, postgrePas)
        ) {
            PreparedStatement prepStmt;
            // Нэг грүппийг нэг л админ удирдах ёстой
            prepStmt = con.prepareStatement(s_type);
            prepStmt.setString(1, s_userType);
            ResultSet typeCheck = prepStmt.executeQuery();
            typeCheck.next();
            int count1 = typeCheck.getInt(1);

            //email шалгаж байна
            prepStmt = con.prepareStatement(s_mail);
            prepStmt.setString(1, s_email);
            ResultSet emailCheck = prepStmt.executeQuery();
            emailCheck.next();
            int count2 = emailCheck.getInt(1);

            // Утасны дугаар шалгаж байна
            prepStmt = con.prepareStatement(s_phone);
            prepStmt.setInt(1, i_phone);
            emailCheck = prepStmt.executeQuery();
            emailCheck.next();
            int count3 = emailCheck.getInt(1);
            if (!s_userType.equals("Админ") || count1 < 1) {
                if (count2 < 1 && count3 < 1) {
                    prepStmt = con.prepareStatement(s_query);
                    prepStmt.setString(1, s_userType);
                    prepStmt.setString(2, s_name);
                    prepStmt.setInt(3, i_phone);
                    prepStmt.setString(4, s_email);
                    prepStmt.setString(5, s_password);
                    prepStmt.executeUpdate();

                    //db-г хаах хэрэгтэй
                    con.close();
                    return 2;
                } else {

                    //db-г хаах хэрэгтэй
                    con.close();
                    return 3;
                }
            } else {
                return 1;
            }
        } catch (SQLException ex) {
            // Log-руу бичиж буй байдал
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return 4;
        }
    }

    /*
     * SignInDB функц нь хэрэглэгчийг шалгах
     * String userType, => Тухайн системд бүртгүүлэхдээ Админ эсвэл хэрэглэгч гэсэн 2 төрлөөр сонгоно
     * String name, => Хэрэглэгчийн нэр
     * String password, => Хэрэглэгчийн нууц үг
     * */
    public static int signInDB(String userType, String name, String password) {
        String query = "select usertype, name, phone, email, password, userid from users where usertype = ? and name = ? and password = ?";
        String check = "select count(*) from users where usertype = ? and name = ? and password = ?";

        try (Connection con = DriverManager.getConnection(url, db, postgrePas)
        ) {
            PreparedStatement prepStmt;
            // Нэг грүппийг нэг л админ удирдах ёстой
            prepStmt = con.prepareStatement(check);
            prepStmt.setString(1, userType);
            prepStmt.setString(2, name);
            prepStmt.setString(3, password);
            ResultSet typeCheck = prepStmt.executeQuery();
            typeCheck.next();
            int count1 = typeCheck.getInt(1);

            //db-г хаах хэрэгтэй
            con.close();
            if (count1 == 1) {
                return 1;
            } else {
                return 2;
            }
        } catch (SQLException ex) {
            // Log-руу бичиж буй байдал
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return 3;
        }
    }

    /*
     * returnUserId функц нь хэрэглэгч амжилттай нэвтэрхэд өгөгдөлөө шүүх зорилгоор ID - г буцаадаг байна
     * String userType, => Тухайн системд бүртгүүлэхдээ Админ эсвэл хэрэглэгч гэсэн 2 төрлөөр сонгоно
     * String name, => Хэрэглэгчийн нэр
     * String password, => Хэрэглэгчийн нууц үг
     * */
    public static int returnUserId(String userType, String name, String password) {
        String check = "select userid from users where usertype = ? and name = ? and password = ?";

        try (Connection con = DriverManager.getConnection(url, db, postgrePas)
        ) {
            PreparedStatement prepStmt;
            // Нэг грүппийг нэг л админ удирдах ёстой
            prepStmt = con.prepareStatement(check);
            prepStmt.setString(1, userType);
            prepStmt.setString(2, name);
            prepStmt.setString(3, password);
            ResultSet typeCheck = prepStmt.executeQuery();
            typeCheck.next();
            int count1 = typeCheck.getInt(1);
            return count1;
        } catch (SQLException ex) {
            // Log-руу бичиж буй байдал
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return 0;
        }
    }

    public static void getData(int userid) {
        String s_query = "select " +
                "ognoo, " +
                "togtmol, " +
                "hariltsagch, " +
                "mungu_usuh, " +
                "mungu_buurah, " +
                "ur_usuh, " +
                "ur_buurah, " +
                "avlaga_usuh, " +
                "avlaga_buurah," +
                "orlogo," +
                "zardal," +
                "userid " +
                "from usersdata where userid = ? order by id asc";
        try (Connection con = DriverManager.getConnection(url, db, postgrePas)
        ) {
            PreparedStatement prepStmt;
            // Нэг грүппийг нэг л админ удирдах ёстой
            prepStmt = con.prepareStatement(s_query);
            prepStmt.setInt(1, userid);

            ResultSet typeCheck = prepStmt.executeQuery();
            while (typeCheck.next()) {
                //Мэдээллийг хэвэлж байна
                System.out.println(typeCheck.getDate(1) + " | " +
                        typeCheck.getByte(2) + " | " +
                        typeCheck.getString(3) + " | " +
                        typeCheck.getInt(4) + " | " +
                        typeCheck.getInt(5) + " | " +
                        typeCheck.getInt(6) + " | " +
                        typeCheck.getInt(7) + " | " +
                        typeCheck.getInt(8) + " | " +
                        typeCheck.getInt(9) + " | " +
                        typeCheck.getInt(10) + " | " +
                        typeCheck.getInt(11));

                //өгөгдлийг нэг контролд хадгалах хэрэгтэй
            }
        } catch (SQLException ex) {
            // Log-руу бичиж буй байдал
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void userViewData(String userId) {
        String s_id = "select userid from users where userid = ? and name = ?";
        String s_data = "select * from users where userid = ?";
    }
    public static int saveData(String[] row,int userid) {
        String createQuery = "insert into usersdata(" +
                "ognoo, " +
                "togtmol, " +
                "hariltsagch, " +
                "mungu_usuh, " +
                "mungu_buurah, " +
                "ur_usuh, " +
                "ur_buurah, " +
                "avlaga_usuh, " +
                "avlaga_buurah," +
                "orlogo," +
                "zardal," +
                "userid ) " +
                "values(? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?)";

        try (Connection con = DriverManager.getConnection(url, db, postgrePas)
        ) {
            PreparedStatement prepStmt;
            // Нэг грүппийг нэг л админ удирдах ёстой
            prepStmt = con.prepareStatement(createQuery);
            prepStmt.setDate(1, Date.valueOf(row[0]));
            prepStmt.setString(2, row[1]);
            prepStmt.setString(3, row[2]);
            prepStmt.setInt(4, Integer.parseInt(row[3]));
            prepStmt.setInt(5, Integer.parseInt(row[4]));
            prepStmt.setInt(6, Integer.parseInt(row[5]));
            prepStmt.setInt(7, Integer.parseInt(row[6]));
            prepStmt.setInt(8, Integer.parseInt(row[7]));
            prepStmt.setInt(9, Integer.parseInt(row[8]));
            prepStmt.setInt(10, Integer.parseInt(row[9]));
            prepStmt.setInt(11, Integer.parseInt(row[10]));
            prepStmt.setInt(12, userid);
            prepStmt.executeUpdate();

            //db-г хаах хэрэгтэй
            con.close();
            Database.getData(userid);
            return 1;
        } catch (SQLException ex) {
            // Log-руу бичиж буй байдал
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return 2;
        }
    }
    public static void updateData() {

    }

    public static void deleteData() {

    }

    public static void filterType() {

    }
}
