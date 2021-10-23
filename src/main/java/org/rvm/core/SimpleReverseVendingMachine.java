package org.rvm.core;

import org.rvm.dto.Container;
import org.rvm.dto.Receipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.UnaryOperator;

@Component
public class SimpleReverseVendingMachine implements ReverseVendingMachine {

    private final UnaryOperator<Container> action;
    private final Trunk trunk;
    private final ContainerCashier containerCashier;
    Logger logger = LoggerFactory.getLogger(SimpleReverseVendingMachine.class);

    @Autowired
    public SimpleReverseVendingMachine(UnaryOperator<Container> action, ContainerCashier containerCashier, Trunk trunk) {
        this.trunk = trunk;
        this.action = action;
        this.containerCashier = containerCashier;
    }

    @Override
    public Receipt accept(Container container) {
        action.apply(container);
        trunk.addContainer(container);
        logger.info("just accept a new: " + container.getClass().getName());
        return containerCashier.addContainer(container);
    }

    @Override
    public Receipt commit() {
        Receipt currentReceipt = containerCashier.commit();
        logger.info("collecting receipt: " + currentReceipt);
        return currentReceipt;
    }

    @Override
    public List<Container> collect() {
        List<Container> pantToCollect = trunk.emptyTrunk();
        logger.info("collected turned in bottles: " + pantToCollect.size());
        return pantToCollect;
    }
}
