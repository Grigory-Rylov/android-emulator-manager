package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.EmulatorConfig;
import com.github.grishberg.androidemulatormanager.PreferenceContext;
import org.gradle.api.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static com.github.grishberg.androidemulatormanager.utils.SysUtils.executeWithArgsAndReturnOutput;
import static com.github.grishberg.androidemulatormanager.utils.SysUtils.getAvdSystemImage;

/**
 * Wrapper for sdkmanager
 */
public class SdkManager {
    private PreferenceContext context;
    private final Logger logger;
    private String pathToSdkManager;

    public SdkManager(PreferenceContext context,
                      String pathToSdkManager,
                      Logger logger) {
        this.context = context;
        this.logger = logger;
        this.pathToSdkManager = context.getAndroidSdkPath() + pathToSdkManager;
    }

    /**
     * Download emulator image for current apiLevel
     */
    public EmulatorImageType installEmulatorImage(EmulatorConfig emulatorConfig)
            throws AvdManagerException {
        logger.lifecycle("Installing system image for {} API", emulatorConfig.getApiLevel());
        EmulatorImageType emulatorImageType = getEmulatorImageTypeForApiLevel(emulatorConfig.getApiLevel());

        try {
            executeWithArgsAndReturnOutput(true, logger,
                    buildInstallAvdCommand(emulatorConfig, emulatorImageType));
        } catch (IOException e) {
            throw new AvdManagerException("exception while installing avd", e);
        }
        if (!getAvdSystemImage(context, emulatorConfig.getApiLevel(), emulatorImageType.getImageName())
                .exists()) {
            throw new NoEmulatorImageException("System image not downloaded, see debug for details");
        }
        return emulatorImageType;
    }

    private EmulatorImageType getEmulatorImageTypeForApiLevel(int apiLevel) {
        EmulatorImageType emulatorImageType = EmulatorImageType.GOOGLE_APIS;
        //TODO: this is hack, need to download list of available API to check image type.
        if (apiLevel < 21) {
            emulatorImageType = EmulatorImageType.DEFAULT;
        }
        return emulatorImageType;
    }

    private String[] buildInstallAvdCommand(EmulatorConfig emulatorConfig,
                                            EmulatorImageType emulatorImageType) {
        ArrayList<String> params = new ArrayList<>();
        params.add(pathToSdkManager);
        params.add("--install");
        //TODO: check EmulatorConfig.withPlayStore flag
        params.add(String.format(Locale.US, "system-images;android-%d;%s;x86",
                emulatorConfig.getApiLevel(),
                emulatorImageType.getImageName()));
        return params.toArray(new String[params.size()]);
    }
}
