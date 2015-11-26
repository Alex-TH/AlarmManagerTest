package com.mezcalab.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by Alex-T on 26/11/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context,
                "Tenemos una nueva alarma muchacho!",
                Toast.LENGTH_LONG).show();
        //MainActivity.getTxtView2().setText("Descansaste demasiado, trabaja culero");
        Uri uri =  Uri.parse("android.resource://com.mezcalab.alarmmanager/" + R.raw.tingsha1_2);//RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
    }
}
