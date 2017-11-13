package com.github.grishberg.avdmanager.emulatorManager;

import com.github.grishberg.avdmanager.PreferenceContext;

/**
 * Created by grishberg on 12.11.17.
 */
public class MacEmulatorManagerWrapper extends EmulatorManagerWrapper {
    public MacEmulatorManagerWrapper(PreferenceContext context) {
        super(context.getAndroidSdkPath() + "/emulator/emulator");
    }
}
