package org.rvm.core;


import org.rvm.dto.Container;

import java.util.List;

public interface Trunk {

    void addContainer(Container container);

    List<Container> emptyTrunk();
}
