package com.aware.plugin.bitalino.domain;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aware.Aware;
import com.aware.plugin.bitalino.Plugin;

public class AwareStartReceiver extends BroadcastReceiver {
    public static final String INTERNAL_BITALINO_WORK = "pl.internal.bitalino";
    private Plugin plugin;
    Context context;

    public AwareStartReceiver(Context context, Plugin plugin) {
        this.context = context;
        this.plugin = plugin;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (INTERNAL_BITALINO_WORK.equalsIgnoreCase(intent.getAction())) {
            Boolean startWorking = intent.getBooleanExtra("work", false);

            if(startWorking) {
                Aware.startAWARE(context);
            } else {
                Plugin.enabledPlugin = false;
                plugin.setEndOfPluginWork();
                plugin.stopTask();
            }
        }
    }
}
