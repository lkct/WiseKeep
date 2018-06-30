package javaprog.wisekeep;

import android.app.Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public static final int numOutType = 6;
    public static final int numInType = 5;

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
        // other init
        readSet();
        initDate();
        makeFileName();
        readTerm(OUT);
        readTerm(IN);
    }

    public String dateToStr(int year, int month, int day) {
        return String.format(Locale.getDefault(), "%04d%02d%02d", year, month, day);
    }

    public void makeFileName() {
        filename = dateToStr(year, month, day);
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
        outDateList = new ArrayList<>();
        inDateList = new ArrayList<>();
        saveSet();
    }

    public void saveTerm(String i_o) {
        ArrayList<Term> list;
        if (i_o.equals(OUT)) {
            list = outList;
            if (outDateList.indexOf(filename) == -1) {
                outDateList.add(filename);
                saveSet();
            }
        } else {
            list = inList;
            if (inDateList.indexOf(filename) == -1) {
                inDateList.add(filename);
                saveSet();
            }
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
            output.writeInt(outDateList.size());
            for (int i = 0; i < outDateList.size(); i++) {
                int date = Integer.valueOf(outDateList.get(i));
                output.writeInt(date);
            }
            output.writeInt(inDateList.size());
            for (int i = 0; i < inDateList.size(); i++) {
                int date = Integer.valueOf(inDateList.get(i));
                output.writeInt(date);
            }
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
            int size = input.readInt();
            outDateList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String date = String.valueOf(input.readInt());
                outDateList.add(date);
            }
            size = input.readInt();
            inDateList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String date = String.valueOf(input.readInt());
                inDateList.add(date);
            }
            input.close();
            inputFile.close();
        } catch (FileNotFoundException e) {
            initSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double sumDate(String i_o, String date, int type) {
        try {
            FileInputStream inputFile = openFileInput(i_o + date);
            DataInputStream input = new DataInputStream(inputFile);
            int size = input.readInt();
            double sum = 0;
            for (int i = 0; i < size; i++) {
                double amount = input.readDouble();
                int type_ = input.readInt();
                if (type_ == type || type == -1) {
                    sum += amount;
                }
                int len = input.readInt();
                for (int j = 0; j < len; j++) {
                    input.readChar();
                }
            }
            input.close();
            inputFile.close();
            return sum;
        } catch (FileNotFoundException e) {
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double sumRange(String i_o, String date0, String date1, int type) {
        ArrayList<String> dateList;
        if (i_o.equals(OUT)) {
            dateList = outDateList;
        } else {
            dateList = inDateList;
        }
        int size = dateList.size();
        double sum = 0;
        for (int i = 0; i < size; i++) {
            if ((Integer.valueOf(dateList.get(i)) >= Integer.valueOf(date0)) &&
                    (Integer.valueOf(dateList.get(i)) <= Integer.valueOf(date1))) {
                sum += sumDate(i_o, dateList.get(i), type);
            }
        }
        return sum;
    }

}
