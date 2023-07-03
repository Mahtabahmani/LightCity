package org.example.defualtSystem;
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

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import org.example.Database;
import org.example.models.Business;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StockMarket {
    private Map<String, Double> users;
    private Database database;
    private ArrayList<Business> businesses;

    public StockMarket() {
        users = new HashMap<>();
        database = new Database();
        businesses = new ArrayList<>();

    }


    public synchronized void depositCapital(String userName, double amount) {
        if (users.containsKey(userName)) {
            double currentCapital = users.get(userName);
            users.put(userName, currentCapital + amount);
        } else {
            users.put(userName, amount);
        }
    }

    public synchronized double getDetail(String userName) {
        if (users.containsKey(userName)) {
            return users.get(userName);
        } else {
            return 0.0;
        }
    }

    public synchronized double withdrawCapital(String userName) {
        if (users.containsKey(userName)) {
            double amount = users.get(userName);
            users.put(userName, 0.0);
            return amount;
        } else {
            return 0.0;
        }
    }

    public void startMarketSimulation() {
        Thread marketThread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                double change = random.nextDouble() * 0.06 - 0.03;
                for (String userName : users.keySet()) {
                    double capital = users.get(userName);
                    double newCapital = capital + capital * change;
                    if (newCapital < 0) {
                        newCapital = 0;
                    }
                    users.put(userName, newCapital);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        marketThread.start();
    }

}