package com.github.grishberg.androidemulatormanager

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

/**
 * Task creates and starts emulators.
 */
class CreateAndRunEmulatorsTask extends DefaultTask {
    public static final String NAME = 'createAndRunEmulators'
    AndroidEmulatorManager emulatorManager
    EmulatorManagerConfig extConfig

    @TaskAction
    void runTask() {
        //TODO: make CLI parser that creates array of EmulatorConfig
        initCli()
        if (extConfig.emulatorArgs == null) {
            throw new GradleException("Need to setup EmulatorManagerConfig extension object")
        }
        emulatorManager.initIfNeeded()

        emulatorManager.createEmulators(extConfig.emulatorArgs)

        emulatorManager.startEmulators(extConfig.emulatorArgs)

        emulatorManager.waitForEmulatorStarts(extConfig.emulatorArgs, extConfig.waitingTimeout)
    }

    private void initCli() {
        if (project.hasProperty('api') && extConfig.emulatorArgs == null) {
            int apiLevel = Integer.parseInt(project.property('api') as String)
            EmulatorConfig defaultConfig = new EmulatorConfig("phone_avd",
                    DisplayMode.PHONE_HDPI, apiLevel)
            extConfig.emulatorArgs = [defaultConfig]
        }
    }
}
