package com.example.campusassistant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class subjectWiseAttendance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_wise_attendance);

        BarChart barChart = findViewById(R.id.barChart);

        // Sample data, replace it with your actual data
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 5f)); // Subject 1
        entries.add(new BarEntry(2, 4f)); // Subject 2
        entries.add(new BarEntry(3, 3f)); // Subject 3
        entries.add(new BarEntry(4, 2f)); // Subject 4
        entries.add(new BarEntry(5, 1f)); // Subject 5
        entries.add(new BarEntry(6, 2.5f)); // Lab 1
        entries.add(new BarEntry(7, 3.5f)); // Lab 2
        entries.add(new BarEntry(8, 4.5f)); // Lab 3

        BarDataSet barDataSet = new BarDataSet(entries, "Subjects and Labs");

        // Assign different colors to each bar
        barDataSet.setColors(getColors());

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        // Additional settings for the chart (optional)
        barChart.getDescription().setEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDrawGridBackground(false);

        // Customize X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new XAxisValueFormatter());

        // Customize Y-axis
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setGranularity(1f);
        yAxis.setAxisMinimum(0f);

        // To hide right Y-axis
        barChart.getAxisRight().setEnabled(false);

        // Add animation
        barChart.animateY(1000); // 1000 milliseconds (1 second) animation

        // Refresh the chart
        barChart.invalidate();
    }

    private class XAxisValueFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            // Customize X-axis labels here
            if (value == 1) return "Subject 1";
            else if (value == 2) return "Subject 2";
            else if (value == 3) return "Subject 3";
            else if (value == 4) return "Subject 4";
            else if (value == 5) return "Subject 5";
            else if (value == 6) return "Lab 1";
            else if (value == 7) return "Lab 2";
            else if (value == 8) return "Lab 3";
            else return "";
        }
    }

    // Define different colors for each bar
    private List<Integer> getColors() {
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(77, 172, 38)); // Green
        colors.add(Color.rgb(255, 0, 0));   // Red
        colors.add(Color.rgb(255, 165, 0)); // Orange
        colors.add(Color.rgb(30, 144, 255)); // Dodger Blue
        colors.add(Color.rgb(128, 0, 128)); // Purple
        colors.add(Color.rgb(255, 255, 0)); // Yellow
        colors.add(Color.rgb(0, 128, 0));   // Dark Green
        colors.add(Color.rgb(255, 69, 0));  // Red-Orange
        return colors;
    }
}
