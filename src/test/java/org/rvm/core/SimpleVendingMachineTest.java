package org.rvm.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


class SimpleVendingMachineTest {

    ReverseVendingMachine vendingMachine;

    @BeforeEach
    void setUp() {
        vendingMachine = new SimpleReverseVendingMachine();
    }

    @Test
    void test_vending_machine_empty_at_startup() {
        Assertions.assertThat(vendingMachine.collect()).isEmpty();
    }

    @Test
    void test_vending_machine_accepts_pants() {
        Receipt receipt = vendingMachine.accept(new Bottle());
        Receipt receipt1 = vendingMachine.accept(new Can());
        Assertions.assertThat(receipt).isEqualTo(receipt1);

        vendingMachine.accept(new Can());
        Assertions.assertThat(receipt.getPant().get(Bottle.class)).isEqualTo(1);
        Assertions.assertThat(receipt.getPant().get(Can.class)).isEqualTo(2);

        Receipt receipt2 = vendingMachine.commit();
        Assertions.assertThat(receipt2).isNotNull().isNotEqualTo(receipt);
        Assertions.assertThat(receipt2.getPant().get(Bottle.class)).isEqualTo(1);
        Assertions.assertThat(receipt2.getPant().get(Can.class)).isEqualTo(2);

        Receipt receipt4 = vendingMachine.accept(new Bottle());
        Assertions.assertThat(receipt4).isNotNull().isNotEqualTo(receipt);
        Assertions.assertThat(receipt4.getPant().keySet().size()).isEqualTo(1);

        List<Pant> pants = vendingMachine.collect();
        Assertions.assertThat(pants.size()).isEqualTo(4);
        Assertions.assertThat(pants.stream().filter(pant -> pant instanceof Bottle).count()).isEqualTo(2);
        Assertions.assertThat(pants.stream().filter(pant -> pant instanceof Can).count()).isEqualTo(2);
        Assertions.assertThat(vendingMachine.collect().size()).isEqualTo(0);
    }
}