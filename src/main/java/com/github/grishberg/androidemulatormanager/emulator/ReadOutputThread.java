package com.github.grishberg.androidemulatormanager.emulator;

import com.github.grishberg.androidemulatormanager.utils.SysUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by grishberg on 21.01.18.
 */
public class ReadOutputThread extends Thread {
    private final InputStream inputStream;
    private String name;
    private final Logger logger;

    public ReadOutputThread(String name, Process process, Logger logger) {
        this.name = name;
        this.logger = logger;
        inputStream = process.getInputStream();
    }

    @Override
    public void run() {
        String line;
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(inputStream, SysUtils.UTF8));

        try {
            while ((line = stdInput.readLine()) != null) {
                logger.info("ReadOutputThread: [{}] {} ", name, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(inputStream);
    }
}
