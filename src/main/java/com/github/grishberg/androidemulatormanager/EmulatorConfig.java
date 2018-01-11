package com.github.grishberg.androidemulatormanager;

/**
 * Created by grishberg on 12.11.17.
 */
public class EmulatorConfig {
    private String name;
    private int apiLevel;
    private boolean withPlayStore;
    private int diskSize = 800;
    private int sdCardSize = 100;
    private int displayWidth;
    private int displayHeight;
    private int displayDensity;

    public static EmulatorConfig argsFromJson(String argsAsJson) {
        //TODO: extract parameters from json
        EmulatorConfig args = new EmulatorConfig("phone", DisplayMode.getPhoneHdpi(), 26);
        return args;
    }

    public EmulatorConfig(String name) {
        this.name = name;
    }

    public EmulatorConfig(String name, DisplayMode displayMode, int apiLevel) {
        this.name = name;
        displayWidth = displayMode.getWidth();
        displayHeight = displayMode.getHeight();
        displayDensity = displayMode.getDensity();
        this.apiLevel = apiLevel;
    }

    public String getName() {
        return name;
    }

    public DisplayMode getDisplayMode() {
        return new DisplayMode(displayWidth, displayHeight, displayDensity);
    }

    public int getApiLevel() {
        return apiLevel;
    }

    public void setWithPlayStore(boolean withPlayStore) {
        this.withPlayStore = withPlayStore;
    }

    public boolean isWithPlaystore() {
        return withPlayStore;
    }

    public int getSdCardSize() {
        return sdCardSize;
    }

    public void setSdCardSize(int sdCardSize) {
        this.sdCardSize = sdCardSize;
    }

    public int getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(int diskSize) {
        this.diskSize = diskSize;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public int getDisplayDensity() {
        return displayDensity;
    }

    public void setDisplayDensity(int displayDensity) {
        this.displayDensity = displayDensity;
    }
}
