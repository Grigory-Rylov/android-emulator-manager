package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.PreferenceContext;
import com.github.grishberg.androidemulatormanager.utils.AbsProvider;
import org.gradle.api.logging.Logger;

/**
 * Creates AvdManagerWrapper for current OS.
 */
public class AvdManagerFabric extends AbsProvider {
    private final Logger logger;

    public AvdManagerFabric(Logger logger) {
        this.logger = logger;
    }

    public AvdManagerWrapper createAvdManagerForOs(PreferenceContext context) {

        if (isWindows()) {
            throw new NoSuchMethodError();
        } else if (isMac()) {
            return new UnixAvdManagerWrapper(context, logger);
        } else if (isUnix()) {
            return new UnixAvdManagerWrapper(context, logger);
        } else if (isSolaris()) {
            throw new NoSuchMethodError();
        } else {
            throw new NoSuchMethodError("Your OS is not support!!");
        }
    }
}
