package javaprog.wisekeep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class IncomeDetail extends AppCompatActivity {

    private Button rtn, dlt, edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rtn = findViewById(R.id.returnIn);
        dlt = findViewById(R.id.deleteIn);
        edt = findViewById(R.id.editIn);

        rtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IncomeDetail.this.onBackPressed();
            }
        });

        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IncomeDetail.this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setMessage(R.string.dltDecision);
                alertDialogBuilder.setPositiveButton(R.string.positive_btn,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                FileApp.inList.remove(FileApp.curDetail);
                                FileApp.curDetail = -1;
                                // TODO:refresh the act...
                                IncomeDetail.this.onBackPressed();
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
