package javaprog.wisekeep;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class NewOutcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_outcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_newout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText amount = (EditText) findViewById(R.id.amount_newout);
        amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        amount.setText(s);
                        amount.setSelection(s.length());
                    }
                }
                if (s.toString().trim().equals(".")) {
                    s = "0" + s;
                    amount.setText(s);
                    amount.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        amount.setText(s.subSequence(0, 1));
                        amount.setSelection(1);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }

    public void addNewOutcome() {
        FileApp app = (FileApp) this.getApplication();
        ArrayList list = new ArrayList();
        list = app.readTerm(FileApp.OUT);


        app.saveTerm(FileApp.OUT, list);
    }

    public void editOldOutcome() {
        FileApp app = (FileApp) this.getApplication();
        ArrayList list = new ArrayList();
        list = app.readTerm(FileApp.OUT);


        app.saveTerm(FileApp.OUT, list);
    }

    public void deleteOldOutcome() {
        FileApp app = (FileApp) this.getApplication();
        ArrayList list = new ArrayList();
        list = app.readTerm(FileApp.OUT);


        app.saveTerm(FileApp.OUT, list);
    }

}
