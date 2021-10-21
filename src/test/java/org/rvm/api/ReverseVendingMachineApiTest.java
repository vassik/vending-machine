package org.rvm.api;

import org.junit.jupiter.api.Test;
import org.rvm.core.Bottle;
import org.rvm.core.Can;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ReverseVendingMachineApiTest {

    @Autowired
    private MockMvc mvc;

    private final String bottle = "{\"type\": \"bottle\"}";
    private final String can = "{\"type\": \"can\"}";
    private final String canDifferent = "{\"type\": \"can\", \"value\" : 6}";
    private final Integer bottlePrice = new Bottle().getValue();
    private final Integer canPrice = new Can().getValue();

    @Test
    public void test_rvm_gives_you_reciept() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/receipt").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.containers").isEmpty())
                .andExpect(jsonPath("$.total").value(0));
    }

    @Test
    public void test_rvm_can_be_collected() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/collect").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }

    @Test
    public void test_rvm_accepts_containers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/deposit").
                        contentType(MediaType.APPLICATION_JSON_VALUE).content(bottle)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.containers").exists())
                .andExpect(jsonPath("$.total").value(bottlePrice));

        mvc.perform(MockMvcRequestBuilders.post("/deposit").
                        contentType(MediaType.APPLICATION_JSON_VALUE).content(can)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.containers").exists())
                .andExpect(jsonPath("$.total").value(bottlePrice + canPrice));

        mvc.perform(MockMvcRequestBuilders.post("/deposit").
                        contentType(MediaType.APPLICATION_JSON_VALUE).content(canDifferent)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.containers").exists())
                .andExpect(jsonPath("$.total").value(bottlePrice + canPrice + 6));
    }
}