package com.mezcalab.alarmmanager;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TimePicker;

/**
 * Created by Alex-T on 26/11/15.
 */
public class MyDialogFragment extends DialogFragment {
    private int hora;
    private int min;
    private Handler handler;
    //public  MyDialogFragment() {}
    public MyDialogFragment(Handler handler) {
        this.handler = handler;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle bundle = getArguments();
        hora = bundle.getInt(MyConstants.HOUR);
        min = bundle.getInt(MyConstants.MINUTE);
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hora = hourOfDay;
                min = minute;
                Bundle b = new Bundle();
                b.putInt(MyConstants.HOUR, hora);
                b.putInt(MyConstants.MINUTE, min);
                Message msg = new Message();
                msg.setData(b);
                handler.sendMessage(msg);
            }
        };

        return new TimePickerDialog(getActivity(), listener, hora, min, false);
    }

}
