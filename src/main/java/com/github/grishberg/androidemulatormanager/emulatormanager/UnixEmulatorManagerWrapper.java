package com.github.grishberg.androidemulatormanager.emulatormanager;

import com.github.grishberg.androidemulatormanager.PreferenceContext;
import org.gradle.api.logging.Logger;

/**
 * Created by grishberg on 12.11.17.
 */
public class UnixEmulatorManagerWrapper extends EmulatorManagerWrapper {
    public UnixEmulatorManagerWrapper(PreferenceContext context, Logger logger) {
        super(context.getAndroidSdkPath() + "/emulator/emulator", logger);
    }
}
