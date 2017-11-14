package com.github.grishberg.avdmanager.emulatormanager;

import com.github.grishberg.avdmanager.AndroidEmulator;
import com.github.grishberg.avdmanager.EmulatorConfig;
import org.apache.commons.io.IOUtils;
import org.gradle.api.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.github.grishberg.avdmanager.utils.SysUtils.UTF8;

/**
 * Wrapper for emulatormanager..
 */
public abstract class EmulatorManagerWrapper {
    private final Logger logger;
    private final String emulatorManagerPath;

    public EmulatorManagerWrapper(String emulatorManagerPath, Logger logger) {
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
        ArrayList<String> result = new ArrayList<>();
        Runtime rt = Runtime.getRuntime();
        Process process;
        StringBuilder errorSb;
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        try {
            process = rt.exec(emulatorManagerPath + " -list-avds");
            stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream(), UTF8));

            stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream(), UTF8));

            // read the output from the command
            String s;
            while ((s = stdInput.readLine()) != null) {
                result.add(s);
                logger.info(s);
            }

            // read any errors from the attempted command
            errorSb = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                errorSb.append(s);
                logger.error(s);
            }
        } catch (IOException e) {
            throw new EmulatorManagerException("exception while executing emulator manager", e);
        } finally {
            IOUtils.closeQuietly(stdInput);
            IOUtils.closeQuietly(stdError);
        }

        String errorString = errorSb.toString();
        if (errorString.length() != 0) {
            throw new EmulatorManagerException(errorString);
        }
        return result.toArray(new String[result.size()]);
    }
}
