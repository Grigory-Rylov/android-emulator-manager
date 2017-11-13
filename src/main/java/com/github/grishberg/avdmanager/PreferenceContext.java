package com.github.grishberg.avdmanager;

import java.util.Map;

/**
 * Created by grishberg on 12.11.17.
 */
public class PreferenceContext {
    private final Map<String, String> env;

    public PreferenceContext() {
        env = System.getenv();
    }

    public String getAndroidSdkPath() {
        return env.get("ANDROID_HOME");
    }
}
