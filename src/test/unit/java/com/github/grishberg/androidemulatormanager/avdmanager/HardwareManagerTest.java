package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.DisplayMode;
import com.github.grishberg.androidemulatormanager.EmulatorConfig;
import org.gradle.api.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * Created by grishberg on 18.11.17.
 */
public class HardwareManagerTest {
    private static final String CONFIG_FILE_PATH = "for_test/test.avd/config.ini";
    @Mock
    Logger logger;
    private HardwareManager manager;

    @Before
    public void setUp() throws IOException {
        createIniFile();
        manager = new HardwareManager(new File("for_test"), logger);
    }

    @After
    public void tearDown() throws IOException {
        Files.delete(Paths.get(CONFIG_FILE_PATH));
    }

    @Test
    public void testWriteConfig() {
        EmulatorConfig config = new EmulatorConfig("test", DisplayMode.PHONE_HDPI, 26);
        manager.writeHardwareFile(config);
        assertTrue(new File(CONFIG_FILE_PATH).exists());
    }

    private void createIniFile() throws IOException {
        new File("for_test/test.avd").mkdirs();
        StringBuilder sb = new StringBuilder();
        sb.append("PlayStore.enabled=false\n");
        sb.append("abi.type=x86\n");
        sb.append("avd.ini.encoding=UTF-8\n");
        sb.append("hw.cpu.arch=x86\n");
        sb.append("image.sysdir.1=system-images/android-26/google_apis/x86/\n");
        sb.append("tag.display=Google APIs\n");
        sb.append("tag.id=google_apis\n");
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(CONFIG_FILE_PATH)), "utf-8"))) {
            writer.write(sb.toString());
        }
    }
}