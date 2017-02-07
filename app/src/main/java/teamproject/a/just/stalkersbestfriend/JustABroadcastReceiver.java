package teamproject.a.just.stalkersbestfriend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class JustABroadcastReceiver extends BroadcastReceiver {
    public JustABroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, getStalkersOnDemand_Service.class);
        context.startService(serviceIntent);
        System.out.println("started12345");
    }
}
