package org.rvm.core;

import org.rvm.dto.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@Component
public class SimpleReverseVendingMachine implements ReverseVendingMachine {

    private final UnaryOperator<Container> action;
    private final Trunk trunk;
    private final Receipt receipt;
    Logger logger = LoggerFactory.getLogger(SimpleReverseVendingMachine.class);

    @Autowired
    public SimpleReverseVendingMachine(UnaryOperator<Container> action, Receipt receipt, Trunk trunk) {
        this.trunk = trunk;
        this.action = action;
        this.receipt = receipt;
    }

    @Override
    public Receipt accept(Container container) {
        action.apply(container);
        trunk.addContainer(container);
        logger.info("just accept a new: " + container.getClass().getName());
        receipt.commit(container);
        return new Receipt(receipt);
    }

    @Override
    public Receipt commit() {
        Receipt currentReceipt = new Receipt(receipt);
        receipt.reset();
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
