package com.github.grishberg.androidemulatormanager

import com.github.grishberg.androidemulatormanager.ext.EmulatorManagerConfig
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.TaskAction

/**
 * Created by grishberg on 19.11.17.
 */
class CreateEmulatorsTask extends DefaultTask {
    public static final String NAME = "createEmulators"
    AndroidEmulatorManager emulatorManager

    @TaskAction
    void runTask() {
        def emulatorConfigs = project.extensions.getByName(EmulatorManagerPlugin.EMULATOR_CONFIGS) as NamedDomainObjectContainer<EmulatorConfig>
        def config = project.extensions.getByName(EmulatorManagerPlugin.CONFIG_NAME) as EmulatorManagerConfig

        if (emulatorConfigs.size() == 0) {
            throw new GradleException("Need to setup 'emulatorConfigs' extension")
        }
        emulatorManager.initIfNeeded()

        emulatorManager.createEmulators(emulatorConfigs.asList(), config.installSystemImageIfNotExists)
    }
}
