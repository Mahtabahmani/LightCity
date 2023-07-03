package org.example;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.crypto.*;

import org.example.defualtSystem.StockMarket;
import org.example.models.Stock;
import org.example.models.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Date;


public class Database {
    static Cipher cipher;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/lightcity";

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

    public void createTables(User user) {
//        query example
//        String query = "CREATE TABLE IF NOT EXISTS Users (username varchar(255) primary key ,password varchar(255));" +
//                "CREATE TABLE IF NOT EXISTS ....";
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            cipher = Cipher.getInstance("AES");
            SecretKey secretKey = getKeyFromPassword("fullyoubilltch","2514");
            String encryptedPass = encrypt(user.getPassword(),secretKey);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO user(username,password)"+" values('"+user.getUsername()+"','"+encryptedPass+"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void storeStockData(Stock stock){//  record stock's price to see profit and loss in a period of time
        Thread thread = new Thread(()->{
            float previousP = stock.getValue();
            int count = 0;
            while (true){
                try {
                    Statement stmt = conn.createStatement();
                    LocalDateTime ldt = LocalDateTime.now();
                    String date = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);
                    if(previousP!=stock.getValue() || count==0){
                        stmt.executeUpdate("INSERT INTO stocks(name,price,date)"+" values('"+stock.getTitle()+"','"+stock.getValue()+"','"+date+"')");
                        previousP = stock.getValue();
                        count=1;
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(10800000); // wait for 1 minute
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    public Map readStockData(Stock stock){
        HashMap<String, Float> chartDate = new HashMap<String,Float>();
        try {
            Statement stmt = conn.createStatement();
            String query = "select * from stocks";
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                String name = res.getString(1);
                if(name.equals(stock.getTitle())){
                    float price = res.getFloat(2);
                    String date = res.getString(3);
                    chartDate.put(date,price);
                }
            }

        } catch (Exception exception) {
        }
        return chartDate;
    }
    public boolean checkUsername(String username){
        try {
            Statement stmt = conn.createStatement();
            String query = "select * from user";
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                String use = res.getString(1);
                if(use.equals(username)){
                    return true;
                }
            }
        } catch (Exception exception) {
        }
       return false;
    }

    public boolean loginGame(User user) {
        try {
            Statement stmt = conn.createStatement();
            String query = "select * from user";
            ResultSet res = stmt.executeQuery(query);
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            cipher = Cipher.getInstance("AES");
            SecretKey secretKey = getKeyFromPassword("fullyoubilltch","2514");
            while (res.next()) {
                String use = res.getString(1);
                String pass = res.getString(2);
                String plainPass = decrypt(pass,secretKey);
                User user1 = new User(use,plainPass);
                if(user.getUsername().equals(user1.getUsername()) && user.getPassword().equals(user1.getPassword())){
                    return true;
                }
            }

        } catch (Exception exception) {
        }
        return false;
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
    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
        return secret;
    }

    public void registerGame(User user) {
    }

}
