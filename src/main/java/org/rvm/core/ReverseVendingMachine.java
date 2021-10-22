package org.rvm.core;

import java.util.List;

import org.rvm.dto.Container;

public interface ReverseVendingMachine {

    Receipt accept(Container container);

    Receipt commit();

    List<Container> collect();
}
