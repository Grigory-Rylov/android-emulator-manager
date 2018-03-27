package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.PreferenceContext;
import com.github.grishberg.androidemulatormanager.utils.AbsProvider;
import org.gradle.api.logging.Logger;

import java.io.File;

/**
 * Creates AvdManagerWrapper for current OS.
 */
public class AvdManagerFabric extends AbsProvider {
    private final PreferenceContext context;
    private final HardwareManager hardwareManager;
    private SdkManager sdkManager;
    private final Logger logger;

    public AvdManagerFabric(PreferenceContext context,
                            HardwareManager hardwareManager,
                            SdkManager sdkManager,
                            Logger logger) {
        this.context = context;
        this.hardwareManager = hardwareManager;
        this.sdkManager = sdkManager;
        this.logger = logger;
    }

    public AvdManagerWrapper createAvdManagerForOs() {

        if (isWindows()) {
            throw new NoSuchMethodError();
        } else if (isMac()) {
            if (oldSdk()) {
                return new UnixAndroidManagerWrapper(context, hardwareManager, sdkManager, logger);
            }
            return new UnixAvdManagerWrapper(context, hardwareManager, sdkManager, logger);
        } else if (isUnix()) {
            if (oldSdk()) {
                return new UnixAndroidManagerWrapper(context, hardwareManager, sdkManager, logger);
            }
            return new UnixAvdManagerWrapper(context, hardwareManager, sdkManager, logger);
        } else if (isSolaris()) {
            throw new NoSuchMethodError();
        } else {
            throw new NoSuchMethodError("Your OS is not support!!");
        }
    }

    private boolean oldSdk() {
        File avdManagerFile = new File(context.getAndroidSdkPath(), "/tools/bin/avdmanager");
        return !avdManagerFile.exists();
    }
}
