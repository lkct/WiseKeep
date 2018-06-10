package javaprog.wisekeep;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class NewIncome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addNewIncome() {
        FileApp app = (FileApp)this.getApplication();
        String fileName = FileApp.makeFileName();
        ArrayList list = new ArrayList();
        list = app.readTerm(FileApp.IN, fileName);

        FileApp.Term t = app.new Term();
        list.add(t);

        app.saveTerm(FileApp.IN, fileName, list);
    }

    public void editOldIncome() {
        FileApp app = (FileApp)this.getApplication();
        String fileName = FileApp.makeFileName();
        ArrayList list = new ArrayList();
        list = app.readTerm(FileApp.IN, fileName);



        app.saveTerm(FileApp.IN, fileName, list);
    }

    public void deleteOldIncome() {
        FileApp app = (FileApp)this.getApplication();
        String fileName = FileApp.makeFileName();
        ArrayList list = new ArrayList();
        list = app.readTerm(FileApp.IN, fileName);



        app.saveTerm(FileApp.IN, fileName, list);
    }

}
