package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.PreferenceContext;
import com.github.grishberg.androidemulatormanager.utils.SysUtils;
import org.gradle.api.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Wrapper for sdkmanager
 */
public class SdkManager {
    private final Logger logger;
    private String pathToSdkManager;

    public SdkManager(PreferenceContext context,
                      String pathToSdkManager,
                      Logger logger) {
        this.logger = logger;
        this.pathToSdkManager = context.getAndroidSdkPath() + pathToSdkManager;
    }

    /**
     * Download emulator image for current apiLevel
     */
    public EmulatorImageType installEmulatorImage(int apiLevel) throws AvdManagerException {
        logger.lifecycle("Installing system image for {} API", apiLevel);
        try {
            SysUtils.executeWithArgsAndReturnOutput(logger, buildInstallAvdCommand(apiLevel));
        } catch (IOException e) {
            throw new AvdManagerException("exception while installing avd", e);
        }
        return EmulatorImageType.GOOGLE_APIS;
    }

    private String[] buildInstallAvdCommand(int apiLevel) {
        ArrayList<String> params = new ArrayList<>();
        params.add(pathToSdkManager);
        params.add("--install");
        params.add(String.format(Locale.US, "\"system-images;android-%d;google_apis;x86\"", apiLevel));
        return params.toArray(new String[params.size()]);
    }
}
