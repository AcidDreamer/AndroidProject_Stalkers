package teamproject.a.just.stalkersbestfriend;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;


public class postStalkerService extends IntentService {
    public postStalkerService() {
        super("MyWorkerThread2");                                                                    //Naming our baby
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {                              //When we start the service/thread
        Toast.makeText(this, "Sending your location...", Toast.LENGTH_LONG).show();          //Inform the user
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {                                                                       //When the user stops the service
        super.onDestroy();
    }

    protected void onHandleIntent(Intent intent) {

    }
}
