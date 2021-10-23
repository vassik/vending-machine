package org.rvm.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rvm.core.ContainerCashier;
import org.rvm.core.Trunk;
import org.rvm.dto.Bottle;
import org.rvm.dto.Can;
import org.rvm.dto.Container;
import org.rvm.dto.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
class ReverseVendingMachineApiTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ContainerCashier containerCashier;
    @Autowired
    private Trunk trunk;

    private final String bottle = "{\"type\": \"bottle\"}";
    private final String can = "{\"type\": \"can\"}";
    private final String canDifferent = "{\"type\": \"can\", \"value\" : 6}";
    private final Integer bottlePrice = new Bottle().getValue();
    private final Integer canPrice = new Can().getValue();

    @BeforeEach
    public void setUp() {
        containerCashier.commit();
        trunk.emptyTrunk();
    }

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
        Receipt receipt = objectMapper.readValue(jsonString, new TypeReference<Receipt>() {
        });
        Assertions.assertThat(receipt.getTotal()).isEqualTo(bottlePrice);
        Assertions.assertThat(receipt.getContainers().get(Container.Type.BOTTLE)).isEqualTo(1);
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
        List<Bottle> bottleList = objectMapper.readValue(jsonArrayString, new TypeReference<List<Bottle>>() {
        });
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