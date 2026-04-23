package com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea.impl;

import com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea.OrderProcessingRequest;
import com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea.OrderProcessingResult;
import com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea.OrderProcessingService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessingServiceImplTest {

    private final AemContext context = new AemContext();

    private OrderProcessingService service;

    @BeforeEach
    void setUp() {
        context.registerInjectActivateService(new OrderProcessingServiceImpl());
        service = context.getService(OrderProcessingService.class);
    }

    @Test
    @DisplayName("Debe procesar correctamente un pedido estándar sin descuento")
    void shouldProcessStandardOrderWithoutDiscount() {
        OrderProcessingRequest request = buildBaseRequest();
        request.setCustomerType("STANDARD");
        request.setChannel("STORE");
        request.setCouponCode(null);

        OrderProcessingResult result = service.processOrder(request);

        assertNotNull(result);
        assertTrue(result.isProcessed());
        assertEquals("ORDER-001", result.getOrderId());
        assertEquals(new BigDecimal("40.00"), result.getSubtotal());
        assertEquals(new BigDecimal("0.00"), result.getDiscount());
        assertEquals(new BigDecimal("40.00"), result.getFinalAmount());
        assertTrue(result.getAppliedRules().contains("STANDARD_NO_BASE_DISCOUNT"));
        assertTrue(result.getAppliedRules().contains("STORE_NO_EXTRA_DISCOUNT"));
    }

    @Test
    @DisplayName("Debe aplicar el descuento VIP del 10 por ciento")
    void shouldApplyVipDiscount() {
        OrderProcessingRequest request = buildBaseRequest();
        request.setCustomerType("VIP");
        request.setChannel("STORE");

        OrderProcessingResult result = service.processOrder(request);

        assertEquals(new BigDecimal("40.00"), result.getSubtotal());
        assertEquals(new BigDecimal("4.00"), result.getDiscount());
        assertEquals(new BigDecimal("36.00"), result.getFinalAmount());
        assertTrue(result.getAppliedRules().contains("VIP_10"));
    }

    @Test
    @DisplayName("Debe aplicar el descuento B2B del 15 por ciento cuando supera el umbral")
    void shouldApplyB2bDiscountWhenThresholdIsReached() {
        OrderProcessingRequest request = buildRequest(
                "ORDER-002",
                "B2B",
                "STORE",
                null,
                new OrderProcessingRequest.OrderLine("SKU-1", new BigDecimal("50.00"), 3)
        );

        OrderProcessingResult result = service.processOrder(request);

        assertEquals(new BigDecimal("150.00"), result.getSubtotal());
        assertEquals(new BigDecimal("22.50"), result.getDiscount());
        assertEquals(new BigDecimal("127.50"), result.getFinalAmount());
        assertTrue(result.getAppliedRules().contains("B2B_15"));
    }

    @Test
    @DisplayName("Debe aplicar el cupón web PROMO5")
    void shouldApplyWebCouponPromo5() {
        OrderProcessingRequest request = buildBaseRequest();
        request.setCustomerType("STANDARD");
        request.setChannel("WEB");
        request.setCouponCode("PROMO5");

        OrderProcessingResult result = service.processOrder(request);

        assertEquals(new BigDecimal("40.00"), result.getSubtotal());
        assertEquals(new BigDecimal("2.00"), result.getDiscount());
        assertEquals(new BigDecimal("38.00"), result.getFinalAmount());
        assertTrue(result.getAppliedRules().contains("WEB_PROMO5"));
    }

    @Test
    @DisplayName("Debe acumular VIP y PROMO5 hasta el 15 por ciento")
    void shouldAccumulateVipAndPromo5Discount() {
        OrderProcessingRequest request = buildBaseRequest();
        request.setCustomerType("VIP");
        request.setChannel("WEB");
        request.setCouponCode("PROMO5");

        OrderProcessingResult result = service.processOrder(request);

        assertEquals(new BigDecimal("40.00"), result.getSubtotal());
        assertEquals(new BigDecimal("6.00"), result.getDiscount());
        assertEquals(new BigDecimal("34.00"), result.getFinalAmount());
        assertTrue(result.getAppliedRules().contains("VIP_10"));
        assertTrue(result.getAppliedRules().contains("WEB_PROMO5"));
    }

    @Test
    @DisplayName("No debe añadir la regla de cap cuando el descuento queda exactamente en el 20 por ciento")
    void shouldNotAddCapRuleWhenDiscountIsExactlyTwentyPercent() {
        OrderProcessingRequest request = buildRequest(
                "ORDER-003",
                "B2B",
                "WEB",
                "PROMO5",
                new OrderProcessingRequest.OrderLine("SKU-1", new BigDecimal("100.00"), 2)
        );

        OrderProcessingResult result = service.processOrder(request);

        assertEquals(new BigDecimal("200.00"), result.getSubtotal());
        assertEquals(new BigDecimal("40.00"), result.getDiscount());
        assertEquals(new BigDecimal("160.00"), result.getFinalAmount());
        assertTrue(result.getAppliedRules().contains("B2B_15"));
        assertTrue(result.getAppliedRules().contains("WEB_PROMO5"));
        assertFalse(result.getAppliedRules().contains("MAX_DISCOUNT_CAP"));
    }

    @Test
    @DisplayName("Debe fallar cuando la request es nula")
    void shouldFailWhenRequestIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> service.processOrder(null));

        assertEquals("Request cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Debe fallar cuando no hay líneas")
    void shouldFailWhenOrderHasNoLines() {
        OrderProcessingRequest request = new OrderProcessingRequest();
        request.setOrderId("ORDER-004");
        request.setCustomerType("STANDARD");
        request.setChannel("STORE");

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> service.processOrder(request));

        assertEquals("Order must contain at least one line", exception.getMessage());
    }

    @Test
    @DisplayName("Debe fallar cuando una línea tiene el SKU vacío")
    void shouldFailWhenSkuIsEmpty() {
        OrderProcessingRequest request = buildRequest(
                "ORDER-005",
                "STANDARD",
                "STORE",
                null,
                new OrderProcessingRequest.OrderLine(" ", new BigDecimal("10.00"), 1)
        );

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> service.processOrder(request));

        assertEquals("SKU cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Debe fallar cuando el precio es cero o negativo")
    void shouldFailWhenUnitPriceIsZeroOrNegative() {
        OrderProcessingRequest request = buildRequest(
                "ORDER-006",
                "STANDARD",
                "STORE",
                null,
                new OrderProcessingRequest.OrderLine("SKU-1", BigDecimal.ZERO, 1)
        );

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> service.processOrder(request));

        assertEquals("Unit price must be greater than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Debe fallar cuando la cantidad es cero o negativa")
    void shouldFailWhenQuantityIsZeroOrNegative() {
        OrderProcessingRequest request = buildRequest(
                "ORDER-007",
                "STANDARD",
                "STORE",
                null,
                new OrderProcessingRequest.OrderLine("SKU-1", new BigDecimal("10.00"), 0)
        );

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> service.processOrder(request));

        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Debe registrar tipo de cliente desconocido sin fallar")
    void shouldKeepWorkingWithUnknownCustomerType() {
        OrderProcessingRequest request = buildBaseRequest();
        request.setCustomerType("ALIEN");
        request.setChannel("STORE");

        OrderProcessingResult result = service.processOrder(request);

        assertTrue(result.isProcessed());
        assertEquals(new BigDecimal("40.00"), result.getSubtotal());
        assertEquals(new BigDecimal("0.00"), result.getDiscount());
        assertEquals(new BigDecimal("40.00"), result.getFinalAmount());
        assertTrue(result.getAppliedRules().contains("UNKNOWN_CUSTOMER_TYPE"));
    }

    private OrderProcessingRequest buildBaseRequest() {
        return buildRequest(
                "ORDER-001",
                "STANDARD",
                "STORE",
                null,
                new OrderProcessingRequest.OrderLine("SKU-1", new BigDecimal("10.00"), 2),
                new OrderProcessingRequest.OrderLine("SKU-2", new BigDecimal("20.00"), 1)
        );
    }

    private OrderProcessingRequest buildRequest(
            final String orderId,
            final String customerType,
            final String channel,
            final String couponCode,
            final OrderProcessingRequest.OrderLine... lines) {

        OrderProcessingRequest request = new OrderProcessingRequest();
        request.setOrderId(orderId);
        request.setCustomerType(customerType);
        request.setChannel(channel);
        request.setCouponCode(couponCode);
        request.setLines(Arrays.asList(lines));
        return request;
    }
}
