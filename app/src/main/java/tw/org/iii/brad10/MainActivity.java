package tw.org.iii.brad10;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private UIHnadler handler;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        handler = new UIHnadler();
        tv = (TextView)findViewById(R.id.tv);
    }

    public void test1(View v){
        new MyThread().start();
    }
    public void test2(View v){
        new Thread(){
            @Override
            public void run() {
                for (int i=0; i<10; i++){
                    Log.v("brad", "i = " + i);
                    handler.sendEmptyMessage(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();
    }

    public void test3(View v){
        timer.schedule(new MyTimerTask(),0,3000);
    }
    private class MyTimerTask extends TimerTask {
        int i;
        @Override
        public void run() {
            getLatLng();
        }
    }

    private void getLatLng(){
        //
        try {
            URL url = new URL("http://10.0.2.2:8080/BradWeb/Brad95.jsp?id=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer(); String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            Log.v("brad", sb.toString());
        }catch(Exception ee){
            Log.v("brad", ee.toString());
        }

    }
    public void test4(View v){
        getLatLng();
    }


    @Override
    public void finish() {
        if (timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }
        super.finish();
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            for (int i=0; i<10; i++){
                Log.v("brad", "i = " + i);
                handler.sendEmptyMessage(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private class UIHnadler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv.setText("i = " + msg.what);
        }
    }

    private class MyAsyncTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }
    }



}
