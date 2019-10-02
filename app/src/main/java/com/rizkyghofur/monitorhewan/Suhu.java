package com.rizkyghofur.monitorhewan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class Suhu extends AppCompatActivity {

        LineChartView lineChartView;
        String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
                "Oct", "Nov", "Dec"};
        int[] yAxisData = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_suhu);

            lineChartView = findViewById(R.id.chart);

            List yAxisValues = new ArrayList();
            List axisValues = new ArrayList();


            Line line = new Line(yAxisValues).setColor(Color.parseColor("#FFFFFF"));

            for (int i = 0; i < axisData.length; i++) {
                axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
            }

            for (int i = 0; i < yAxisData.length; i++) {
                yAxisValues.add(new PointValue(i, yAxisData[i]));
            }

            //Mengambil data dari intent
            Intent mIntent = getIntent();
            int suhuBaru = mIntent.getIntExtra("nilai_suhu", 0);
            String labelBaru = mIntent.getStringExtra("nilai_x");

            int index_terakhir = axisData.length-1;

            axisValues.add(index_terakhir, new AxisValue(index_terakhir).setLabel(labelBaru));
            yAxisValues.add(new PointValue(index_terakhir, suhuBaru));



            List lines = new ArrayList();
            lines.add(line);

            LineChartData data = new LineChartData();
            data.setLines(lines);

            Axis axis = new Axis();
            axis.setValues(axisValues);
            axis.setTextSize(16);
            axis.setTextColor(Color.parseColor("#FFFFFF"));
            data.setAxisXBottom(axis);

            Axis yAxis = new Axis();
            yAxis.setName("Contoh Grafik");
            yAxis.setTextColor(Color.parseColor("#FFFFFF"));
            yAxis.setTextSize(16);
            data.setAxisYLeft(yAxis);

            lineChartView.setLineChartData(data);
            Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
            viewport.top = 110;
            lineChartView.setMaximumViewport(viewport);
            lineChartView.setCurrentViewport(viewport);
        }

    }
