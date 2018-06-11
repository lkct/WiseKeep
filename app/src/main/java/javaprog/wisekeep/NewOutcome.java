package javaprog.wisekeep;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Arrays;
import java.util.List;

public class NewOutcome extends AppCompatActivity {

    public static final List buttonIds = Arrays.asList(R.id.type0_newout, R.id.type1_newout, R.id.type2_newout,
            R.id.type3_newout, R.id.type4_newout, R.id.type5_newout);
    public int curCheck = -1;

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
                String amt = ((EditText) findViewById(R.id.amount_newout)).getText().toString();
                if (amt.equals("")){
                    Toast.makeText(NewOutcome.this, "请输入金额", Toast.LENGTH_LONG).show();
                    return;
                }
                double amount = Double.valueOf(amt);
                String desc = ((EditText) findViewById(R.id.desc_newout)).getText().toString();

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

        for (int i = 0; i < buttonIds.size(); i++) {
            ((ToggleButton) findViewById((int) buttonIds.get(i))).setOnCheckedChangeListener(OnChecked);
        }
    }

    CompoundButton.OnCheckedChangeListener OnChecked = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int thisId = buttonView.getId();
            if (isChecked) {
                // The toggle is enabled
                curCheck = buttonIds.indexOf(thisId);
                for (int i = 0; i < buttonIds.size(); i++) {
                    if (curCheck != i) {
                        ((ToggleButton) findViewById((int) buttonIds.get(i))).setChecked(false);
                    }
                }
            } else {
                if ((int) buttonIds.get(curCheck) == thisId) {
                    buttonView.setChecked(true);
                }
            }
        }
    };

}
