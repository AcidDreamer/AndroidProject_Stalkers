package teamproject.a.just.stalkersbestfriend;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by Acid on 2/8/2017.
 */

public class postReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);          //create the alarm manager
        Intent i = new Intent(context,postStalkerService.class);                                             //Create a new intent for the service
        PendingIntent pI = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);       //Create a pending intent  for a service{context,singal(we define it),intent,Flag}
        Calendar rightNow = Calendar.getInstance();                                                          //get the instance of the calendar to a local calendar
        rightNow.add(Calendar.SECOND, 5);                                                                    //add to it ,5 seconds
        alarmManager.setInexactRepeating(AlarmManager.RTC, rightNow.getTimeInMillis(), 30 * 1000, pI);       //Resend the singal {don't wake up the device,starting time,interval,pending inent}
    }
}
