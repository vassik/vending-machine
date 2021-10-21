package org.rvm.core;

import java.util.List;

public interface ReverseVendingMachine {

    Receipt accept(Container container);

    Receipt commit();

    List<Container> collect();
}
