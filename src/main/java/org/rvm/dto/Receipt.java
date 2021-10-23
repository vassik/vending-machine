package org.rvm.dto;

import java.util.HashMap;
import java.util.Map;

public class Receipt {

    private final Integer total;
    private final Map<Container.Type, Integer> containers;

    public Receipt() {
        this.containers = new HashMap<>();
        this.total = 0;
    }

    @Override
    public String toString() {
        String stringReceipt = "";
        for (Container.Type key : containers.keySet()) {
            stringReceipt += String.format("%s X %d,", key, containers.get(key));
        }
        stringReceipt += String.format("total: %d NOK", total);
        return stringReceipt;
    }

    public Receipt(Map<Container.Type, Integer> containers, Integer total) {
        this.containers = containers;
        this.total = total;
    }

    public Map<Container.Type, Integer> getContainers() {
        return new HashMap<>(containers);
    }

    public Integer getTotal() {
        return total;
    }
}
