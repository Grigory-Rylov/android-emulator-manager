package com.github.grishberg.androidemulatormanager;

import com.github.grishberg.androidemulatormanager.ext.EmulatorManagerConfig;
import org.gradle.api.Project;
import org.junit.Test;

/**
 * Created by grishberg on 19.11.17.
 */
public class EmulatorManagerPluginIntegrationTest extends BaseTestCaseWithLogger {
    private Project project;
    private EmulatorConfig argPhone = new EmulatorConfig("test_phone",
            DisplayMode.PHONE_HDPI, 26);
    private EmulatorConfig argTablet = new EmulatorConfig("test_tablet",
            DisplayMode.TABLET_XHDPI, 26);

    public EmulatorManagerPluginIntegrationTest() {
        super();
        project = getProject();
        project.getPluginManager().apply(EmulatorManagerPlugin.class);

        EmulatorManagerConfig ext = project.getExtensions().findByType(EmulatorManagerConfig.class);
        EmulatorConfig[] args = {argPhone, argTablet};
        ext.setEmulatorArgs(args);
        ext.setWaitingTimeout(60 * 1000);
    }

    @Test
    public void executeTasks() {

        CreateEmulatorsTask createTask = (CreateEmulatorsTask) project.getTasks()
                .getByName("createEmulators");

        StartEmulatorsTask startEmulatorsTask = (StartEmulatorsTask) project.getTasks()
                .getByName("startEmulators");

        WaitForEmulatorsTask waitForEmulatorsTask = (WaitForEmulatorsTask) project.getTasks()
                .getByName("waitForEmulators");

        StopEmulatorsTask stopEmulatorsTask = (StopEmulatorsTask) project.getTasks()
                .getByName("stopRunningEmulators");

        DeleteEmulatorsTask deleteTasks = (DeleteEmulatorsTask) project.getTasks()
                .getByName("deleteEmulators");

        createTask.runTask();
        startEmulatorsTask.runTask();
        waitForEmulatorsTask.runTask();
        stopEmulatorsTask.runTask();
        deleteTasks.runTask();
    }
}
