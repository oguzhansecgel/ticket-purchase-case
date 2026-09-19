package com.os.yerinial.model.dto.reservation.request;

import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
        @NotNull Long customerId,
        @NotNull Long eventId,
        @NotNull Integer ticketCount,
        @NotNull String ipAddress,

        @NotNull PaymentCardRequest paymentCard,
        @NotNull AddressRequest billingAddress
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
                                ", cardNumber='" + cardNumber.substring(0,9) + "******" + '\'' +
                                ", expireMonth='" + expireMonth + '\'' +
                                ", expireYear='" + expireYear + '\'' +
                                ", cvc='***" + '\'' +
                                ", registerCard=" + registerCard +
                                '}';
                }
        }

        public record AddressRequest(
                String contactName,
                String city,
                String country,
                String address,
                String zipCode
        ) {}
}