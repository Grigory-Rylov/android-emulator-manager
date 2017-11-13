package com.github.grishberg.avdmanager.emulatorManager;

import com.github.grishberg.avdmanager.AndroidEmulator;
import com.github.grishberg.avdmanager.EmulatorConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by grishberg on 12.11.17.
 */
public abstract class EmulatorManagerWrapper {
    private final String emulatorManagerPath;

    public EmulatorManagerWrapper(String emulatorManagerPath) {
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
        ArrayList<String> result = new ArrayList<String>();
        Runtime rt = Runtime.getRuntime();
        Process proc;
        StringBuilder errorSb;
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        try {
            proc = rt.exec(emulatorManagerPath + " -list-avds");
            stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            String s;
            while ((s = stdInput.readLine()) != null) {
                result.add(s);
            }

            // read any errors from the attempted command
            errorSb = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                errorSb.append(s);
            }
        } catch (IOException e) {
            throw new EmulatorManagerException("exception while executing emulator manager", e);
        } finally {
            if (stdInput != null) {
                try {
                    stdInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (stdError != null) {
                try {
                    stdError.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String errorString = errorSb.toString();
        if (errorString.length() != 0) {
            throw new EmulatorManagerException(errorString);
        }
        return result.toArray(new String[result.size()]);
    }
}
