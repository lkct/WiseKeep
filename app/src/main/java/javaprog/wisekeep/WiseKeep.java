package javaprog.wisekeep;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

public class WiseKeep extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePicker.OnDateChangedListener {

    public FileApp app;
    public String curIO = FileApp.OUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wise_keep);
        app = (FileApp) this.getApplication();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                    dialog.dismiss();
                }
            });

            final AlertDialog dialog = builder.create();
            View dialogView = View.inflate(WiseKeep.this, R.layout.dialog_data, null);
            final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);
            dialog.setTitle("设置日期");
            dialog.setView(dialogView);
            dialog.show();
            datePicker.init(FileApp.year, FileApp.month, FileApp.day, this);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_outcome) {
            findViewById(R.id.include_in).setVisibility(View.INVISIBLE);
            findViewById(R.id.include_out).setVisibility(View.VISIBLE);
            curIO = FileApp.OUT;
        } else if (id == R.id.nav_income) {
            findViewById(R.id.include_in).setVisibility(View.VISIBLE);
            findViewById(R.id.include_out).setVisibility(View.INVISIBLE);
            curIO = FileApp.IN;
        } else if (id == R.id.nav_summary) {
            Intent intent = new Intent(WiseKeep.this, Summary.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(WiseKeep.this, Settings.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void onDateChanged(DatePicker view, int year, int month, int day) {
        FileApp.year = year;
        FileApp.month = month;
        FileApp.day = day;
        app.makeFileName();
    }
}
