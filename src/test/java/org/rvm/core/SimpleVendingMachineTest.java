package org.rvm.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.UnaryOperator;


class SimpleVendingMachineTest {

    ReverseVendingMachine vendingMachine;
    private Integer value;
    UnaryOperator<Pant> action = pant -> {
        value = pant.price();
        return pant;
    };

    @BeforeEach
    void setUp() {
        vendingMachine = new SimpleReverseVendingMachine(action);
    }

    @Test
    void test_vending_machine_empty_at_startup() {
        Assertions.assertThat(vendingMachine.collect()).isEmpty();
    }

    @Test
    void test_action_is_executed() {
        vendingMachine.accept(new Bottle());
        Assertions.assertThat(value).isEqualTo(new Bottle().price());
        vendingMachine.accept(new Can());
        Assertions.assertThat(value).isEqualTo(new Can().price());
    }

    @Test
    void test_vending_machine_accepts_pants() {
        Receipt receipt = vendingMachine.accept(new Bottle());
        Receipt receipt1 = vendingMachine.accept(new Can());
        Assertions.assertThat(receipt).isEqualTo(receipt1);

        vendingMachine.accept(new Can());
        Assertions.assertThat(receipt.getPants().get(Bottle.class)).isEqualTo(1);
        Assertions.assertThat(receipt.getPants().get(Can.class)).isEqualTo(2);

        Receipt receipt2 = vendingMachine.commit();
        Assertions.assertThat(receipt2).isNotNull().isNotEqualTo(receipt);
        Assertions.assertThat(receipt2.getPants().get(Bottle.class)).isEqualTo(1);
        Assertions.assertThat(receipt2.getPants().get(Can.class)).isEqualTo(2);

        Receipt receipt4 = vendingMachine.accept(new Bottle());
        Assertions.assertThat(receipt4).isNotNull().isNotEqualTo(receipt);
        Assertions.assertThat(receipt4.getPants().keySet().size()).isEqualTo(1);

        List<Pant> pants = vendingMachine.collect();
        Assertions.assertThat(pants.size()).isEqualTo(4);
        Assertions.assertThat(pants.stream().filter(pant -> pant instanceof Bottle).count()).isEqualTo(2);
        Assertions.assertThat(pants.stream().filter(pant -> pant instanceof Can).count()).isEqualTo(2);
        Assertions.assertThat(vendingMachine.collect().size()).isEqualTo(0);
    }

    @Test
    void test_zero_reciept_is_returned_if_not_deposited() {
        Receipt receipt = vendingMachine.commit();
        Assertions.assertThat(receipt).isNotNull();
        Assertions.assertThat(receipt.getPants().size()).isEqualTo(0);
        Assertions.assertThat(receipt.getTotal()).isEqualTo(0);
    }

    @Test
    void test_zero_receipt_is_returned_if_receipt_collected() {
        vendingMachine.accept(new Bottle());
        vendingMachine.commit();
        Receipt receipt = vendingMachine.commit();
        Assertions.assertThat(receipt).isNotNull();
        Assertions.assertThat(receipt.getPants().size()).isEqualTo(0);
        Assertions.assertThat(receipt.getTotal()).isEqualTo(0);
    }

    @Test
    void test_no_bottles_is_returned_if_not_deposited() {
        List<Pant> pant = vendingMachine.collect();
        Assertions.assertThat(pant).isNotNull().isEmpty();
    }
}