package com.aware.plugin.bitalino;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.plugin.bitalino.domain.AwareStartReceiver;
import com.aware.plugin.bitalino.domain.bitalino.AsyncBITalinoConnect;
import com.aware.plugin.bitalino.domain.Settings;
import com.aware.utils.Aware_Plugin;

import static com.aware.plugin.bitalino.domain.AwareStartReceiver.INTERNAL_BITALINO_WORK;

public class Plugin extends Aware_Plugin {

    public static boolean enabledPlugin = false;

    private BroadcastReceiver awareStartReceiver = new AwareStartReceiver(this, this);
    private AsyncBITalinoConnect asyncBITalinoConnect;

    @Override
    public void onCreate() {
        super.onCreate();

        TAG = "AWARE::"+getResources().getString(R.string.app_name);

        /**
         * Plugins share their current status, i.e., context using this method.
         * This method is called automatically when triggering
         * {@link Aware#ACTION_AWARE_CURRENT_CONTEXT}
         **/
        CONTEXT_PRODUCER = new ContextProducer() {
            @Override
            public void onContext() {
                //Broadcast your context here
            }
        };

        IntentFilter intentFilter = new IntentFilter(INTERNAL_BITALINO_WORK);

        registerReceiver(awareStartReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(awareStartReceiver);

        if(asyncBITalinoConnect != null) {
            asyncBITalinoConnect.isWorking = false;
        }
        setEndOfPluginWork();
        Aware.stopAWARE(this);
    }

    //This function gets called every 5 minutes by AWARE to make sure this plugin is still running.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (PERMISSIONS_OK && enabledPlugin) {

            DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");

            Aware.setSetting(this, Settings.STATUS_PLUGIN_TEMPLATE, true);

            startTask();

            Aware.startAWARE(this);
        }

        return START_STICKY;
    }

    private void startTask() {
        if(asyncBITalinoConnect == null) {
            asyncBITalinoConnect = new AsyncBITalinoConnect(this);
            asyncBITalinoConnect.execute();
        }
    }

    public void stopTask() {
        if(asyncBITalinoConnect != null) {
            asyncBITalinoConnect.isWorking = false;
            asyncBITalinoConnect.cancel(true);
            asyncBITalinoConnect = null;
        }
    }

    public void setEndOfPluginWork() {
        Aware.setSetting(this, Settings.STATUS_PLUGIN_TEMPLATE, false);
    }


}
