package com.github.grishberg.avdmanager.utils;

/**
 * Created by grishberg on 12.11.17.
 */
public abstract class AbsProvider {
    private static final String OS = System.getProperty("os.name").toLowerCase();

    protected static boolean isWindows() {

        return (OS.contains("win"));
    }

    protected static boolean isMac() {

        return (OS.contains("mac"));
    }

    protected static boolean isUnix() {

        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    protected static boolean isSolaris() {

        return (OS.contains("sunos"));
    }
}
