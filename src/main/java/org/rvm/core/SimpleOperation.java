package org.rvm.core;

import org.rvm.dto.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.UnaryOperator;

@Component
public class SimpleOperation implements UnaryOperator<Container> {

    Logger logger = LoggerFactory.getLogger(SimpleOperation.class);

    @Override
    public Container apply(Container container) {
        logger.info("performing simple operation with " + container);
        return container;
    }
}