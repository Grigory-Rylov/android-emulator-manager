package com.github.grishberg.androidemulatormanager.emulatormanager;

import com.github.grishberg.androidemulatormanager.PreferenceContext;
import com.github.grishberg.androidemulatormanager.utils.AbsProvider;
import org.gradle.api.logging.Logger;

/**
 * Creates EmulatorManagerWrapper for current os.
 */
public class EmulatorManagerFabric extends AbsProvider {
    private final Logger logger;

    public EmulatorManagerFabric(Logger logger) {
        this.logger = logger;
    }

    public EmulatorManagerWrapper createEmulatorManagerForOs(PreferenceContext context) {

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
