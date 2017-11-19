package com.github.grishberg.androidemulatormanager.emulatormanager;

import com.github.grishberg.androidemulatormanager.AndroidEmulator;
import com.github.grishberg.androidemulatormanager.EmulatorConfig;
import com.github.grishberg.androidemulatormanager.utils.SysUtils;
import org.gradle.api.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for emulatormanager..
 */
public abstract class EmulatorManagerWrapper {
    private final Logger logger;
    private final String emulatorManagerPath;

    EmulatorManagerWrapper(String emulatorManagerPath, Logger logger) {
        this.logger = logger;
        this.emulatorManagerPath = emulatorManagerPath;
    }

    public AndroidEmulator startEmulator(EmulatorConfig arg) throws EmulatorManagerException {
        AndroidEmulator result;
        Process process;
        try {
            ProcessBuilder pb = new ProcessBuilder(buildStartEmulatorCommand(arg));
            process = pb.start();
            result = new AndroidEmulator(process, arg);
        } catch (IOException e) {
            throw new EmulatorManagerException("exception while starting emulator", e);
        }
        return result;
    }

    private String[] buildStartEmulatorCommand(EmulatorConfig arg) {
        ArrayList<String> params = new ArrayList<>();
        params.add(emulatorManagerPath);
        params.add("-avd");
        params.add(arg.getName());
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
}
