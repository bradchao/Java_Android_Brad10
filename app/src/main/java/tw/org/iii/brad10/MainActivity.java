package tw.org.iii.brad10;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        timer.schedule(new MyTimerTask(),0,200);
    }
    private class MyTimerTask extends TimerTask {
        int i;
        @Override
        public void run() {
            Log.v("brad", "i = " + i++);
            handler.sendEmptyMessage(i);
        }
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


}
