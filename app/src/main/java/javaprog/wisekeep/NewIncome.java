package javaprog.wisekeep;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class NewIncome extends AppCompatActivity {
    
    public FileApp app;
    public int curCheck = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        app = (FileApp) this.getApplication();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_newin);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amt = ((EditText) findViewById(R.id.amount_newin)).getText().toString();
                if (amt.equals("")) {
                    Toast.makeText(NewIncome.this, "请输入金额", Toast.LENGTH_LONG).show();
                    return;
                }
                double amount = Double.valueOf(amt);
                if (curCheck == -1) {
                    Toast.makeText(NewIncome.this, "请选择类别", Toast.LENGTH_LONG).show();
                    return;
                }
                String desc = ((EditText) findViewById(R.id.desc_newin)).getText().toString();
                FileApp.Term t = new FileApp.Term();
                t.amount = amount;
                t.type = curCheck;
                t.description = desc;
                FileApp.inList.add(t);
                app.saveTerm(FileApp.IN);
                Toast.makeText(NewIncome.this, "保存成功", Toast.LENGTH_LONG).show();
                FileApp.mainAct.refreshRecycler();
                NewIncome.this.onBackPressed();
            }
        });
        final EditText amount = (EditText) findViewById(R.id.amount_newin);
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

        for (int i = 0; i < FileApp.inBtnId.size(); i++) {
            ((ToggleButton) findViewById((int) FileApp.inBtnId.get(i))).setOnCheckedChangeListener(OnChecked);
        }
    }

    CompoundButton.OnCheckedChangeListener OnChecked = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int thisId = buttonView.getId();
            if (isChecked) {
                // The toggle is enabled
                curCheck = FileApp.inBtnId.indexOf(thisId);
                for (int i = 0; i < FileApp.inBtnId.size(); i++) {
                    if (curCheck != i) {
                        ((ToggleButton) findViewById((int) FileApp.inBtnId.get(i))).setChecked(false);
                    }
                }
            } else {
                if ((int) FileApp.inBtnId.get(curCheck) == thisId) {
                    buttonView.setChecked(true);
                }
            }
        }
    };

}
