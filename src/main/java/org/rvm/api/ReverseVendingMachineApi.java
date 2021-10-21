package org.rvm.api;

import org.rvm.core.Container;
import org.rvm.core.Receipt;
import org.rvm.core.ReverseVendingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReverseVendingMachineApi {

    @Autowired
    ReverseVendingMachine reverseVendingMachine;

    @PostMapping(
            value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Receipt deposite(@RequestBody Container container) {
        return reverseVendingMachine.accept(container);
    }

    @GetMapping(value = "/receipt", produces = MediaType.APPLICATION_JSON_VALUE)
    public Receipt receipt() {
        return reverseVendingMachine.commit();
    }

    @GetMapping(value = "/collect", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Container> collect() {
        return reverseVendingMachine.collect();
    }
}
