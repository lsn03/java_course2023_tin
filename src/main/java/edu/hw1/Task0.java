package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task0 {
    private static final Logger LOGGER = LogManager.getLogger();

    public void writeHelloWorld() {
        LOGGER.info("Привет, мир!");
    }
}
