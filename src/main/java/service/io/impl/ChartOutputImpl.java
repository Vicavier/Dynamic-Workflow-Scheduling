package service.io.impl;

import entity.Chromosome;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import service.io.Output;

import javax.swing.*;
import java.util.List;

public class ChartOutputImpl implements Output {
    @Override
    public void output(List<List<Chromosome>> list) {
        XYSeriesCollection collection=new XYSeriesCollection();
        int i=0;
        for(List<Chromosome> list1:list){
            i++;
            XYSeries series=new XYSeries("rank "+i);
            for (Chromosome chromosome:list1){
                series.add(chromosome.getMakeSpan(),chromosome.getCost());
            }
            collection.addSeries(series);
        }

        JFreeChart freeChart = ChartFactory.createScatterPlot(
                "Pareto Set Diagram",// 图表标题
                "makespan",
                "cost",
                collection,//数据集，即要显示在图表上的数据
                PlotOrientation.VERTICAL,//设置方向
                true,//是否显示图例
                true,//是否显示提示
                false//是否生成URL连接
        );
        NumberAxis axis= (NumberAxis) freeChart.getXYPlot().getDomainAxis();
        axis.setAutoTickUnitSelection(true);

//        axis.setTickUnit(new NumberTickUnit(1));

        ChartPanel chartPanel = new ChartPanel(freeChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 400));
        //创建一个主窗口来显示面板
        JFrame frame = new JFrame("帕累托散点图");
        frame.setLocation(500, 400);
        frame.setSize(600, 500);

        //将主窗口的内容面板设置为图表面板
        frame.setContentPane(chartPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
