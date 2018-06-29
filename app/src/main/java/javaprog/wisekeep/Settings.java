package javaprog.wisekeep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    public FileApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app = (FileApp) this.getApplication();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
            String stDate = ((EditText) findViewById(R.id.editText_stDate)).getText().toString();
            // TODO: check robust of all of Settings
//            if (amt.equals("")) {
//                Toast.makeText(NewOutcome.this, "请输入金额", Toast.LENGTH_LONG).show();
//                return;
//            }
            FileApp.startingDate = Integer.valueOf(stDate);
            String budget = ((EditText) findViewById(R.id.editText_budget)).getText().toString();
            FileApp.budgetPerMonth = Integer.valueOf(budget);
            String goal = ((EditText) findViewById(R.id.editText_goal)).getText().toString();
            FileApp.goalPerMonth = Integer.valueOf(goal);
            app.saveSet();
            Toast.makeText(Settings.this, "保存成功", Toast.LENGTH_LONG).show();
            // TODO: refresh
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
