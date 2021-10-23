package org.rvm.core;

import org.rvm.config.RvmConfigProperties;
import org.rvm.dto.Bottle;
import org.rvm.dto.Can;
import org.rvm.dto.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.UnaryOperator;

@Component
public class ProcessingOperation implements UnaryOperator<Container> {

    private final RvmConfigProperties configProperties;
    Logger logger = LoggerFactory.getLogger(ProcessingOperation.class);

    @Autowired
    public ProcessingOperation(RvmConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Override
    public Container apply(Container container) {
        logger.info("performing simple operation with " + container);
        if (configProperties.isIgnoreProcessSpeed()) {
            logger.warn("ignoring proccessing speed");
            return container;
        }
        try {
            if (container instanceof Bottle) {
                Thread.sleep(configProperties.getBottleProcessSpeed());
            } else if (container instanceof Can) {
                Thread.sleep(configProperties.getCanProcessSpeed());
            } else {
                Thread.sleep(configProperties.getDefaultProcessSpeed());
            }
        } catch (InterruptedException e) {
            logger.error("cannot sleep for some reasons", e);
        }
        return container;
    }
}