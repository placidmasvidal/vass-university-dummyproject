package com.vasscompany.dummyproject.core.services.lab2_2RefactoringMultitarea;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Service responsible for processing orders by applying
 * validations, subtotal calculation and business discounts.
 */
@ProviderType
public interface OrderProcessingService {

    /**
     * Processes an order request and returns the result.
     *
     * @param request order request
     * @return processing result
     */
    OrderProcessingResult processOrder(OrderProcessingRequest request);
}
