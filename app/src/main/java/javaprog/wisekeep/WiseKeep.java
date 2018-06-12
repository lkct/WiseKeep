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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wise_keep);
        app = (FileApp) this.getApplication();
        FileApp.mainAct = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        TO.setText(String.valueOf(app.sumDate(FileApp.OUT, FileApp.filename)));
        TI.setText(String.valueOf(app.sumDate(FileApp.IN, FileApp.filename)));
        String std = FileApp.filename.substring(0,6) + String.valueOf(FileApp.startingDate);
        if (FileApp.startingDate > FileApp.day)
            std = std.substring(0, 4) + String.valueOf(FileApp.month) + std.substring(6, 8);
        rtO.setText(String.valueOf(app.sumRange(FileApp.OUT, std, FileApp.filename)));
        rtI.setText(String.valueOf(app.sumRange(FileApp.IN, std, FileApp.filename)));

        RecyclerView recyclerOut = findViewById(R.id.recyclerOut);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerOut.setLayoutManager(layoutManager);
        CustomAdapter adapter = new CustomAdapter(FileApp.OUT);
        recyclerOut.setAdapter(adapter);
        RecyclerView recyclerIn = findViewById(R.id.recyclerIn);
        layoutManager = new LinearLayoutManager(this);
        recyclerIn.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(FileApp.IN);
        recyclerIn.setAdapter(adapter);

    }

    public void refreshRecycler() {
        RecyclerView recyclerOut = findViewById(R.id.recyclerOut);
        CustomAdapter adapter = new CustomAdapter(FileApp.OUT);
        recyclerOut.setAdapter(adapter);
        RecyclerView recyclerIn = findViewById(R.id.recyclerIn);
        adapter = new CustomAdapter(FileApp.IN);
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
        TO.setText(String.valueOf(app.sumDate(FileApp.OUT, FileApp.filename)));
        TI.setText(String.valueOf(app.sumDate(FileApp.IN, FileApp.filename)));
        String std = FileApp.filename.substring(0,6) + String.valueOf(FileApp.startingDate);
        if (FileApp.startingDate > FileApp.day)
            std = std.substring(0, 4) + String.valueOf(FileApp.month) + std.substring(6, 8);
        rtO.setText(String.valueOf(app.sumRange(FileApp.OUT, std, FileApp.filename)));
        rtI.setText(String.valueOf(app.sumRange(FileApp.IN, std, FileApp.filename)));
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
                    app.readTerm(FileApp.OUT);
                    app.readTerm(FileApp.IN);
                    // TODO: 刷新条目列表
                    refreshRecycler();
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
        FileApp.year = year;
        FileApp.month = month;
        FileApp.day = day;
        app.makeFileName();
    }

}
