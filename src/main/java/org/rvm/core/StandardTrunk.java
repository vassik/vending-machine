package org.rvm.core;

import org.rvm.dto.Container;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StandardTrunk implements Trunk {

    private final List<Container> containers = new ArrayList<>();

    @Override
    public void addContainer(Container container) {
        containers.add(container);
    }

    @Override
    public List<Container> emptyTrunk() {
        List<Container> containersToEmpty = new ArrayList<>(containers);
        containers.clear();
        return containersToEmpty;
    }
}
