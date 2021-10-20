package org.rvm.api;

import org.rvm.core.Pant;
import org.rvm.core.Receipt;
import org.rvm.core.ReverseVendingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReverseVendingMachineApi {

    @Autowired
    ReverseVendingMachine reverseVendingMachine;

    @PostMapping("/deposit")
    public Receipt deposite(Pant pant) {
        return reverseVendingMachine.accept(pant);
    }

    @GetMapping("/receipt")
    public Receipt receipt() {
        return reverseVendingMachine.commit();
    }

    @GetMapping("/collect")
    public List<Pant> collect() {
        return reverseVendingMachine.collect();
    }
}
