package org.rvm.core;

import java.util.HashMap;
import java.util.Map;

import org.rvm.dto.Container;

public class Receipt {

    Map<Class<? extends Container>, Integer> containers;
    Integer total;

    public Receipt() {
        this.containers = new HashMap<>();
        this.total = 0;
    }

    @Override
    public String toString() {
        String stringReceipt = "";
        for (Class key: containers.keySet()) {
            stringReceipt+=String.format("%s x %d; ", key, containers.get(key));
        }
        stringReceipt+=String.format("Total: %d NOK", total);
        return stringReceipt;
    }

    public Receipt(Receipt receipt) {
        this.containers = new HashMap<>(receipt.getContainers());
        this.total = Integer.valueOf(receipt.getTotal());
    }

    public void commit(Container pant) {
        Integer number = containers.getOrDefault(pant.getClass(), 0);
        containers.put(pant.getClass(), ++number);
        total += pant.getValue();
    }

    public Map<Class<? extends Container>, Integer> getContainers() {
        return new HashMap<>(containers);
    }

    public Integer getTotal() {
        return total;
    }
}
