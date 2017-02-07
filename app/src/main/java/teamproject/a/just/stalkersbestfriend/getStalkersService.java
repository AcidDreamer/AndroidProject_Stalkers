package teamproject.a.just.stalkersbestfriend;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Acid on 2/7/2017.
 */

public class getStalkersService extends Service{
    private List<String> words;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
}
