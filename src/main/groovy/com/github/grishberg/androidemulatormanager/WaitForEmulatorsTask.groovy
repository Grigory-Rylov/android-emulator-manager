package com.github.grishberg.androidemulatormanager

import com.github.grishberg.androidemulatormanager.ext.EmulatorManagerConfig
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.TaskAction

/**
 * Created by grishberg on 19.11.17.
 */
class WaitForEmulatorsTask extends DefaultTask {
    public static final String NAME = "waitForEmulators"
    AndroidEmulatorManager emulatorManager

    @TaskAction
    void runTask() {
        def emulatorConfigs = project.extensions.getByName(EmulatorManagerPlugin.EMULATOR_CONFIGS) as NamedDomainObjectContainer<EmulatorConfig>
        EmulatorManagerConfig extConfig = project.extensions.getByType(EmulatorManagerConfig)

        if (emulatorConfigs.size() == 0) {
            throw new GradleException("Need to setup 'emulatorConfigs' extension")
        }
        emulatorManager.initIfNeeded()

        emulatorManager.waitForEmulatorStarts(emulatorConfigs.size(), extConfig.waitingTimeout)
    }
}
