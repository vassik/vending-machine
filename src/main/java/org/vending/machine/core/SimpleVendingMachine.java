package org.vending.machine.core;

import java.util.ArrayList;
import java.util.List;

public class SimpleVendingMachine implements VendingMachine {

    List<Pant> pants;
    Receipt receipt;

    public SimpleVendingMachine() {
        this.pants = new ArrayList<>();
    }

    @Override
    public Receipt accept(Pant pant) {
        pants.add(pant);
        if (receipt == null) {
            receipt = new Receipt();
        }
        receipt.commit(pant);
        return receipt;
    }

    @Override
    public Receipt commit() {
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
