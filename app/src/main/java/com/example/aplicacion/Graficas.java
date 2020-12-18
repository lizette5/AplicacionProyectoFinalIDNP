package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class Graficas extends AppCompatActivity {
    private BarChart bar;
    private PieChart pie;
    private String[] a = new String[]{"uno", "dos", "tres"};
    private int[] num = new int[]{25, 20, 38, 10, 15};
    private int[] colors = new int[]{Color.CYAN, Color.GREEN, Color.RED, Color.BLUE, Color.MAGENTA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);
        bar = (BarChart) findViewById(R.id.barras);
        pie = (PieChart) findViewById(R.id.pastel);
        createCharts();
    }

    private Chart getSameChart(Chart chart, String desc, int textc, int background, int anim) {
        chart.getDescription().setText(desc);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(anim);
        legend(chart);
        return chart;
    }

    private void legend(Chart chart) {
        Legend leyenda = chart.getLegend();
        leyenda.setForm(Legend.LegendForm.CIRCLE);
        leyenda.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colors[i];
            entry.label = a[i];
            entries.add(entry);
        }
        leyenda.setCustom(entries);
    }

    private ArrayList<BarEntry> getBarEntries() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < num.length; i++) {
            entries.add(new BarEntry(i, num[i]));
        }
        return entries;
    }

    private ArrayList<PieEntry> getPieEntries() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < num.length; i++) {
            entries.add(new PieEntry(num[i]));
        }
        return entries;
    }

    private void axisX(XAxis axis) {
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(a));
    }

    private void axisLeft(YAxis axis) {
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }

    private void axisRight(YAxis axis) {
        axis.setEnabled(false);
    }

    public void createCharts() {
        bar = (BarChart) getSameChart(bar, "Series", Color.RED, Color.CYAN, 3000);
        bar.setDrawGridBackground(true);
        bar.setDrawBarShadow(true);
        bar.setData(getBarData());
        bar.invalidate();
        axisX(bar.getXAxis());
        axisLeft(bar.getAxisLeft());
        axisRight(bar.getAxisRight());

        pie = (PieChart) getSameChart(pie, "Ventas", Color.GRAY, Color.MAGENTA, 3000);
        pie.setHoleRadius(10);
        pie.setTransparentCircleRadius(12);
        pie.setData(getPieData());
        pie.invalidate();
        //pie.setDrawHoleEnabled(false);
    }

    private DataSet getData(DataSet dataSet) {
        dataSet.setColors(colors);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private BarData getBarData() {
        BarDataSet barDataSet = (BarDataSet) getData(new BarDataSet(getBarEntries(), ""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }

    private PieData getPieData() {
        PieDataSet pieDataSet = (PieDataSet) getData(new PieDataSet(getPieEntries(), ""));
        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);
    }
}