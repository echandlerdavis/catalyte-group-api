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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link PromotionalCodeController}.
 */
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

    /**
     * Tests the {@link PromotionalCodeController#createPromotionalCode(PromotionalCodeDTO)} endpoint
     * for a valid promotional code.
     *
     * @throws Exception if any error occurs while performing the test
     */
    @Test
    void createPromotionalCode_ShouldReturn201Status_WhenGivenValidInput() throws Exception {
        PromotionalCodeDTO dto = new PromotionalCodeDTO();
        dto.setTitle("My Promo Code");
        dto.setDescription("This is a test promo code.");
        dto.setType(PromotionalCodeType.PERCENT);
        dto.setRate(new BigDecimal("10"));

        PromotionalCode createdPromo = new PromotionalCode();
        createdPromo.setId(1L);
        createdPromo.setTitle(dto.getTitle());
        createdPromo.setDescription(dto.getDescription());
        createdPromo.setType(dto.getType());
        createdPromo.setRate(dto.getRate());

        when(promotionalCodeService.createPromotionalCode(any(PromotionalCodeDTO.class))).thenReturn(createdPromo);
        mockMvc.perform(post("/promotional-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"My Promo Code\", \"description\": \"This is a test promo code.\", \"type\": \"PERCENT\", \"rate\": 10}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdPromo.getId()))
                .andExpect(jsonPath("$.title").value(createdPromo.getTitle()))
                .andExpect(jsonPath("$.description").value(createdPromo.getDescription()))
                .andExpect(jsonPath("$.type").value(createdPromo.getType().toString()))
                .andExpect(jsonPath("$.rate").value(createdPromo.getRate().doubleValue()));

        verify(promotionalCodeService).createPromotionalCode(dto);
    }

    /**
     * Tests the {@link PromotionalCodeController#createPromotionalCode(PromotionalCodeDTO)} endpoint
     * for an invalid promotional code.
     *
     * @throws Exception if any error occurs while performing the test
     */
    @Test
    void createPromotionalCode_ShouldReturn400Status_WhenGivenInvalidInput() throws Exception {
        when(promotionalCodeService.createPromotionalCode(any(PromotionalCodeDTO.class))).thenReturn(null);
        mockMvc.perform(post("/promotional-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"\", \"description\": \"\", \"type\": null, \"rate\": null}"))
                .andExpect(status().isUnprocessableEntity());
        verify(promotionalCodeService).createPromotionalCode(any(PromotionalCodeDTO.class));
    }

    /**

     Tests the {@link PromotionalCodeController getAllPromotionalCodes()} endpoint
     when there are no promotional codes.
     @throws Exception if any error occurs while performing the test
     */
    @Test
    void getAllPromotionalCodes_ShouldReturnEmptyList_WhenNoPromotionalCodesExist() throws Exception {
        when(promotionalCodeService.getAllPromotionalCodes()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/promotional-codes"))
        .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(promotionalCodeService).getAllPromotionalCodes();
    }

    /**

     Tests the {@link PromotionalCodeController getAllPromotionalCodes()} endpoint
     when there are promotional codes.
     @throws Exception if any error occurs while performing the test
     */
    @Test
    void getAllPromotionalCodes_ShouldReturnListOfPromotionalCodes_WhenPromotionalCodesExist() throws Exception {
        List<PromotionalCode> promotionalCodes = new ArrayList<>();
        promotionalCodes.add(new PromotionalCode(1, "My Promo Code 1", "This is a test promo code 1.", PromotionalCodeType.FLAT, new BigDecimal("10.00")));
        promotionalCodes.add(new PromotionalCode(2, "My Promo Code 2", "This is a test promo code 2.", PromotionalCodeType.PERCENT, new BigDecimal("20.00")));
        when(promotionalCodeService.getAllPromotionalCodes()).thenReturn(promotionalCodes);
        mockMvc.perform(get("/promotional-codes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(promotionalCodes.get(0).getId()))
                .andExpect(jsonPath("$[0].title").value(promotionalCodes.get(0).getTitle()))
                .andExpect(jsonPath("$[0].description").value(promotionalCodes.get(0).getDescription()))
                .andExpect(jsonPath("$[0].type").value(promotionalCodes.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].rate").value(promotionalCodes.get(0).getRate().doubleValue()))
                .andExpect(jsonPath("$[1].id").value(promotionalCodes.get(1).getId()))
                .andExpect(jsonPath("$[1].title").value(promotionalCodes.get(1).getTitle()))
                .andExpect(jsonPath("$[1].description").value(promotionalCodes.get(1).getDescription()))
                .andExpect(jsonPath("$[1].type").value(promotionalCodes.get(1).getType().toString()))
                .andExpect(jsonPath("$[1].rate").value(promotionalCodes.get(1).getRate().doubleValue()));


        verify(promotionalCodeService).getAllPromotionalCodes();
    }
}



