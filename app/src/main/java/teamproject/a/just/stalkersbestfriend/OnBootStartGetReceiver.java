package teamproject.a.just.stalkersbestfriend;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class OnBootStartGetReceiver extends BroadcastReceiver {
    public OnBootStartGetReceiver() {
    }

    //Intent-filter BOOT_COMPLETE added to this one , making sure that it fires off when the device has booted
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);         //create the alarm manager
        Intent i = new Intent(context, getReceiver.class);                                                  //Create a new intent sending and the other receiver
        PendingIntent pI = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);    //Create a pending intent {context,singal(we define it),intent,Flag}
        Calendar rightNow = Calendar.getInstance();                                                         //get the instance of the calendar to a local calendar
        rightNow.add(Calendar.SECOND, 2);                                                                   //add to it ,10 minutes
        alarmManager.setInexactRepeating(AlarmManager.RTC, rightNow.getTimeInMillis(), 5 * 1000, pI);       //Resend the singal {don't wake up the device,starting time,interval,pending inent}
    }
}
