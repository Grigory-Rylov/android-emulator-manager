package com.github.grishberg.avdmanager.utils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by grishberg on 12.11.17.
 */
public class SysUtils {
    public static final Charset UTF8 = Charset.forName("UTF-8");

    public static File getAvdHomeDir() {
        String homeDir = System.getenv("HOME");
        return new File(homeDir, ".android/avd");
    }

    public static String readStringFromInputString(InputStream is) {
        String s;
        BufferedReader stdError = new BufferedReader(
                new InputStreamReader(is, UTF8));
        StringBuilder errorSb = new StringBuilder();
        try {
            while ((s = stdError.readLine()) != null) {
                errorSb.append(s);
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

    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
    }
}
