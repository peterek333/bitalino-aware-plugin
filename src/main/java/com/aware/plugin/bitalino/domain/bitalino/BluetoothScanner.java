package com.aware.plugin.bitalino.domain.bitalino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.bitalino.comm.BITalinoDevice;
import com.bitalino.comm.BITalinoException;

import java.io.IOException;
import java.util.UUID;

public class BluetoothScanner {
    /*
     * http://developer.android.com/reference/android/bluetooth/BluetoothDevice.html
     * #createRfcommSocketToServiceRecord(java.util.UUID)
     *
     * "Hint: If you are connecting to a Bluetooth serial board then try using the
     * well-known SPP UUID 00001101-0000-1000-8000-00805F9B34FB. However if you
     * are connecting to an Android peer then please generate your own unique
     * UUID."
     */
    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothAdapter bluetoothAdapter;

    public BluetoothScanner() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothSocket getBITalinoBluetoothSocket(String bitalinoMACAddress) throws IOException {
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(bitalinoMACAddress);
        bluetoothAdapter.cancelDiscovery();

        BluetoothSocket bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
        bluetoothSocket.connect();
        return bluetoothSocket;
    }

    public BITalinoDevice createBITalinoDevice(BluetoothSocket bluetoothSocket, int sampleRate, int[] analogChannels) throws BITalinoException, IOException {
        BITalinoDevice bitalinoDevice = new BITalinoDevice(sampleRate, analogChannels);
        bitalinoDevice.open(bluetoothSocket.getInputStream(), bluetoothSocket.getOutputStream());

        return bitalinoDevice;
    }
}
