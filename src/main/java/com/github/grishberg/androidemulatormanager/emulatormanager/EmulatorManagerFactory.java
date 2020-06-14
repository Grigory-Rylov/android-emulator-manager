package com.github.grishberg.androidemulatormanager.emulatormanager;

import com.github.grishberg.androidemulatormanager.PreferenceContext;
import com.github.grishberg.androidemulatormanager.utils.AbsProvider;
import com.github.grishberg.androidemulatormanager.utils.Logger;

/**
 * Creates EmulatorManagerWrapper for current os.
 */
public class EmulatorManagerFactory extends AbsProvider {
    private final Logger logger;

    public EmulatorManagerFactory(Logger logger) {
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
