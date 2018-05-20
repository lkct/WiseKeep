package javaprog.wisekeep;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileApp extends Application {

    // global vars
    public static int budgetPerMonth;
    public static int startingDate;
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
    }

    // global methods
    private void save() {
        String content = editText.getText().toString();
        try {
            FileOutputStream outputStream = openFileOutput(fileName,
                    Activity.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        String content = "123";
        try {
            FileOutputStream outputStream = openFileOutput(fileName,
                    Activity.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read() {
        try {
            FileInputStream inputStream = this.openFileInput(fileName);
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            inputStream.close();
            arrayOutputStream.close();
            String content = new String(arrayOutputStream.toByteArray());
            showTextView.setText(content);

        } catch (FileNotFoundException e) {
            init();
            read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
