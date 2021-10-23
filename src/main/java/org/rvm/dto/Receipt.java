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
