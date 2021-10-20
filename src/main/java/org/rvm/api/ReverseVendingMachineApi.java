package org.rvm.api;

import org.rvm.core.Receipt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReverseVendingMachineApi {

    @PostMapping
    public Receipt deposite() {
        return null;
    }
}
