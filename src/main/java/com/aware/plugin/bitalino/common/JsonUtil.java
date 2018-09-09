package com.aware.plugin.bitalino.common;

import com.bitalino.comm.BITalinoFrame;
import com.google.gson.Gson;

public class JsonUtil {
    public static String serializeToJson(BITalinoFrame[] frames) {
        return new Gson().toJson(frames);
    }
}
