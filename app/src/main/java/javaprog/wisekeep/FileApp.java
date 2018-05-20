package javaprog.wisekeep;

import android.app.Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileApp extends Application {

    // global vars
    public static final String IN = "in";
    public static final String OUT = "out";

    public static int startingDate;
    public static int budgetPerMonth;
    public static int goalPerMonth;

    public class Term {
        public int amount;
        public int type;
        public String description;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // other init
        readSet();
    }

    // global methods
    public void saveTerm(String i_o, String fileName, ArrayList list) {
        try {
            FileOutputStream outputFile = openFileOutput(i_o + fileName, MODE_PRIVATE);
            DataOutputStream output = new DataOutputStream(outputFile);
            output.writeInt(list.size());
            for (int i = 0; i < list.size(); i++) {
                Term t = (Term) list.get(i);
                output.writeInt(t.amount);
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

    public ArrayList readTerm(String i_o, String fileName) {
        try {
            FileInputStream inputFile = openFileInput(i_o + fileName);
            DataInputStream input = new DataInputStream(inputFile);
            ArrayList list = new ArrayList();
            int size = input.readInt();
            for (int i = 0; i < size; i++) {
                Term t = new Term();
                t.amount = input.readInt();
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
            return list;
        } catch (FileNotFoundException e) {
            return new ArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList();
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

    public void initSet() {
        startingDate = 1;
        budgetPerMonth = 0;
        goalPerMonth = 0;
        saveSet();
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
