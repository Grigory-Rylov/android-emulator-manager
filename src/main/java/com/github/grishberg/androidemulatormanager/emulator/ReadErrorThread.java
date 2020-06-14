package com.github.grishberg.androidemulatormanager.emulator;

import com.github.grishberg.androidemulatormanager.utils.Logger;
import com.github.grishberg.androidemulatormanager.utils.SysUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by grishberg on 21.01.18.
 */
public class ReadErrorThread extends Thread {
    private String name;
    private final Logger logger;
    private final InputStream errorStream;

    public ReadErrorThread(String name, InputStream errorStream, Logger logger) {
        this.name = name;
        this.logger = logger;
        this.errorStream = errorStream;
    }

    @Override
    public void run() {
        logger.info("Emulator: begin startReadErrorThread [{}]", name);
        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(errorStream, SysUtils.UTF8));
        String errorLine;
        try {
            while (!Thread.interrupted() && (errorLine = stdError.readLine()) != null) {
                logger.error("Emulator error [{}]: {}", name, errorLine);
            }
        } catch (IOException e) {
            logger.error("Emulator IOException", e);
        }
        logger.info("Emulator: end startReadErrorThread [{}]", name);
        IOUtils.closeQuietly(stdError);
    }
}
