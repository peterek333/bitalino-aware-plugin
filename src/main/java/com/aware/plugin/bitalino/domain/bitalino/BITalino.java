package com.aware.plugin.bitalino.domain.bitalino;

import android.bluetooth.BluetoothSocket;

import com.aware.plugin.bitalino.domain.Settings;
import com.bitalino.comm.BITalinoDevice;
import com.bitalino.comm.BITalinoException;
import com.bitalino.comm.BITalinoFrame;

import java.io.IOException;

public class BITalino {
    public static boolean connected = false;
    private static final int SAMPLE_RATE = 1000;

    private BluetoothSocket bluetoothSocket;
    private BITalinoDevice bitalinoDevice;

    public void connect() throws IOException, BITalinoException {
        BluetoothScanner bluetoothScanner = new BluetoothScanner();

        bluetoothSocket = bluetoothScanner.getBITalinoBluetoothSocket(Settings.bitalinoMacAddress);

        bitalinoDevice = bluetoothScanner.createBITalinoDevice(bluetoothSocket, SAMPLE_RATE, Settings.bitalinoChannels);
    }

    public void startReadingData() throws Exception {
        if(bitalinoDevice == null) {
            throw new Exception("BITalino is not connected");
        }
        bitalinoDevice.start();
        connected = true;
    }

    public void stop() {
        try {
            connected = false;
            bitalinoDevice.stop();
            bluetoothSocket.close();
        } catch (IOException | BITalinoException e) {
            e.printStackTrace();
        }
    }

    public BITalinoFrame[] getDataFrames() throws BITalinoException {
        return bitalinoDevice.read(SAMPLE_RATE);
    }
}
