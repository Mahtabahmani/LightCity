package org.example.models;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

public class BarChartEx extends JFrame {
    private ArrayList<Business> businesses;
    public BarChartEx(ArrayList<Business> businesses) {
        this.businesses = new ArrayList<>();
        this.businesses = businesses;
        initUI();
    }

    private void initUI() {

        CategoryDataset dataset = createDataset();

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset createDataset() {

        var dataset = new DefaultCategoryDataset();
        for(int i=0;i<businesses.size();i++){
            dataset.setValue(businesses.get(i).getIncome(), "Income", businesses.get(i).getName());
        }
//        dataset.setValue(46, "Income", "Market");
//        dataset.setValue(38, "Income", "Amazone");
//        dataset.setValue(22, "Income", "Audi");
//        dataset.setValue(13, "Income", "Apple");
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart barChart = ChartFactory.createBarChart(
                "Businesses Income In 2023",
                "Businesses",
                "Income ($)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }

    public void show() {

        EventQueue.invokeLater(() -> {

            var ex = new BarChartEx(businesses);
            ex.setVisible(true);
        });
    }
}
