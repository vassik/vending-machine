package org.vending.machine.core;

import java.util.HashMap;
import java.util.Map;

public class Receipt {

    Map<Class<? extends Pant>, Integer> pants;
    Integer total;

    public Receipt() {
        this.pants = new HashMap<>();
        this.total = 0;
    }

    public Receipt(Receipt receipt) {
        this.pants = new HashMap<>(receipt.getPant());
        this.total = Integer.valueOf(receipt.getTotal());
    }

    public void commit(Pant pant) {
        Integer number = pants.getOrDefault(pant.getClass(), 0);
        pants.put(pant.getClass(), ++number);
        total += pant.price();
    }

    public Map<Class<? extends Pant>, Integer> getPant() {
        return new HashMap<>(pants);
    }

    public Integer getTotal() {
        return total;
    }
}
