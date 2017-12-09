package com.github.grishberg.androidemulatormanager

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

/**
 * Created by grishberg on 09.12.17.
 */
class StopAndDeleteEmulatorsTask extends DefaultTask {
    public static final String NAME = "stopAndDeleteEmulators"
    AndroidEmulatorManager emulatorManager
    EmulatorManagerConfig extConfig

    @TaskAction
    void runTask() {
        if (extConfig.emulatorArgs == null) {
            throw new GradleException("Need to setup EmulatorManagerConfig extension object")
        }

        emulatorManager.stopRunningEmulators()

        emulatorManager.deleteEmulators(extConfig.emulatorArgs)

    }
}
