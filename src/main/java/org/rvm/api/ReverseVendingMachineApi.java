package org.rvm.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.rvm.dto.Container;
import org.rvm.dto.Receipt;
import org.rvm.core.ReverseVendingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Reverse Vending Machine (RVM)")
public class ReverseVendingMachineApi {

    @Autowired
    ReverseVendingMachine reverseVendingMachine;

    @Operation(summary = "Deposites a container")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Receipt.class))})
            })
    @PostMapping(
            value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Receipt deposite(@RequestBody Container container) {
        return reverseVendingMachine.accept(container);
    }

    @Operation(summary = "Gets a receipt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Receipt.class))})
    })
    @GetMapping(value = "/receipt", produces = MediaType.APPLICATION_JSON_VALUE)
    public Receipt receipt() {
        return reverseVendingMachine.commit();
    }

    @Operation(summary = "Collects all deposited bottles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Receipt.class)))})
    })
    @GetMapping(value = "/collect", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Container> collect() {
        return reverseVendingMachine.collect();
    }
}
