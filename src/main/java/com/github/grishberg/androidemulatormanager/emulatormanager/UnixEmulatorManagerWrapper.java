package com.github.grishberg.androidemulatormanager.emulatormanager;

import com.github.grishberg.androidemulatormanager.PreferenceContext;
import com.github.grishberg.androidemulatormanager.utils.Logger;

/**
 * Created by grishberg on 12.11.17.
 */
public class UnixEmulatorManagerWrapper extends EmulatorManagerWrapper {
    public UnixEmulatorManagerWrapper(PreferenceContext context, Logger logger) {
        super(context, "emulator/emulator", logger);
    }
}
