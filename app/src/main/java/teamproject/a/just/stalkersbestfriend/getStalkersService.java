package teamproject.a.just.stalkersbestfriend;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Acid on 2/7/2017.
 */

public class getStalkersService extends Service{
    int mStartMode;                                                                                     /** indicates how to behave if the service is killed */
    IBinder mBinder;                                                                                    /** interface for clients that bind */
    boolean mAllowRebind;                                                                               /** indicates whether onRebind should be used */
    private final static int INTERVAL = 10 *1000 * 1000; //10 seconds
    Handler myHandler = new Handler();
    ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restart = new Intent(getApplicationContext(),this.getClass());
        restart.setPackage(getPackageName());
        startService(restart);
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Stopped getting data...", Toast.LENGTH_LONG).show();
    }

}
