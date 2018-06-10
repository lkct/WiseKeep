package javaprog.wisekeep;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class IncomeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FileApp app = (FileApp)this.getApplication();
        ArrayList list = new ArrayList();
        list = app.readTerm(FileApp.IN);
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            FileApp.Term t = (FileApp.Term)list.get(i);
            total += t.amount;
        }

        TextView t1 = (TextView)findViewById(R.id.incomeOfToday);
        t1.setText("Today's Income\n" +  String.valueOf(total));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
