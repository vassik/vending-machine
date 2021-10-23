package org.rvm.core;

import org.rvm.dto.Container;
import org.rvm.dto.Receipt;

public interface ContainerCashier {

    Receipt addContainer(Container container);

    Receipt commit();
}
