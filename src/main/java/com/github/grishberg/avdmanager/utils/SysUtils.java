package com.github.grishberg.avdmanager.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by grishberg on 12.11.17.
 */
public class SysUtils {
    public static File getAvdHomeDir() {
        String homeDir = System.getenv("HOME");
        return new File(homeDir, ".android/avd");
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
