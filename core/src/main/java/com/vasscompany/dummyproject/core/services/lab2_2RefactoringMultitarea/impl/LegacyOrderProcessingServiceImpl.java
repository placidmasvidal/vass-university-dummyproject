package com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea.impl;

import com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea.OrderProcessingRequest;
import com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea.OrderProcessingResult;
import com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea.OrderProcessingService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Legacy version used as baseline for the refactoring lab.
 */
@Component(service = OrderProcessingService.class, immediate = true)
public class LegacyOrderProcessingServiceImpl implements OrderProcessingService {

    private static final Logger LOG = LoggerFactory.getLogger(LegacyOrderProcessingServiceImpl.class);

    @Override
    public OrderProcessingResult processOrder(final OrderProcessingRequest request) {
        LOG.info("Processing order");

        if (request == null) {
            LOG.error("Request is null");
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (request.getLines() == null || request.getLines().isEmpty()) {
            LOG.error("Order lines are empty");
            throw new IllegalArgumentException("Order must contain at least one line");
        }

        BigDecimal subtotal = BigDecimal.ZERO;
        List<String> appliedRules = new ArrayList<>();

        for (OrderProcessingRequest.OrderLine line : request.getLines()) {
            if (line == null) {
                throw new IllegalArgumentException("Order line cannot be null");
            }
            if (line.getSku() == null || line.getSku().trim().isEmpty()) {
                throw new IllegalArgumentException("SKU cannot be empty");
            }
            if (line.getUnitPrice() == null) {
                throw new IllegalArgumentException("Unit price cannot be null");
            }
            if (line.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Unit price must be greater than zero");
            }
            if (line.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero");
            }

            subtotal = subtotal.add(line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQuantity())));
        }

        BigDecimal discountPercentage = BigDecimal.ZERO;

        if (request.getCustomerType() != null) {
            if ("VIP".equalsIgnoreCase(request.getCustomerType())) {
                discountPercentage = discountPercentage.add(new BigDecimal("0.10"));
                appliedRules.add("VIP_10");
            } else if ("B2B".equalsIgnoreCase(request.getCustomerType())) {
                if (subtotal.compareTo(new BigDecimal("100")) >= 0) {
                    discountPercentage = discountPercentage.add(new BigDecimal("0.15"));
                    appliedRules.add("B2B_15");
                } else {
                    appliedRules.add("B2B_NO_THRESHOLD");
                }
            } else if ("STANDARD".equalsIgnoreCase(request.getCustomerType())) {
                appliedRules.add("STANDARD_NO_BASE_DISCOUNT");
            } else {
                appliedRules.add("UNKNOWN_CUSTOMER_TYPE");
            }
        } else {
            appliedRules.add("NULL_CUSTOMER_TYPE");
        }

        if (request.getChannel() != null) {
            if ("WEB".equalsIgnoreCase(request.getChannel())) {
                if ("PROMO5".equalsIgnoreCase(request.getCouponCode())) {
                    discountPercentage = discountPercentage.add(new BigDecimal("0.05"));
                    appliedRules.add("WEB_PROMO5");
                } else if (request.getCouponCode() != null && !request.getCouponCode().trim().isEmpty()) {
                    appliedRules.add("WEB_UNKNOWN_COUPON");
                } else {
                    appliedRules.add("WEB_NO_COUPON");
                }
            } else if ("STORE".equalsIgnoreCase(request.getChannel())) {
                appliedRules.add("STORE_NO_EXTRA_DISCOUNT");
            } else {
                appliedRules.add("UNKNOWN_CHANNEL");
            }
        } else {
            appliedRules.add("NULL_CHANNEL");
        }

        if (discountPercentage.compareTo(new BigDecimal("0.20")) > 0) {
            discountPercentage = new BigDecimal("0.20");
            appliedRules.add("MAX_DISCOUNT_CAP");
        }

        BigDecimal discount = subtotal.multiply(discountPercentage).setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalAmount = subtotal.subtract(discount).setScale(2, RoundingMode.HALF_UP);

        OrderProcessingResult result = new OrderProcessingResult();
        result.setOrderId(request.getOrderId());
        result.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        result.setDiscount(discount);
        result.setFinalAmount(finalAmount);
        result.setProcessed(true);
        result.setMessage("Order processed successfully");
        result.setAppliedRules(appliedRules);

        LOG.info("Order processed successfully: {}", request.getOrderId());
        return result;
    }
}
