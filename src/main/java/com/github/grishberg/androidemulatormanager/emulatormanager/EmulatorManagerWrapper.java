package com.github.grishberg.androidemulatormanager.emulatormanager;

import com.github.grishberg.androidemulatormanager.EmulatorConfig;
import com.github.grishberg.androidemulatormanager.PreferenceContext;
import com.github.grishberg.androidemulatormanager.emulator.AndroidEmulator;
import com.github.grishberg.androidemulatormanager.utils.Logger;
import com.github.grishberg.androidemulatormanager.utils.SysUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for emulatormanager..
 */
public abstract class EmulatorManagerWrapper {
    private final PreferenceContext context;
    private final Logger logger;
    private final String emulatorManagerPath;
    private String absEmulatorManagerPath;

    EmulatorManagerWrapper(PreferenceContext context, String emulatorManagerPath, Logger logger) {
        this.context = context;
        this.logger = logger;
        this.emulatorManagerPath = emulatorManagerPath;
    }

    public AndroidEmulator startEmulator(EmulatorConfig arg) throws EmulatorManagerException {
        AndroidEmulator result;
        Process process;
        try {
            ProcessBuilder pb = new ProcessBuilder(buildStartEmulatorCommand(arg));
            logger.info("AndroidEmulator: starting emulator {}", arg.getName());
            process = pb.start();
            result = new AndroidEmulator(process, arg, logger);
            logger.info("AndroidEmulator: emulator {} started", arg.getName());
        } catch (IOException e) {
            throw new EmulatorManagerException("Exception while starting emulator", e);
        }
        return result;
    }

    private String[] buildStartEmulatorCommand(EmulatorConfig arg) {
        ArrayList<String> params = new ArrayList<>();
        params.add(getAbsEmulatorManagerPath());
        params.add("-avd");
        params.add(arg.getName());
        params.add("-no-window");
        for (String additionalParameter : context.getAdditionalEmulatorParameters()) {
            params.add(additionalParameter);
        }
        if (logger.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder("buildStartEmulatorCommand:");
            for (String param : params) {
                sb.append(" ");
                sb.append(param);
            }
            logger.info(sb.toString());
        }
        return params.toArray(new String[params.size()]);
    }

    public String[] getAvdList() throws EmulatorManagerException {
        List<String> result;
        try {
            result = SysUtils.executeWithArgsAndReturnOutput(logger, emulatorManagerPath + " -list-avds");
        } catch (IOException e) {
            throw new EmulatorManagerException("exception while executing emulator manager", e);
        }
        return result.toArray(new String[result.size()]);
    }

    private String getAbsEmulatorManagerPath() {
        if (absEmulatorManagerPath == null) {
            absEmulatorManagerPath = new File(context.getAndroidSdkPath(), emulatorManagerPath)
                    .getAbsolutePath();
        }
        return absEmulatorManagerPath;
    }
}
