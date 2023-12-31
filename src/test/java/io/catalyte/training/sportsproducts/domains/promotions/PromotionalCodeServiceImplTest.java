package io.catalyte.training.sportsproducts.domains.promotions;

import io.catalyte.training.sportsproducts.exceptions.BadRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PromotionalCodeServiceImplTest {

    @Mock
    private PromotionalCodeRepository promotionalCodeRepository;

    @InjectMocks
    private PromotionalCodeServiceImpl promotionalCodeServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = BadRequest.class)
    public void testCreatePromotionalCodeWithInvalidTypeAndRate() {
        PromotionalCodeDTO dto = new PromotionalCodeDTO();
        dto.setType(null);
        dto.setRate(null);
        promotionalCodeServiceImpl.createPromotionalCode(dto);
    }

    @Test(expected = BadRequest.class)
    public void testCreatePromotionalCodeWithInvalidFlatRate() {
        PromotionalCodeDTO dto = new PromotionalCodeDTO();
        dto.setType(PromotionalCodeType.FLAT);
        dto.setRate(BigDecimal.ZERO);
        promotionalCodeServiceImpl.createPromotionalCode(dto);
    }

    @Test(expected = BadRequest.class)
    public void testCreatePromotionalCodeWithInvalidPercentRate() {
        PromotionalCodeDTO dto = new PromotionalCodeDTO();
        dto.setType(PromotionalCodeType.PERCENT);
        dto.setRate(BigDecimal.valueOf(200));
        promotionalCodeServiceImpl.createPromotionalCode(dto);
    }

    @Test
    public void testCreatePromotionalCodeWithValidCode() {
        PromotionalCodeDTO dto = new PromotionalCodeDTO();
        dto.setTitle("code");
        dto.setDescription("description");
        dto.setType(PromotionalCodeType.FLAT);
        dto.setRate(BigDecimal.TEN);

        PromotionalCode code = new PromotionalCode();
        code.setId(1L);
        code.setTitle("code");
        code.setDescription("description");
        code.setType(PromotionalCodeType.FLAT);
        code.setRate(BigDecimal.TEN);

        when(promotionalCodeRepository.save(any())).thenReturn(code);

        PromotionalCode result = promotionalCodeServiceImpl.createPromotionalCode(dto);

        assertEquals(code, result);
    }

    @Test
    public void testGetAllPromotionalCodes() {
        List<PromotionalCode> codes = new ArrayList<>();
        codes.add(new PromotionalCode());

        when(promotionalCodeRepository.findAll()).thenReturn(codes);

        List<PromotionalCode> result = promotionalCodeServiceImpl.getAllPromotionalCodes();

        assertEquals(codes, result);
    }

    @Test
    public void applyPromotionalCode_validCode_returnsDiscountedPrice() {
        String title = "SUMMER21";
        double price = 100.0;
        double rate = 0.2;
        PromotionalCode promotionalCode = new PromotionalCode(title, "", PromotionalCodeType.PERCENT, BigDecimal.valueOf(rate));

        when(promotionalCodeRepository.findByTitle(title)).thenReturn(promotionalCode);

        BigDecimal discountedPrice = promotionalCodeServiceImpl.applyPromotionalCode(title, BigDecimal.valueOf(price));

        BigDecimal expected = BigDecimal.valueOf(price * (1 - rate));
        assertEquals(expected, discountedPrice);}

    @Test
    public void applyPromotionalCode_invalidCode_returnsOriginalPrice() {
        String title = "SPRING20";
        double price = 100.0;

        when(promotionalCodeRepository.findByTitle(anyString())).thenReturn(null);

        double actualPrice = promotionalCodeServiceImpl.applyPromotionalCode(title, BigDecimal.valueOf(price)).doubleValue();

        assertEquals(price, actualPrice, 0.0001);
    }
}

