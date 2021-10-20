package org.rvm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class SimpleReverseVendingMachine implements ReverseVendingMachine {

    private final UnaryOperator<Pant> action;
    List<Pant> pants;
    Receipt receipt;
    Logger logger = LoggerFactory.getLogger(SimpleReverseVendingMachine.class);
    
    public SimpleReverseVendingMachine(UnaryOperator<Pant> action) {
        this.pants = new ArrayList<>();
        this.action = action;
    }

    @Override
    public Receipt accept(Pant pant) {
        action.apply(pant);
        pants.add(pant);
        logger.info("just accept a new: " + pant.getClass().getName());
        if (receipt == null) {
            receipt = new Receipt();
        }
        receipt.commit(pant);
        return receipt;
    }

    @Override
    public Receipt commit() {
        logger.info("collecting receipt: " + receipt);
        Receipt currentReceipt = new Receipt(receipt);
        receipt = null;
        return currentReceipt;
    }

    @Override
    public List<Pant> collect() {
        List<Pant> pantToCollect = new ArrayList<>(pants);
        pants.clear();
        return pantToCollect;
    }
}
