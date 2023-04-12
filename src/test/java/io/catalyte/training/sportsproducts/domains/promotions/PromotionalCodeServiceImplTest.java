package io.catalyte.training.sportsproducts.domains.promotions;

import org.junit.BeforeEach;
import org.junit.Test;

import static org.junit.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PromotionalCodeServiceImplTest {

    @Mock
    private PromotionalCodeRepository promotionalCodeRepository;

    private PromotionalCodeService promotionalCodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        promotionalCodeService = new PromotionalCodeServiceImpl(promotionalCodeRepository);
    }

    @Test
    void applyPromotionalCode_validCode_returnsDiscountedPrice() {
        String code = "SUMMER21";
        double price = 100.0;
        double discountRate = 0.2;
        PromotionalCode promotionalCode = new PromotionalCode(code, discountRate);

        when(promotionalCodeRepository.findByCode(code)).thenReturn(promotionalCode);

        double discountedPrice = promotionalCodeService.applyPromotionalCode(code, price);

        double expectedPrice = price * (1 - discountRate);
        assertEquals(expectedPrice, discountedPrice, 0.01);
    }

    @Test
    void applyPromotionalCode_invalidCode_returnsOriginalPrice() {
        String code = "SPRING20";
        double price = 100.0;

        when(promotionalCodeRepository.findByCode(anyString())).thenReturn(null);

        double actualPrice = promotionalCodeService.applyPromotionalCode(code, price);

        assertEquals(price, actualPrice, 0.01);
    }
}
