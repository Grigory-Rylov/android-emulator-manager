package com.github.grishberg.avdmanager.emulatorManager;

import com.github.grishberg.avdmanager.PreferenceContext;
import com.github.grishberg.avdmanager.utils.AbsProvider;

/**
 * Created by grishberg on 12.11.17.
 */
public class EmulatorManagerFabric extends AbsProvider {
    public EmulatorManagerWrapper createEmulatorManagerForOs(PreferenceContext context) {

        if (isWindows()) {
            throw new NoSuchMethodError();
        } else if (isMac()) {
            return new MacEmulatorManagerWrapper(context);
        } else if (isUnix()) {
            throw new NoSuchMethodError();
        } else if (isSolaris()) {
            throw new NoSuchMethodError();
        } else {
            throw new NoSuchMethodError("Your OS is not support!!");
        }
    }
}
