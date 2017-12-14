package com.github.grishberg.androidemulatormanager.utils;

import com.github.grishberg.androidemulatormanager.PreferenceContext;
import org.gradle.api.logging.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Helpers methods.
 */
public class SysUtils {
    public static final Charset UTF8 = Charset.forName("UTF-8");

    private SysUtils() {/*not needed*/}

    public static File getAvdHomeDir() {
        String homeDir = System.getenv("HOME");
        return new File(homeDir, ".android/avd");
    }

    public static String readStringFromInputString(InputStream is) {
        String line;
        BufferedReader stdError = new BufferedReader(
                new InputStreamReader(is, UTF8));
        StringBuilder errorSb = new StringBuilder();
        try {
            while ((line = stdError.readLine()) != null) {
                errorSb.append(line);
                errorSb.append("\n");
            }
        } catch (IOException e) {
            return e.getLocalizedMessage();
        }
        return errorSb.toString();
    }

    public static File getAvdConfig(String name) {
        return new File(getAvdHomeDir(), name + ".ini");
    }

    public static File getAvdSystemImage(PreferenceContext context,
                                   int apiLevel, String imageType) {
        return new File(context.getAndroidSdkPath(),
                String.format(Locale.US, "system-images/android-%d/%s/x86/system.img",
                        apiLevel, imageType));
    }

    public static List<String> executeWithArgsAndReturnOutput(Logger logger, String... cmd)
            throws IOException {
        return executeWithArgsAndReturnOutput(false, logger, cmd);
    }

    public static List<String> executeWithArgsAndReturnOutput(boolean ignoreErrors,
                                                              Logger logger,
                                                              String... cmd)
            throws IOException {
        ArrayList<String> result = new ArrayList<>();
        Runtime rt = Runtime.getRuntime();
        Process process;
        StringBuilder errorSb;

        process = rt.exec(cmd);

        try (BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(process.getInputStream(), SysUtils.UTF8));

             BufferedReader stdError = new BufferedReader(new
                     InputStreamReader(process.getErrorStream(), SysUtils.UTF8))) {

            // read the output from the command
            String line;
            while ((line = stdInput.readLine()) != null) {
                result.add(line);
                logger.info(line);
            }

            // read any errors from the attempted command
            errorSb = new StringBuilder();
            while ((line = stdError.readLine()) != null) {
                errorSb.append(line);
                logger.error(line);
            }

            String errorString = errorSb.toString();
            if (errorString.length() != 0) {
                if (!ignoreErrors) {
                    throw new IOException(errorString);
                }
            }
        }
        return result;
    }
}
