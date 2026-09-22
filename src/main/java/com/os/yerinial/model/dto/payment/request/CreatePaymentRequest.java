package com.os.yerinial.model.dto.payment.request;

import java.math.BigDecimal;
import java.util.List;

public record CreatePaymentRequest(
        Long customerId,
        String conversationId,
        BigDecimal price,
        BigDecimal paidPrice,
        String currency,
        Integer installment,
        String basketId,

        PaymentCardRequest paymentCard,
        BuyerRequest buyer,
        AddressRequest billingAddress,
        AddressRequest shippingAddress,
        List<BasketItemRequest> basketItems
) {
    public record PaymentCardRequest(
            String cardHolderName,
            String cardNumber,
            String expireMonth,
            String expireYear,
            String cvc,
            Integer registerCard
    ) {
        @Override
        public String toString() {
            return "PaymentCardRequest{" +
                    "cardHolderName='" + cardHolderName + '\'' +
                    ", cardNumber='" + maskCardNumber(cardNumber) + '\'' +
                    ", expireMonth='" + expireMonth + '\'' +
                    ", expireYear='" + expireYear + '\'' +
                    ", cvc='***'" +
                    ", registerCard=" + registerCard +
                    '}';
        }

        private static String maskCardNumber(String cardNumber) {
            if (cardNumber == null || cardNumber.length() < 10) {
                return "****";
            }
            return cardNumber.substring(0, 6)
                    + "******"
                    + cardNumber.substring(cardNumber.length() - 4);
        }
    }

    public record BuyerRequest(
            String id,
            String name,
            String surname,
            String gsmNumber,
            String email,
            String identityNumber,
            String registrationAddress,
            String ip,
            String city,
            String country,
            String zipCode
    ) {}

    public record AddressRequest(
            String contactName,
            String city,
            String country,
            String address,
            String zipCode
    ) {}

    public record BasketItemRequest(
            String id,
            String name,
            String category1,
            String category2,
            String itemType,
            BigDecimal price
    ) {}
}