package org.vending.machine.dao;

import java.util.HashMap;
import java.util.Map;

import org.vending.machine.dao.Pant;

public class Receipt {

    public Map<Pant, Integer> getPant() {
        return new HashMap<>();
    }

    public Integer getTotal() {
        return 0;
    }
}
