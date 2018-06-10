package javaprog.wisekeep;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;

public class NewOutcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_outcome);
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

    public void addNewOutcome() {
        FileApp app = (FileApp)this.getApplication();
        String fileName = FileApp.makeFileName();
        ArrayList list = new ArrayList();
        list = app.readTerm(FileApp.OUT, fileName);



        app.saveTerm(FileApp.OUT, fileName, list);
    }

    public void editOldOutcome() {
        FileApp app = (FileApp)this.getApplication();
        String fileName = FileApp.makeFileName();
        ArrayList list = new ArrayList();
        list = app.readTerm(FileApp.OUT, fileName);



        app.saveTerm(FileApp.OUT, fileName, list);
    }

    public void deleteOldOutcome() {
        FileApp app = (FileApp)this.getApplication();
        String fileName = FileApp.makeFileName();
        ArrayList list = new ArrayList();
        list = app.readTerm(FileApp.OUT, fileName);



        app.saveTerm(FileApp.OUT, fileName, list);
    }

}
