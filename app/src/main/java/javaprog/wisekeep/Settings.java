package javaprog.wisekeep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    public FileApp app;
    public int curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app = (FileApp) this.getApplication();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        EditText budget = findViewById(R.id.editText_budget);
        budget.setText(String.valueOf(FileApp.budgetPerMonth));
        EditText goal = findViewById(R.id.editText_goal);
        goal.setText(String.valueOf(FileApp.goalPerMonth));
        Spinner sp = findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curDate = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        curDate = FileApp.startingDate;
        sp.setSelection(curDate - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            FileApp.startingDate = curDate;
            String budget = ((EditText) findViewById(R.id.editText_budget)).getText().toString();
            FileApp.budgetPerMonth = Integer.valueOf(budget);
            String goal = ((EditText) findViewById(R.id.editText_goal)).getText().toString();
            FileApp.goalPerMonth = Integer.valueOf(goal);
            app.saveSet();
            Toast.makeText(Settings.this, "保存成功", Toast.LENGTH_LONG).show();
            FileApp.mainAct.refreshDateAmount();
            FileApp.mainAct.refreshRecycler();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
