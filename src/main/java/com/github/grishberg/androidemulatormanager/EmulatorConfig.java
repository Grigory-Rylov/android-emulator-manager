package com.github.grishberg.androidemulatormanager;

/**
 * Created by grishberg on 12.11.17.
 */
public class EmulatorConfig {
    private String name;
    private DisplayMode displayMode;
    private int apiLevel;
    private boolean withPlayStore;

    public static EmulatorConfig argsFromJson(String argsAsJson) {
        //TODO: extract parameters from json
        EmulatorConfig args = new EmulatorConfig("phone", DisplayMode.PHONE_HDPI, 26);
        return args;
    }

    public EmulatorConfig(String name, DisplayMode displayMode, int apiLevel) {
        this.name = name;
        this.displayMode = displayMode;
        this.apiLevel = apiLevel;
    }

    public String getName() {
        return name;
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
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
}
