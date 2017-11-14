package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.EmulatorConfig;
import com.github.grishberg.androidemulatormanager.utils.SysUtils;
import com.github.grishberg.androidemulatormanager.PreferenceContext;
import org.apache.commons.io.IOUtils;
import org.gradle.api.logging.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for avdmanager.
 */
public abstract class AvdManagerWrapper {
    private final Logger logger;
    private final String pathToAvdManager;

    public AvdManagerWrapper(PreferenceContext context, String pathToAvdManager, Logger logger) {
        this.logger = logger;
        this.pathToAvdManager = context.getAndroidSdkPath() + pathToAvdManager;
    }

    public boolean createAvd(EmulatorConfig arg) throws InterruptedException, AvdManagerException {
        boolean isAvdCreated = false;
        Process process;
        try {
            ProcessBuilder pb = new ProcessBuilder(buildCreateEmulatorCommand(arg));
            process = pb.start();
        } catch (IOException e) {
            throw new AvdManagerException("exception while creating emulator", e);
        }

        // Redirect process's stderr to a stream, for logging purposes
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        // Command may prompt us whether we want to further customise the AVD.
        // Just "press" Enter to continue with the selected target's defaults.
        try {
            boolean processAlive = true;

            // Block until the command outputs something (or process ends)
            final PushbackInputStream in = new PushbackInputStream(process.getInputStream(), 10);
            int len = in.read();
            if (len == -1) {
                // Check whether the process has exited badly, as sometimes no output is valid.
                // e.g. When creating an AVD with Google APIs, no user input is requested.
                if (process.waitFor() != 0) {

                    throw new AvdManagerException(SysUtils.readStringFromInputString(process.getErrorStream()));
                }
                processAlive = false;
            }
            in.unread(len);

            // Write CRLF, if required
            if (processAlive) {
                final OutputStream stream = process.getOutputStream();
                stream.write('\r');
                stream.write('\n');
                stream.flush();
                stream.close();
            }

            in.close();

            // Wait for happy ending
            if (process.waitFor() == 0) {
                // Do a sanity check to ensure the AVD was really created
                isAvdCreated = SysUtils.getAvdConfig(arg.getName()).exists();
            }
        } catch (IOException e) {
            // read any errors from the attempted command
            logger.error(SysUtils.readStringFromInputString(process.getErrorStream()));
            throw new AvdManagerException("Exception while creating avd", e);
        } finally {
            IOUtils.closeQuietly(stderr);
            process.destroy();
        }
        return isAvdCreated;
    }

    public boolean deleteAvd(EmulatorConfig arg) throws AvdManagerException {
        Runtime rt = Runtime.getRuntime();
        Process proc;
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        boolean isCreated = false;
        try {
            proc = rt.exec(buildDeleteAvdCommand(arg));
            stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream(), SysUtils.UTF8));

            stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream(), SysUtils.UTF8));

            // read the output from the command
            String line;
            while ((line = stdInput.readLine()) != null) {
                logger.info(line);
            }

            // read any errors from the attempted command
            while ((line = stdError.readLine()) != null) {
                logger.error(line);
            }
        } catch (IOException e) {
            throw new AvdManagerException("exception while creating avd", e);
        } finally {
            IOUtils.closeQuietly(stdInput);
            IOUtils.closeQuietly(stdError);
        }
        return isCreated;
    }

    List<String> buildCreateEmulatorCommand(EmulatorConfig arg) {
        ArrayList<String> params = new ArrayList<>();
        params.add(pathToAvdManager);
        params.add("-s");
        params.add("create");
        params.add("avd");
        params.add("-n");
        params.add(arg.getName());
        params.add("-k");
        params.add(buildSdkId(arg));
        return params;
    }

    private String buildSdkId(EmulatorConfig arg) {
        StringBuilder sb = new StringBuilder();
        sb.append("system-images;");
        sb.append("android-");
        sb.append(arg.getApiLevel());
        sb.append(";");
        sb.append("google_apis");
        if (arg.isWithPlaystore()) {
            sb.append("_playstore");
        }
        sb.append(";x86");
        return sb.toString();
    }

    private String[] buildDeleteAvdCommand(EmulatorConfig arg) {
        ArrayList<String> params = new ArrayList<>();
        params.add(pathToAvdManager);
        params.add("delete");
        params.add("avd");
        params.add("-n");
        params.add(arg.getName());
        return params.toArray(new String[params.size()]);
    }
}
