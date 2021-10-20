package org.rvm.core;

import java.util.List;

public interface ReverseVendingMachine {

    Receipt accept(Pant pant);

    Receipt commit();

    List<Pant> collect();
}
