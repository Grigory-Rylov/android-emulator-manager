package com.github.grishberg.androidemulatormanager.utils;

import java.io.*;
import java.nio.charset.Charset;

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
}
