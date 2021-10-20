package org.rvm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.UnaryOperator;

@Component
public class SimpleOperation implements UnaryOperator<Pant> {

    Logger logger = LoggerFactory.getLogger(SimpleOperation.class);

    @Override
    public Pant apply(Pant pant) {
        logger.info("performing simple operation with " + pant);
        return pant;
    }
}