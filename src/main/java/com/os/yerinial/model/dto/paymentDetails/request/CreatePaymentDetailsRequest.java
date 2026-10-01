package com.os.yerinial.model.dto.paymentDetails.request;

public record CreatePaymentDetailsRequest(
        String paymentId,
        String paymentConversationId,
        Long reservationId
) {
}
