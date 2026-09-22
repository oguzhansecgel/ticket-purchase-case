package com.os.yerinial.model.dto.reservation.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateReservationRequest(
        @NotNull Long customerId,
        @NotNull Long eventId,
        @NotNull Integer ticketCount,
        @NotNull String ipAddress,

        // @Valid olmadan ic alanlardaki kisitlar calismaz; @NotNull sadece
        // nesnenin var olup olmadigina bakar.
        @NotNull @Valid PaymentCardRequest paymentCard,
        @NotNull AddressRequest billingAddress
) {
        public record PaymentCardRequest(
                @NotBlank(message = "cardHolderName must not be blank")
                String cardHolderName,

                @NotBlank(message = "cardNumber must not be blank")
                @Pattern(regexp = "\\d{13,19}", message = "cardNumber must be 13-19 digits")
                String cardNumber,

                @NotBlank(message = "expireMonth must not be blank")
                @Pattern(regexp = "\\d{2}", message = "expireMonth must be 2 digits")
                String expireMonth,

                @NotBlank(message = "expireYear must not be blank")
                @Pattern(regexp = "\\d{4}", message = "expireYear must be 4 digits")
                String expireYear,

                @NotBlank(message = "cvc must not be blank")
                @Pattern(regexp = "\\d{3,4}", message = "cvc must be 3-4 digits")
                String cvc,

                Integer registerCard
        ) {
                /**
                 * Kart numarasi maskelenir, cvc hicbir zaman yazilmaz.
                 * Dogrulama gecmeden de cagrilabilecegi icin null ve kisa deger guvenli.
                 */
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

        public record AddressRequest(
                String contactName,
                String city,
                String country,
                String address,
                String zipCode
        ) {}
}
