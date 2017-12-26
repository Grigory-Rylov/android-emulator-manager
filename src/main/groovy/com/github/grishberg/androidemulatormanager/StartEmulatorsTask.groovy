package com.github.grishberg.androidemulatormanager

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

/**
 * Created by grishberg on 19.11.17.
 */
class StartEmulatorsTask extends DefaultTask {
    public static final String NAME = 'startEmulators'
    AndroidEmulatorManager emulatorManager
    EmulatorManagerConfig extConfig

    @TaskAction
    void runTask() {
        if (extConfig.emulatorArgs == null) {
            throw new GradleException("Need to setup EmulatorManagerConfig extension object")
        }

        emulatorManager.startEmulators(extConfig.emulatorArgs)
    }
}
