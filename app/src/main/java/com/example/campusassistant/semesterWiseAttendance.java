package com.example.campusassistant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class semesterWiseAttendance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_wise_attendance);

        PieChart pieChart = findViewById(R.id.pieChart);

        // Sample data, you should replace it with your actual data
        int presentCount = 75;
        int absentCount = 25;

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(presentCount, "Present"));
        entries.add(new PieEntry(absentCount, "Absent"));

        PieDataSet dataSet = new PieDataSet(entries, "Attendance");

        // Assign colors to slices
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);  // Green for Present
        colors.add(Color.RED);    // Red for Absent
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        // Additional settings for the chart
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Attendance");  // Set center text
        pieChart.setCenterTextSize(18f);
        pieChart.setCenterTextColor(Color.BLACK);

        // Legend settings
        Legend legend = pieChart.getLegend();
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        // Enable touch gestures
        pieChart.setTouchEnabled(true);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setEntryLabelTextSize(14f);

        // Animation settings
        pieChart.animateY(1000);  // Animation duration in milliseconds
        pieChart.invalidate();  // Refresh the chart
    }
}
