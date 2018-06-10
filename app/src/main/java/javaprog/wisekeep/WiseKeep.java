package javaprog.wisekeep;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.Calendar;

public class WiseKeep extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DatePicker.OnDateChangedListener {

    public FileApp app = (FileApp) this.getApplication();
    public String curIO = FileApp.OUT;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wise_keep);
        context = this;

        initDateTime();
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

    private void initDateTime(){
        Calendar calendar = Calendar.getInstance();
        app.year = calendar.get(Calendar.YEAR);
        app.month = calendar.get(Calendar.MONTH);
        app.day = calendar.get(Calendar.DAY_OF_MONTH);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO: 刷新条目列表
                    dialog.dismiss();
                }
            });

            final AlertDialog dialog = builder.create();
            View dialogView = View.inflate(context, R.layout.dialog_data, null);
            final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);
            dialog.setTitle("设置日期");
            dialog.setView(dialogView);
            dialog.show();
            datePicker.init(app.year,app.month,app.day,this);
            return true;
        } else if (id == R.id.action_add) {
            if (curIO == FileApp.OUT) {
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

    @SuppressWarnings("StatementWithEmptyBody")
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


    public void onDateChanged(DatePicker view, int year, int month , int day){
        app.year = year;
        app.month = month;
        app.day = day;
    }
}
