package org.vending.machine.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vending.machine.dao.Receipt;

@RestController
public class VendingMachineApi {

    @PostMapping
    public Receipt deposite() {
        return null;
    }
}
