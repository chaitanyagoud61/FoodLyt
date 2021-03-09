package com.chaitanya.quicksoft.glutton.activities.location;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import androidx.annotation.Nullable;


public class GpsService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
