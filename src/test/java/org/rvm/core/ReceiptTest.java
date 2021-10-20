package org.rvm.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ReceiptTest {

    List<Pant> pants = Arrays.asList(new Bottle(), new Can(), new Bottle());

    @Test
    void test_receipt_accept() {
        Receipt receipt = new Receipt();
        for (Pant pant : pants) {
            receipt.commit(pant);
        }
        Assertions.assertThat(receipt.getTotal()).isEqualTo(pants.stream().mapToInt(Pant::price).sum());
        Assertions.assertThat(receipt.getPants().size()).isEqualTo(2);
        Assertions.assertThat(receipt.getPants().get(Bottle.class)).isEqualTo(2);
        Assertions.assertThat(receipt.getPants().get(Can.class)).isEqualTo(1);
    }

    @Test
    void test_receipt_is_cloned() {
        Receipt receipt = new Receipt();
        receipt.commit(new Bottle());
        Receipt receipt1 = new Receipt(receipt);
        Assertions.assertThat(receipt.getTotal()).isEqualTo(receipt1.getTotal());
        Assertions.assertThat(receipt.getPants().size()).isEqualTo(receipt1.getPants().size());
        Assertions.assertThat(receipt.getPants().get(Bottle.class)).isEqualTo(receipt1.getPants().get(Bottle.class));
    }

}