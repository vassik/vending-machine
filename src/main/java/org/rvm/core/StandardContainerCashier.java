package org.rvm.core;

import org.rvm.dto.Container;
import org.rvm.dto.Receipt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StandardContainerCashier implements ContainerCashier {

    private final List<Container> containerList = new ArrayList<>();

    @Override
    public Receipt addContainer(Container container) {
        containerList.add(container);
        return new Receipt(countContainersByType(), calculateTotal());
    }

    @Override
    public Receipt commit() {
        Receipt receipt = new Receipt(countContainersByType(), calculateTotal());
        containerList.clear();
        return receipt;
    }

    private Map<Container.Type, Integer> countContainersByType() {
        Map<Container.Type, Integer> containerTotalByType = new HashMap<>();
        Map<Container.Type, List<Container>> groupedContainers = containerList.stream().collect(
                Collectors.groupingBy(container -> Container.Type.typeByClass(container.getClass())));
        groupedContainers.keySet().forEach(key -> containerTotalByType.put(key, groupedContainers.get(key).size()));
        return containerTotalByType;
    }

    private Integer calculateTotal() {
        return containerList.stream().mapToInt(Container::getValue).sum();
    }
}
