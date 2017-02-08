package teamproject.a.just.stalkersbestfriend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class getReceiver extends BroadcastReceiver {
    public getReceiver() {
    }
    //This receiver gets fired off by the OnBootStartGetReceiver
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, getStalkersService.class);              //create the intent giving {context ,the class to start}
        context.startService(serviceIntent);                                                        //start the service
    }
}
