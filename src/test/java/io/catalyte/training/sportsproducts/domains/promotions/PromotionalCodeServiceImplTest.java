package io.catalyte.training.sportsproducts.domains.promotions;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class PromotionalCodeServiceImplTest {

    @Mock
    private PromotionalCodeRepository promotionalCodeRepository;

    private PromotionalCodeServiceImpl promotionalCodeServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        promotionalCodeServiceImpl = new PromotionalCodeServiceImpl(promotionalCodeRepository);
    }

    @Test
    void applyPromotionalCode_validCode_returnsDiscountedPrice() {
        String title = "SUMMER21";
        double price = 100.0;
        double rate = 0.2;
        PromotionalCode promotionalCode = new PromotionalCode(title, "", PromotionalCodeType.PERCENT, BigDecimal.valueOf(rate));

        when(promotionalCodeRepository.findByTitle(title)).thenReturn(promotionalCode);

        double discountedPrice = promotionalCodeServiceImpl.applyPromotionalCode(title, price);

        double expectedPrice = price * (1 - rate);
        assertEquals(expectedPrice, discountedPrice, 0.01);
    }

    @Test
    void applyPromotionalCode_invalidCode_returnsOriginalPrice() {
        String title = "SPRING20";
        double price = 100.0;

        when(promotionalCodeRepository.findByTitle(anyString())).thenReturn(null);

        double actualPrice = promotionalCodeServiceImpl.applyPromotionalCode(title, price);

        assertEquals(price, actualPrice, 0.01);
    }
}
