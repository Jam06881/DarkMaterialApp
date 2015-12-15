package com.chummy.dark.material.layers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.lovejoy777.rroandlayersmanager.IOperation;


public class Overlay extends Service {


    private final IOperation.Stub mulBinder =
            new IOperation.Stub() {
                public int operation(int i1, int i2) {
                    return i1 * i2;
                }
            };

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return mulBinder;
    }
}