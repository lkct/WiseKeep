package javaprog.wisekeep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

public class IncomeDetail extends AppCompatActivity {

    public FileApp app;
    private Button dlt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        app = (FileApp) this.getApplication();

        dlt = findViewById(R.id.deleteIn);
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
                                app.saveTerm(FileApp.IN);
                                FileApp.curDetail = -1;
                                FileApp.mainAct.refreshDateAmount();
                                FileApp.mainAct.refreshRecycler();
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

        TextView txt1, txt2, txt3;
        txt1 = findViewById(R.id.disAmoIn);
        txt2 = findViewById(R.id.disTypIn);
        txt3 = findViewById(R.id.disDesIn);
        FileApp.Term t = FileApp.inList.get(FileApp.curDetail);
        txt1.setText(String.format(Locale.getDefault(), "%.2f", t.amount));
        txt2.setText(FileApp.inTypeStrId.get(t.type));
        txt3.setText(t.description);
    }

    public void Deletion() {

    }
}
