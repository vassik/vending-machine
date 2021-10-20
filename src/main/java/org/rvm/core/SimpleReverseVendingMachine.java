package org.rvm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@Component
public class SimpleReverseVendingMachine implements ReverseVendingMachine {

    private final UnaryOperator<Pant> action;
    List<Pant> pants;
    Receipt receipt;
    Logger logger = LoggerFactory.getLogger(SimpleReverseVendingMachine.class);

    @Autowired
    public SimpleReverseVendingMachine(UnaryOperator<Pant> action) {
        this.pants = new ArrayList<>();
        this.action = action;
        this.receipt = new Receipt();
    }

    @Override
    public Receipt accept(Pant pant) {
        action.apply(pant);
        pants.add(pant);
        logger.info("just accept a new: " + pant.getClass().getName());
        receipt = testReceipt();
        receipt.commit(pant);
        return receipt;
    }

    @Override
    public Receipt commit() {
        Receipt currentReceipt = new Receipt(testReceipt());
        receipt = null;
        logger.info("collecting receipt: " + currentReceipt);
        return currentReceipt;
    }

    @Override
    public List<Pant> collect() {
        List<Pant> pantToCollect = new ArrayList<>(pants);
        pants.clear();
        logger.info("collected turned in bottles: " + pantToCollect.size());
        return pantToCollect;
    }

    private Receipt testReceipt() {
        return receipt != null ? receipt : new Receipt();
    }
}
