package org.rvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("org.rvm.config")
public class ReverseVendingMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReverseVendingMachineApplication.class, args);
    }

}
