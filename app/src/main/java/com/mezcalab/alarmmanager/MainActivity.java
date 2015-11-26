package com.mezcalab.alarmmanager;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.os.Build;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static int hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int minuto = Calendar.getInstance().get(Calendar.MINUTE);
    private TextView txtView1;
    static TextView txtView2;

    public static TextView getTxtView2() {
        return txtView2;
    }
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtView1 = (TextView) findViewById(R.id.msg1);
        txtView1.setText(hora + ":" + minuto);
        txtView2 = (TextView) findViewById((R.id.msg2));

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

        OnClickListener evento1 = new OnClickListener() {
            public void onClick(View view) {
                txtView2.setText("");
                Bundle bundle = new Bundle();
                bundle.putInt(MyConstants.HOUR, hora);
                bundle.putInt(MyConstants.MINUTE, minuto);
                MyDialogFragment fragment = new MyDialogFragment(new MyHandler());
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.add(fragment, MyConstants.TIME_PICKER);
                transaction.commit();
            }
        };

        Button btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(evento1);
        OnClickListener event2 = new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtView2.setText("");
                cancelAlarm();
            }
        };

        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(event2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            hora =  bundle.getInt(MyConstants.HOUR);
            minuto =  bundle.getInt(MyConstants.MINUTE);
            txtView1.setText(hora + ":" + minuto);
            setAlarm();
        }
    }

    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            txtView1.setText("Que paso muchacho");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private void cancelAlarm() {
        if(alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
