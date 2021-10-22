package org.rvm.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rvm.dto.Bottle;
import org.rvm.dto.Can;
import org.rvm.core.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public void test_rvm_can_issue_reciept() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/receipt").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.containers").isEmpty())
                .andExpect(jsonPath("$.total").value(0));
    }

    @Test
    public void test_rvm_gives_you_receipt_for_collected_containers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/deposit")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(bottle).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/receipt").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Receipt receipt = objectMapper.readValue(jsonString, new TypeReference<Receipt>() { });
        Assertions.assertThat(receipt.getTotal()).isEqualTo(new Bottle().getValue());
        Assertions.assertThat(receipt.getContainers().get(Bottle.class)).isEqualTo(1);
    }

    @Test
    public void test_rvm_can_be_collected() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/collect").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }

    @Test
    public void test_rvm_can_collect_containers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/deposit")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(bottle).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/collect").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray()).andReturn();
        String jsonArrayString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Bottle> bottleList = objectMapper.readValue(jsonArrayString, new TypeReference<List<Bottle>>() { });
        Assertions.assertThat(bottleList.size()).isEqualTo(1);
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