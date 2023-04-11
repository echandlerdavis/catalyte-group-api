package io.catalyte.training.sportsproducts.domains.promotions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PromotionalCodeController.class)
public class PromotionsAPITest {

    @MockBean
    private PromotionalCodeService promotionalCodeService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createPromotionalCode_ShouldReturn201Status_WhenGivenValidInput() throws Exception {
        PromotionalCode promotionalCode = new PromotionalCode();
        promotionalCode.setTitle("My Promo Code");
        promotionalCode.setDescription("This is a test promo code.");
        promotionalCode.setType(PromotionalCodeType.PERCENT);
        promotionalCode.setRate(new BigDecimal("10"));

        when(promotionalCodeService.createPromotionalCode(any())).thenReturn(promotionalCode);

        mockMvc.perform(post("/promotional-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"My Promo Code\", \"description\": \"This is a test promo code.\", \"type\": \"PERCENT\", \"rate\": 10}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createPromotionalCode_ShouldReturn400Status_WhenGivenInvalidInput() throws Exception {
        when(promotionalCodeService.createPromotionalCode(any())).thenReturn(null);

        mockMvc.perform(post("/promotional-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\", \"description\": \"\", \"type\": null, \"rate\": null}"))
                .andExpect(status().isBadRequest());
    }
}



