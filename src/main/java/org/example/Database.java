package org.example;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.example.models.User;
import javax.xml.crypto.Data;
import java.sql.*;


public class Database {
    static Cipher cipher;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/lightcity";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "mahtamysql";


    private Connection conn;

    public Database() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connecting to database...");
        } catch (Exception exp) {
            System.out.println("Database Exception : \n" + exp.toString());
            System.exit(0);
        }
    }
    //    Tables

    /**
     * Users
     */

    private void createTables() {
//        query example
        String query = "CREATE TABLE IF NOT EXISTS Users (username varchar(255) primary key ,password varchar(255));" +
                "CREATE TABLE IF NOT EXISTS ....";
        try {
            Statement stmt = conn.createStatement();
           if(stmt.execute(query)){
               stmt.executeUpdate("insert into Users(username, password) values(100, 'A')");
           }else
               System.out.println("An error accord during operation");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void loginGame(User user) {
        try {
            Statement stmt = conn.createStatement();
            String query = "select * from User";
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                String use = res.getString(1);
                String pass = res.getString(2);
                User user1 = new User(use,pass);
                if(user.equals(user1)){

                }
            }
        } catch (Exception exception) {
        }

    }
    public static String encrypt(String plainText, SecretKey secretKey)
            throws Exception {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public static String decrypt(String encryptedText, SecretKey secretKey)
            throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

    public void registerGame(User user) {
    }

}
