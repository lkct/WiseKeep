package javaprog.wisekeep;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Locale;

public class WiseKeep extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePicker.OnDateChangedListener {

    public FileApp app;
    public String curIO;
    public int curYear, curMonth, curDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wise_keep);
        app = (FileApp) this.getApplication();
        FileApp.mainAct = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        curIO = FileApp.OUT;
        setTitle(R.string.title_activity_outcome);

        TextView DI = findViewById(R.id.dateIn);
        TextView DO = findViewById(R.id.dateOut);
        String str = String.format(Locale.getDefault(), "%04d-%02d-%02d", FileApp.year, FileApp.month, FileApp.day);
        DI.setText(str);
        DO.setText(str);

        TextView TO = findViewById(R.id.todayOut);
        TextView TI = findViewById(R.id.todayIn);
        TextView rtO = findViewById(R.id.r_tOut);
        TextView rtI = findViewById(R.id.r_tIn);
        TO.setText(String.format(Locale.getDefault(), "%.2f", app.sumDate(FileApp.OUT, FileApp.filename, -1)));
        TI.setText(String.format(Locale.getDefault(), "%.2f", app.sumDate(FileApp.IN, FileApp.filename, -1)));
        int yearL = FileApp.year;
        int monthL = FileApp.month;
        int dayL = FileApp.startingDate;
        if (dayL > FileApp.day) {
            monthL--;
            if (monthL == 0) {
                yearL--;
                monthL = 12;
            }
        }
        String dateL = app.dateToStr(yearL, monthL, dayL);
        rtO.setText(String.format(Locale.getDefault(), "%.2f / %d", app.sumRange(FileApp.OUT, dateL, FileApp.filename, -1), FileApp.budgetPerMonth));
        rtI.setText(String.format(Locale.getDefault(), "%.2f / %d", app.sumRange(FileApp.IN, dateL, FileApp.filename, -1), FileApp.goalPerMonth));

        RecyclerView recyclerOut = findViewById(R.id.recyclerOut);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerOut.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(FileApp.OUT);
        recyclerOut.setAdapter(adapter);
        RecyclerView recyclerIn = findViewById(R.id.recyclerIn);
        layoutManager = new LinearLayoutManager(this);
        recyclerIn.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(FileApp.IN);
        recyclerIn.setAdapter(adapter);

    }

    public void refreshRecycler() {
        RecyclerView recyclerOut = findViewById(R.id.recyclerOut);
        RecyclerAdapter adapter = new RecyclerAdapter(FileApp.OUT);
        recyclerOut.setAdapter(adapter);
        RecyclerView recyclerIn = findViewById(R.id.recyclerIn);
        adapter = new RecyclerAdapter(FileApp.IN);
        recyclerIn.setAdapter(adapter);
    }

    public void refreshDateAmount() {
        TextView DI = findViewById(R.id.dateIn);
        TextView DO = findViewById(R.id.dateOut);
        String str = String.format(Locale.getDefault(), "%04d-%02d-%02d", FileApp.year, FileApp.month, FileApp.day);
        DI.setText(str);
        DO.setText(str);

        TextView TO = findViewById(R.id.todayOut);
        TextView TI = findViewById(R.id.todayIn);
        TextView rtO = findViewById(R.id.r_tOut);
        TextView rtI = findViewById(R.id.r_tIn);
        TO.setText(String.format(Locale.getDefault(), "%.2f", app.sumDate(FileApp.OUT, FileApp.filename, -1)));
        TI.setText(String.format(Locale.getDefault(), "%.2f", app.sumDate(FileApp.IN, FileApp.filename, -1)));
        int yearL = FileApp.year;
        int monthL = FileApp.month;
        int dayL = FileApp.startingDate;
        if (dayL > FileApp.day) {
            monthL--;
            if (monthL == 0) {
                yearL--;
                monthL = 12;
            }
        }
        String dateL = app.dateToStr(yearL, monthL, dayL);
        rtO.setText(String.format(Locale.getDefault(), "%.2f / %d", app.sumRange(FileApp.OUT, dateL, FileApp.filename, -1), FileApp.budgetPerMonth));
        rtI.setText(String.format(Locale.getDefault(), "%.2f / %d", app.sumRange(FileApp.IN, dateL, FileApp.filename, -1), FileApp.goalPerMonth));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wise_keep, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_date) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WiseKeep.this);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FileApp.year = curYear;
                    FileApp.month = curMonth;
                    FileApp.day = curDay;
                    app.makeFileName();
                    app.readTerm(FileApp.OUT);
                    app.readTerm(FileApp.IN);
                    FileApp.mainAct.refreshDateAmount();
                    FileApp.mainAct.refreshRecycler();
                    dialog.dismiss();
                }
            });

            final AlertDialog dialog = builder.create();
            View dialogView = View.inflate(WiseKeep.this, R.layout.dialog_date, null);
            final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
            dialog.setTitle("设置日期");
            dialog.setView(dialogView);
            dialog.show();
            datePicker.init(FileApp.year, FileApp.month - 1, FileApp.day, this);
            return true;
        } else if (id == R.id.action_add) {
            if (curIO.equals(FileApp.OUT)) {
                Intent intent = new Intent(WiseKeep.this, NewOutcome.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(WiseKeep.this, NewIncome.class);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_outcome) {
            findViewById(R.id.include_in).setVisibility(View.INVISIBLE);
            findViewById(R.id.include_out).setVisibility(View.VISIBLE);
            curIO = FileApp.OUT;
            setTitle(R.string.title_activity_outcome);
        } else if (id == R.id.nav_income) {
            findViewById(R.id.include_in).setVisibility(View.VISIBLE);
            findViewById(R.id.include_out).setVisibility(View.INVISIBLE);
            curIO = FileApp.IN;
            setTitle(R.string.title_activity_income);
        } else if (id == R.id.nav_summary) {
            Intent intent = new Intent(WiseKeep.this, Summary.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(WiseKeep.this, Settings.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        curYear = year;
        curMonth = month + 1;
        curDay = day;
    }

}
