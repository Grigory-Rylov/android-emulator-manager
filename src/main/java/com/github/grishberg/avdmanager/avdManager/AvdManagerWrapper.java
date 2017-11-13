package com.github.grishberg.avdmanager.avdManager;

import com.github.grishberg.avdmanager.EmulatorConfig;
import com.github.grishberg.avdmanager.PreferenceContext;
import com.github.grishberg.avdmanager.utils.SysUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grishberg on 12.11.17.
 */
public abstract class AvdManagerWrapper {
    private final String pathToAvdManager;

    public AvdManagerWrapper(PreferenceContext context, String pathToAvdManager) {
        this.pathToAvdManager = context.getAndroidSdkPath() + pathToAvdManager;
    }

    public boolean createAvd(EmulatorConfig arg) throws InterruptedException, AvdManagerException {
        Runtime rt = Runtime.getRuntime();

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
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        new ErrorReaderThread(process.getErrorStream(), stderr).start();
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
                    throw new AvdManagerException(stderr.toString());
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

            //TODO: read in to stdout to debug.
            SysUtils.copyStream(in, System.out);
            /*
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            // read the output from the command
            StringBuilder sbResult = new StringBuilder();
            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println(sbResult.toString());
*/

            //len = in.read();

            in.close();

            // Wait for happy ending
            if (process.waitFor() == 0) {
                // Do a sanity check to ensure the AVD was really created
                isAvdCreated = SysUtils.getAvdConfig(arg.getName()).exists();
            }
        } catch (IOException e) {
            throw new AvdManagerException("Exception while creating avd", e);
        } finally {
            process.destroy();
        }
        return isAvdCreated;
    }


    public boolean deleteAvd(EmulatorConfig arg) throws InterruptedException, AvdManagerException {
        Runtime rt = Runtime.getRuntime();
        Process proc;
        StringBuilder errorSb;
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        boolean isCreated = false;
        try {
            proc = rt.exec(buildDeleteAvdCommand(arg));
            stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            errorSb = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                errorSb.append(s);
                System.out.println("Error>>> " + s);
            }
        } catch (IOException e) {
            throw new AvdManagerException("exception while creating avd", e);
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
    private static class ErrorReaderThread extends Thread {
        private final InputStream procErrorStream;
        private final OutputStream stdError;

        ErrorReaderThread(InputStream procErrorStream, OutputStream stdError) {
            this.procErrorStream = procErrorStream;
            this.stdError = stdError;
        }

        @Override
        public void run() {
            int size;
            byte[] errorBuffer = new byte[4096];
            try {
                while ((size = procErrorStream.read(errorBuffer)) > 0) {
                    stdError.write(errorBuffer, 0, size);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (stdError != null) {
                    try {
                        stdError.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
