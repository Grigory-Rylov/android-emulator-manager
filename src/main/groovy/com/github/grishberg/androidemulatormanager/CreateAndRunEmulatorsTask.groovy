package com.github.grishberg.androidemulatormanager

import com.github.grishberg.androidemulatormanager.ext.EmulatorManagerConfig
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.TaskAction

/**
 * Task creates and starts emulators.
 */
class CreateAndRunEmulatorsTask extends DefaultTask {
    public static final String NAME = 'createAndRunEmulators'
    AndroidEmulatorManager emulatorManager

    @TaskAction
    void runTask() {
        initCli()

        def emulatorConfigs = project.extensions.getByName(EmulatorManagerPlugin.EMULATOR_CONFIGS) as NamedDomainObjectContainer<EmulatorConfig>
        EmulatorManagerConfig extConfig = project.extensions.getByType(EmulatorManagerConfig)

        if (emulatorConfigs.size() == 0) {
            throw new GradleException("Need to setup 'emulatorConfigs' extension")
        }
        emulatorManager.initIfNeeded()

        emulatorManager.createEmulators(emulatorConfigs.asList(), extConfig.installSystemImageIfNotExists)

        emulatorManager.startEmulators(emulatorConfigs.asList())

        emulatorManager.waitForEmulatorStarts(emulatorConfigs.asList(), extConfig.waitingTimeout)
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
