package javaprog.wisekeep;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Summary extends AppCompatActivity {

    public FileApp app;
    public String curIO;
    public List<Integer> typeStrId;
    public int yearL, monthL, dayL;
    public int yearR, monthR, dayR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app = (FileApp) this.getApplication();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initDate(1);

        TextView dateL = findViewById(R.id.sumDateL);
        dateL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Summary.this);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        app.readTerm(FileApp.OUT);
                        app.readTerm(FileApp.IN);
                        FileApp.mainAct.refreshDateAmount();
                        FileApp.mainAct.refreshRecycler();
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(Summary.this, R.layout.dialog_date, null);
                final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
                dialog.setTitle("设置日期");
                dialog.setView(dialogView);
                dialog.show();
                datePicker.init(FileApp.year, FileApp.month - 1, FileApp.day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        yearL = year;
                        monthL = month + 1;
                        dayL = day;
                        refresh();
                    }
                });
            }
        });
        TextView dateR = findViewById(R.id.sumDateR);
        dateR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Summary.this);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        app.readTerm(FileApp.OUT);
                        app.readTerm(FileApp.IN);
                        FileApp.mainAct.refreshDateAmount();
                        FileApp.mainAct.refreshRecycler();
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(Summary.this, R.layout.dialog_date, null);
                final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
                dialog.setTitle("设置日期");
                dialog.setView(dialogView);
                dialog.show();
                datePicker.init(FileApp.year, FileApp.month - 1, FileApp.day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        yearR = year;
                        monthR = month + 1;
                        dayR = day;
                        refresh();
                    }
                });
            }
        });

        Button btnMonth = findViewById(R.id.buttonMonth);
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDate(1);
                refresh();
            }
        });
        Button btnHalf = findViewById(R.id.buttonHalf);
        btnHalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDate(6);
                refresh();
            }
        });
        Button btnYear = findViewById(R.id.buttonYear);
        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDate(12);
                refresh();
            }
        });

        curIO = FileApp.OUT;
        typeStrId = FileApp.outTypeStrId;
        Switch switchIO = findViewById(R.id.switchIO);
        switchIO.setChecked(false);
        switchIO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    curIO = FileApp.IN;
                    typeStrId = FileApp.inTypeStrId;
                    refresh();
                } else {
                    curIO = FileApp.OUT;
                    typeStrId = FileApp.outTypeStrId;
                    refresh();
                }
            }
        });

        refresh();
    }

    public void initDate(int month) {
        yearL = FileApp.year;
        monthL = FileApp.month;
        dayL = FileApp.startingDate;
        if (dayL > FileApp.day) {
            monthL--;
            if (monthL == 0) {
                yearL--;
                monthL = 12;
            }
        }
        yearR = yearL;
        monthR = monthL + 1;
        dayR = dayL - 1;
        if (dayR == 0) {
            monthR--;
            switch (monthR) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    dayR = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    dayR = 30;
                    break;
                case 2:
                    dayR = 28;
                    if (yearR % 4 == 0 && yearR % 100 != 0 || yearR % 400 == 0)
                        dayR++;
                    break;
            }
        }
        if (monthR == 13) {
            yearR++;
            monthR = 1;
        }

        monthL -= (month - 1);
        if (monthL <= 0) {
            yearL--;
            monthL += 12;
        }
    }

    public void refresh() {
        TextView dateL = findViewById(R.id.sumDateL);
        dateL.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d", yearL, monthL, dayL));
        TextView dateR = findViewById(R.id.sumDateR);
        dateR.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d", yearR, monthR, dayR));
        int len;
        if (curIO.equals(FileApp.OUT))
            len = FileApp.numOutType;
        else
            len = FileApp.numInType;
        ArrayList<PieEntry> entries = new ArrayList<>();
        String date0 = app.dateToStr(yearL, monthL, dayL);
        String date1 = app.dateToStr(yearR, monthR, dayR);
        for (int i = 0; i < len; i++) {
            double sum = app.sumRange(curIO, date0, date1, i);
            if (sum != 0)
                entries.add(new PieEntry((float) sum, getString(typeStrId.get(i))));
        }

        PieChart pieChart = findViewById(R.id.pie);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setCenterText(new SpannableString(String.format(Locale.getDefault(), "总计\n%.2f", app.sumRange(curIO, date0, date1, -1))));
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setCenterTextSize(24f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        setData(entries);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);

    }

    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        PieChart pieChart = findViewById(R.id.pie);
        pieChart.setData(data);
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

}
