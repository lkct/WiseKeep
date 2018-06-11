package javaprog.wisekeep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class IncomeDetail extends AppCompatActivity {

    private Button rtn, dlt, edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rtn = (Button) findViewById(R.id.returnIn);
        dlt = (Button) findViewById(R.id.deleteIn);
        edt = (Button) findViewById(R.id.editIn);

        //rtn.setOnClickListener((view) -> {

        //});
        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setMessage(R.string.dltDecision);
                alertDialogBuilder.setPositiveButton(R.string.positive_btn,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //FileApp.inDateList.remove();
                                Intent positveActivity = new Intent(getApplicationContext(),WiseKeep.class);
                                startActivity(positveActivity);
                            }
                        });
                alertDialogBuilder.setNegativeButton(R.string.negative_btn,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    public void Deletion() {

    }
}
