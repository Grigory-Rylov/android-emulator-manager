package com.github.grishberg.androidemulatormanager

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

/**
 * Created by grishberg on 07.12.17.
 */
class CreateAndRunEmulatorsTask extends DefaultTask {
    public static final String NAME = 'createAndRunEmulators'
    AndroidEmulatorManager emulatorManager
    EmulatorManagerConfig extConfig

    @TaskAction
    void runTask() {
        if (extConfig.emulatorArgs == null) {
            throw new GradleException("Need to setup EmulatorManagerConfig extension object")
        }
        emulatorManager.createEmulators(extConfig.emulatorArgs)

        emulatorManager.startEmulators(extConfig.emulatorArgs)

        emulatorManager.waitForEmulatorStarts(extConfig.emulatorArgs, extConfig.waitingTimeout)
    }
}
