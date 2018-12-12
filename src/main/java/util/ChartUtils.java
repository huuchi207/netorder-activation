package util;

import dto.ChartData;
import dto.ChartPoint;
import dto.GroupChartPoint;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.AnchorPane;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;

public class ChartUtils {
  public static void makeChart(JFreeChart freeChart, ChartViewer chartViewer, AnchorPane boxGraphic){
    chartViewer = new ChartViewer(freeChart);
    addChart(boxGraphic, chartViewer);
  }

  public static void configScrollBar(SlidingCategoryDataset dataset, ScrollBar scrollBar, int lengthColumn){
    if (dataset.getColumnCount() < 10) {
      scrollBar.setVisible(false);
    } else {
      scrollBar.setVisible(true);
      scrollBar.setMin(0);
      scrollBar.setValue(0);
      scrollBar.setMax((double) lengthColumn / 10);
      scrollBar.setVisibleAmount(1);
    }
  }
  public static void addChart(AnchorPane box, Node chart) {
    box.getChildren().clear();
    ResizeUtils.margin(chart, 0);
    box.getChildren().add(chart);
  }

  public static DefaultCategoryDataset convertChartDataToCategoryDataset(ChartData chartData) {
    DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
    if (chartData == null || chartData.getGroupChartPoints() == null)
      return categoryDataset;
    for (GroupChartPoint groupChartPoint : chartData.getGroupChartPoints()) {
      if (groupChartPoint.getChartPoints() == null)
        continue;
      for (ChartPoint chartPoint : groupChartPoint.getChartPoints()) {
        categoryDataset.addValue(chartPoint.getValue(), groupChartPoint.getTitle(), chartPoint.getDate());
      }
    }
    return categoryDataset;
  }
}
