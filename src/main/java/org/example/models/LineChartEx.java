package org.example.models;

import org.example.Database;
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
import java.util.HashMap;
import java.util.Map;

public class LineChartEx extends JFrame {
    private Business business;
    public LineChartEx(Business business) {
        this.business = business;
        initUI();
    }

    private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset() {

        var series = new XYSeries("2023");
        Map<String,Float> chartData = new HashMap<String,Float>();
        Database database = new Database();
        chartData = database.readStockData(business.getStocks().get(0));
        double y = chartData.get("2023-07-01");
        series.add(1, y);
        y = chartData.get("2023-07-02");
        series.add(2, y);
        y = chartData.get("2023-07-03");
        series.add(3, y);
        y = chartData.get("2023-07-04");
        series.add(4, y);
        y = chartData.get("2023-07-05");
        series.add(5, y);
        y = chartData.get("2023-07-06");
        series.add(6, y);
        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Stock price in the past few days",
                "Date",
                "Price ($)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Stock price in the past few days",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }


    public void show(){
        EventQueue.invokeLater(() -> {

            var ex = new LineChartEx(business);
            ex.setVisible(true);
        });
    }


}