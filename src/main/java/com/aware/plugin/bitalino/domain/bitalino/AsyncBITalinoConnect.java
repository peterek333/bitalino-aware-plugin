package com.aware.plugin.bitalino.domain.bitalino;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.aware.plugin.bitalino.common.JsonUtil;
import com.bitalino.comm.BITalinoFrame;

public class AsyncBITalinoConnect extends AsyncTask<Void, String, Void> {
    private static final String SEND_FRAMES_ACTION = "pl.agh.broadcast.FRAMES";
    private BITalino bitalino;
    private Context context;
    public volatile Boolean isWorking = true;

    public AsyncBITalinoConnect(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        bitalino = new BITalino();
        try {
            bitalino.connect();
            bitalino.startReadingData();

            while(isWorking) {
                BITalinoFrame[] frames = bitalino.getDataFrames();

                sendFramesBroadcast(frames);

                if (isCancelled())
                    break;
            }
            stopBitalino();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopBitalino();
    }

    private void stopBitalino() {
        bitalino.stop();
    }

    private void sendFramesBroadcast(BITalinoFrame[] frames) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(SEND_FRAMES_ACTION);
        broadcastIntent.putExtra("frames", JsonUtil.serializeToJson(frames));
        context.sendBroadcast(broadcastIntent);
    }
}
