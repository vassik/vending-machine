package org.rvm.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rvm.dto.Bottle;
import org.rvm.dto.Can;
import org.rvm.dto.Container;

import java.util.Arrays;
import java.util.List;

class ReceiptTest {

    List<Container> pants = Arrays.asList(new Bottle(), new Can(), new Bottle());

    @Test
    void test_receipt_accept() {
        Receipt receipt = new Receipt();
        for (Container pant : pants) {
            receipt.commit(pant);
        }
        Assertions.assertThat(receipt.getTotal()).isEqualTo(pants.stream().mapToInt(Container::getValue).sum());
        Assertions.assertThat(receipt.getContainers().size()).isEqualTo(2);
        Assertions.assertThat(receipt.getContainers().get(Bottle.class)).isEqualTo(2);
        Assertions.assertThat(receipt.getContainers().get(Can.class)).isEqualTo(1);
    }

    @Test
    void test_receipt_is_cloned() {
        Receipt receipt = new Receipt();
        receipt.commit(new Bottle());
        Receipt receipt1 = new Receipt(receipt);
        Assertions.assertThat(receipt.getTotal()).isEqualTo(receipt1.getTotal());
        Assertions.assertThat(receipt.getContainers().size()).isEqualTo(receipt1.getContainers().size());
        Assertions.assertThat(receipt.getContainers().get(Bottle.class)).isEqualTo(receipt1.getContainers().get(Bottle.class));
    }

}