package com.github.grishberg.avdmanager.emulatorManager;

import com.github.grishberg.avdmanager.PreferenceContext;
import com.github.grishberg.avdmanager.utils.AbsProvider;
import org.gradle.api.logging.Logger;

/**
 * Creates EmulatorManagerWrapper for current os.
 */
public class EmulatorManagerFabric extends AbsProvider {
    public EmulatorManagerWrapper createEmulatorManagerForOs(PreferenceContext context,
                                                             Logger logger) {

        if (isWindows()) {
            throw new NoSuchMethodError();
        } else if (isMac()) {
            return new UnixEmulatorManagerWrapper(context, logger);
        } else if (isUnix()) {
            return new UnixEmulatorManagerWrapper(context, logger);
        } else if (isSolaris()) {
            throw new NoSuchMethodError();
        } else {
            throw new NoSuchMethodError("Your OS is not support!!");
        }
    }
}
