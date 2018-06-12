package javaprog.wisekeep;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class Settings extends AppCompatActivity {
    public FileApp app;
    public int curCheck = -1;
    private Button btn1,btn2,btn3,btn4,btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_outcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        app = (FileApp) this.getApplication();
        btn1=(Button)findViewById(R.id.set_budget);
        btn2=(Button)findViewById(R.id.set_startingdate);
        btn3=(Button)findViewById(R.id.set_target);
        btn1.setOnClickListener(new Button.OnClickListener()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this)
                    .setTitle("输入预算金额")
                    .setView(R.layout.dialog2);
            final AlertDialog dialog_s = builder.create();
            @Override
            public void onClick(View arg0){
                dialog_s.show();
                String amt = ((EditText) findViewById(R.id.text_budge)).getText().toString();
                if (amt.equals("")) {
                    Toast.makeText(Settings.this, "请输入金额", Toast.LENGTH_LONG).show();
                    return;
                }
                final int amount = Integer.valueOf(amt);
                btn4 = (Button)findViewById(R.id.btn_save_pop);
                btn4.setOnClickListener(new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View arg0){
                        FileApp.budgetPerMonth = amount;
                    }
                });
            }
        });
        btn2.setOnClickListener(new Button.OnClickListener()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this)
                    .setTitle("输入每月起始日期")
                    .setView(R.layout.dialog3);
            final AlertDialog dialog_s = builder.create();
            @Override
            public void onClick(View arg0){
                dialog_s.show();
                String amt = ((EditText) findViewById(R.id.text_day)).getText().toString();
                if (amt.equals("")) {
                    Toast.makeText(Settings.this, "请输入起始日期", Toast.LENGTH_LONG).show();
                    return;
                }
                final int day = Integer.valueOf(amt);
                btn5 = (Button)findViewById(R.id.btn_save_pop);
                btn5.setOnClickListener(new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View arg0){
                        FileApp.startingDate = day;
                    }
                });
            }
        });
        btn3.setOnClickListener(new Button.OnClickListener()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this)
                    .setTitle("输入目标金额")
                    .setView(R.layout.dialog2);
            final AlertDialog dialog_s = builder.create();
            @Override
            public void onClick(View arg0){
                dialog_s.show();
                String amt = ((EditText) findViewById(R.id.text_budge)).getText().toString();
                if (amt.equals("")) {
                    Toast.makeText(Settings.this, "请输入金额", Toast.LENGTH_LONG).show();
                    return;
                }
                final int amount = Integer.valueOf(amt);
                btn4 = (Button)findViewById(R.id.btn_save_pop);
                btn4.setOnClickListener(new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View arg0){
                        FileApp.goalPerMonth = amount;
                    }
                });
            }
        });
    }

}
