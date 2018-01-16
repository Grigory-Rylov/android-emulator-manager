[![Build Status](https://travis-ci.org/Grigory-Rylov/android-emulator-manager.svg?branch=master)](https://travis-ci.org/Grigory-Rylov/android-emulator-manager)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=com.github.grishberg:androidemulatormanager)](https://sonarcloud.io/dashboard/index/com.github.grishberg:androidemulatormanager) [![Technical debt ratio](https://sonarcloud.io/api/badges/measure?key=com.github.grishberg:androidemulatormanager&metric=sqale_debt_ratio)](https://sonarcloud.io/dashboard/index/com.github.grishberg:androidemulatormanager)
[![Download](https://api.bintray.com/packages/yelp/grigory-rylov/gradle/android-emulator-manager/images/download.svg)](https://bintray.com/yelp/grigory-rylov/gradle/android-emulator-manager/_latestVersion)
Gradle plugin for creating, deleting, starting and waiting Android emulators (AVD)

Steps:
1) add ```classpath 'com.github.grishberg:android-emulator-manager:0.3.9'```
to projects build.gradle

2) add ```apply plugin: 'com.github.grishberg.androidemulatormanager'
          apply from: "ui-tests.gradle"```
to module build.gradle

3) create ui-tests.gradle in module folder and put:

```
Task createAndRunEmulatorsTask = project.tasks.findByName('createAndRunEmulators')
Task stopEmulatorsTask = project.tasks.findByName("stopEmulators")
stopEmulatorsTask.mustRunAfter "connectedAndroidTest"

// configurations for emulators, you can add several emulators : test_phone, test_tablet, e.t.c.
emulatorConfigs {
    test_phone {
        displayWidth = 768
        displayHeight = 1280
        displayDensity = 320
        apiLevel = 26
        diskSize = 2048
    }
}

emulatorManagerConfig {
    waitingTimeout = 60 * 3 * 1000
}
/**
 * Setup install apk and test apk
 */
def installApkTask = project.tasks.create("installApk") {
    dependsOn('installDebug', 'installDebugAndroidTest')
    finalizedBy(stopEmulatorsTask, "connectedAndroidTest")
    mustRunAfter createAndRunEmulatorsTask
}

/**
 * Starts creating emulators and running instrumental tests.
 */
project.tasks.create("startConnectedTest") {
    finalizedBy createAndRunEmulatorsTask
    finalizedBy installApkTask
    finalizedBy 'assembleDebug'
    finalizedBy 'assembleAndroidTest'
}
```

5) Run with ```./gradlew startConnectedTest```



Copyright 2017 Grigory Rylov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
