package javaprog.wisekeep;

import android.app.Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FileApp extends Application {

    // global vars
    public static WiseKeep mainAct;

    public static final String OUT = "out";
    public static final String IN = "in";

    public static final List<Integer> outBtnId = Arrays.asList(R.id.type0_newout, R.id.type1_newout, R.id.type2_newout,
            R.id.type3_newout, R.id.type4_newout, R.id.type5_newout);
    public static final List<Integer> inBtnId = Arrays.asList(R.id.type0_newin, R.id.type1_newin, R.id.type2_newin,
            R.id.type3_newin, R.id.type4_newin);
    public static final List<Integer> outTypeStrId = Arrays.asList(R.string.type0_out, R.string.type1_out, R.string.type2_out,
            R.string.type3_out, R.string.type4_out, R.string.type5_out);
    public static final List<Integer> inTypeStrId = Arrays.asList(R.string.type0_in, R.string.type1_in, R.string.type2_in,
            R.string.type3_in, R.string.type4_in);

    public static int startingDate;
    public static int budgetPerMonth;
    public static int goalPerMonth;

    public static int year, month, day;
    public static String filename;
    public static ArrayList<String> outDateList;
    public static ArrayList<String> inDateList;

    public static class Term {
        public double amount;
        public int type;
        public String description;
    }

    public static ArrayList<Term> inList;
    public static ArrayList<Term> outList;

    public static int curDetail = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: other init
        readSet();
        initDate();
        makeFileName();
        readTerm(OUT);
        readTerm(IN);
    }

    public void makeFileName() {
        filename = String.format(Locale.getDefault(), "%04d%02d%02d", year, month, day);
    }

    public void initDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void initSet() {
        startingDate = 1;
        budgetPerMonth = 0;
        goalPerMonth = 0;
        saveSet();
    }

    public void saveTerm(String i_o) {
        ArrayList<Term> list;
        if (i_o.equals(OUT)) {
            list = outList;
        } else {
            list = inList;
        }
        try {
            FileOutputStream outputFile = openFileOutput(i_o + filename, MODE_PRIVATE);
            DataOutputStream output = new DataOutputStream(outputFile);
            output.writeInt(list.size());
            for (int i = 0; i < list.size(); i++) {
                Term t = list.get(i);
                output.writeDouble(t.amount);
                output.writeInt(t.type);
                output.writeInt(t.description.length());
                char[] desc = t.description.toCharArray();
                for (char aDesc : desc) {
                    output.writeChar(aDesc);
                }
            }
            output.close();
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readTerm(String i_o) {
        try {
            FileInputStream inputFile = openFileInput(i_o + filename);
            DataInputStream input = new DataInputStream(inputFile);
            ArrayList<Term> list = new ArrayList<>();
            int size = input.readInt();
            for (int i = 0; i < size; i++) {
                Term t = new Term();
                t.amount = input.readDouble();
                t.type = input.readInt();
                int len = input.readInt();
                char[] desc = new char[len];
                for (int j = 0; j < len; j++) {
                    desc[j] = input.readChar();
                }
                t.description = new String(desc);
                list.add(t);
            }
            input.close();
            inputFile.close();
            if (i_o.equals(OUT)) {
                outList = list;
            } else {
                inList = list;
            }
        } catch (FileNotFoundException e) {
            if (i_o.equals(OUT)) {
                outList = new ArrayList<>();
            } else {
                inList = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSet() {
        try {
            FileOutputStream outputFile = openFileOutput("settings", MODE_PRIVATE);
            DataOutputStream output = new DataOutputStream(outputFile);
            output.writeInt(startingDate);
            output.writeInt(budgetPerMonth);
            output.writeInt(goalPerMonth);
            output.close();
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readSet() {
        try {
            FileInputStream inputFile = openFileInput("settings");
            DataInputStream input = new DataInputStream(inputFile);
            startingDate = input.readInt();
            budgetPerMonth = input.readInt();
            goalPerMonth = input.readInt();
            input.close();
            inputFile.close();
        } catch (FileNotFoundException e) {
            initSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
