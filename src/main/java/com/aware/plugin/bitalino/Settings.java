package com.aware.plugin.bitalino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.aware.Aware;

import java.util.Set;
import java.util.TreeSet;

import static com.aware.plugin.bitalino.AwareStartReceiver.INTERNAL_BITALINO_WORK;

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    //Plugin settings in XML @xml/preferences
    public static final String STATUS_PLUGIN_TEMPLATE = "status_plugin_template";
    public static final String BITALINO_MAC_ADDRESS = "bitalino_mac_address";
    public static String bitalinoMacAddress = "";
    public static final String BITALINO_CHANNELS = "bitalino_channels";
    public static int[] bitalinoChannels = new int[0];

    //Plugin settings UI elements
    private static CheckBoxPreference status;
    private static EditTextPreference macAddress;
    private static MultiSelectListPreference channels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        status = (CheckBoxPreference) findPreference(STATUS_PLUGIN_TEMPLATE);
        if( Aware.getSetting(this, STATUS_PLUGIN_TEMPLATE).length() == 0 ) {
            Aware.setSetting( this, STATUS_PLUGIN_TEMPLATE, false );
        }
        status.setChecked(Aware.getSetting(getApplicationContext(), STATUS_PLUGIN_TEMPLATE).equals("true"));

        macAddress = (EditTextPreference) findPreference(BITALINO_MAC_ADDRESS);
        setMacAddress();

        channels = (MultiSelectListPreference) findPreference(BITALINO_CHANNELS);
        setBitalinoChannels();
    }

    private void setBitalinoChannels() {
        StringBuilder channelsText = new StringBuilder("[");
        int[] channelsRepresentation = new int[ channels.getValues().size() ];
        int i = 0;
        Set<String> values = new TreeSet<>(channels.getValues());
        for(String value: values) {
            int index = channels.findIndexOfValue(value);
            channelsText.append(
                    channels.getEntries()[index] + " ");
            channelsRepresentation[i++] =
                    Integer.parseInt(channels.getEntryValues()[index].toString());
        }
        channelsText.append("]");
        channels.setSummary(channelsText.toString());

        bitalinoChannels = channelsRepresentation;
    }

    private void setMacAddress() {
        bitalinoMacAddress = macAddress.getText();
        macAddress.setSummary(bitalinoMacAddress);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference setting = findPreference(key);
        if( setting.getKey().equals(STATUS_PLUGIN_TEMPLATE) ) {
            Aware.setSetting(this, key, sharedPreferences.getBoolean(key, false));
            status.setChecked(sharedPreferences.getBoolean(key, false));
        }
        if( setting.getKey().equals(BITALINO_MAC_ADDRESS)) {
            setMacAddress();
        }
        if( setting.getKey().equals(BITALINO_CHANNELS)) {
            setBitalinoChannels();
        }
        if (Aware.getSetting(this, STATUS_PLUGIN_TEMPLATE).equals("true")) {
            Plugin.enabledPlugin = true;
            sendToBitalinoTask(true);
            Aware.startPlugin(getApplicationContext(), "com.aware.plugin.bitalino");
        } else {
            sendToBitalinoTask(false);
            Aware.stopPlugin(getApplicationContext(), "com.aware.plugin.bitalino");
        }
    }

    private void sendToBitalinoTask(boolean work) {
        Intent bitalinoIntent = new Intent(INTERNAL_BITALINO_WORK);
        bitalinoIntent.putExtra("work", work);
        sendBroadcast(bitalinoIntent);
    }
}
