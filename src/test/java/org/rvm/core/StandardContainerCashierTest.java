package org.rvm.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rvm.dto.Bottle;
import org.rvm.dto.Can;
import org.rvm.dto.Container;
import org.rvm.dto.Receipt;

import java.util.Arrays;
import java.util.List;

class StandardContainerCashierTest {

    StandardContainerCashier standardContainerCashier;

    List<Container> pants = Arrays.asList(new Bottle(), new Can(), new Bottle());

    @BeforeEach
    void setUp() {
        standardContainerCashier = new StandardContainerCashier();
    }

    @Test
    void test_cashier_accept_containers() {
        pants.forEach(container -> standardContainerCashier.addContainer(container));
        Receipt receipt = standardContainerCashier.commit();
        Assertions.assertThat(receipt.getTotal()).isEqualTo(pants.stream().mapToInt(Container::getValue).sum());
        Assertions.assertThat(receipt.getContainers().size()).isEqualTo(2);
        Assertions.assertThat(receipt.getContainers().get(Container.Type.BOTTLE)).isEqualTo(2);
        Assertions.assertThat(receipt.getContainers().get(Container.Type.CAN)).isEqualTo(1);
    }

    @Test
    void test_receipt_is_build_iteratively() {
        Receipt receipt = standardContainerCashier.addContainer(new Bottle());
        Receipt receipt1 = standardContainerCashier.addContainer(new Can());
        Assertions.assertThat(receipt).isNotEqualTo(receipt1);
        Assertions.assertThat(receipt.getContainers().get(Container.Type.BOTTLE)).isEqualTo(1);
        Assertions.assertThat(receipt.getContainers().get(Container.Type.CAN)).isNull();
        Assertions.assertThat(receipt.getTotal()).isEqualTo(new Bottle().getValue());
        Assertions.assertThat(receipt1.getContainers().size()).isEqualTo(2);
        Assertions.assertThat(receipt1.getContainers().get(Container.Type.BOTTLE)).isEqualTo(1);
        Assertions.assertThat(receipt1.getContainers().get(Container.Type.CAN)).isEqualTo(1);
        Assertions.assertThat(receipt1.getTotal()).isEqualTo(new Bottle().getValue() + new Can().getValue());
    }

    @Test
    void test_receipt_is_correct_for_each_commit() {
        pants.forEach(container -> standardContainerCashier.addContainer(container));
        Receipt receipt = standardContainerCashier.commit();
        int expectedTotal = pants.stream().mapToInt(Container::getValue).sum();
        Assertions.assertThat(receipt.getTotal()).isEqualTo(expectedTotal);
        Assertions.assertThat(receipt.getContainers().size()).isEqualTo(2);

        standardContainerCashier.addContainer(new Bottle());
        receipt = standardContainerCashier.commit();
        Assertions.assertThat(receipt.getTotal()).isEqualTo(new Bottle().getValue());
        Assertions.assertThat(receipt.getContainers().size()).isEqualTo(1);
    }

}