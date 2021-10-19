package org.vending.machine.core;

import java.util.List;

public interface VendingMachine {

    Receipt accept(Pant pant);

    Receipt commit();

    List<Pant> collect();
}
