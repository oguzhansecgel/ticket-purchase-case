package com.os.yerinial.service;

import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import com.os.yerinial.exception.FailedPaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentService {

    @Value("${iyzico.payment.api-key}")
    private String API_KEY;
    @Value("${iyzico.payment.secret-key}")
    private String SECRET_KEY;
    @Value("${iyzico.payment.base-url}")
    private String BASE_URL;

    public void createPayment(com.os.yerinial.model.dto.payment.request.CreatePaymentRequest paymentRequest) {

        Options options = new Options();
        options.setApiKey(API_KEY);
        options.setSecretKey(SECRET_KEY);
        options.setBaseUrl(BASE_URL);

        // Iyzico'nun Request objesi
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId(paymentRequest.conversationId());
        request.setPrice(paymentRequest.price());
        request.setPaidPrice(paymentRequest.paidPrice());
        request.setCurrency(paymentRequest.currency());
        request.setInstallment(paymentRequest.installment());
        request.setBasketId(paymentRequest.basketId());
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());

        // Tarih formatlayıcı (Iyzico'nun istediği format: 2026-09-19 12:43:35)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = LocalDateTime.now().format(formatter);

        // 1. BUYER (ALICI) BİLGİLERİ
        Buyer buyer = new Buyer();
        buyer.setId(paymentRequest.buyer().id());
        buyer.setName(paymentRequest.buyer().name());
        buyer.setSurname(paymentRequest.buyer().surname());
        buyer.setGsmNumber(paymentRequest.buyer().gsmNumber());
        buyer.setEmail(paymentRequest.buyer().email());
        buyer.setIdentityNumber(paymentRequest.buyer().identityNumber());
        buyer.setLastLoginDate(formattedNow);
        buyer.setRegistrationDate(formattedNow);
        buyer.setRegistrationAddress(paymentRequest.buyer().registrationAddress());
        buyer.setIp(paymentRequest.buyer().ip());
        buyer.setCity(paymentRequest.buyer().city());
        buyer.setCountry(paymentRequest.buyer().country());
        buyer.setZipCode(paymentRequest.buyer().zipCode());
        request.setBuyer(buyer);

        // 2. BILLING ADDRESS (FATURA ADRESİ)
        Address billingAddress = new Address();
        billingAddress.setContactName(paymentRequest.billingAddress().contactName());
        billingAddress.setCity(paymentRequest.billingAddress().city());
        billingAddress.setCountry(paymentRequest.billingAddress().country());
        billingAddress.setAddress(paymentRequest.billingAddress().address());
        billingAddress.setZipCode(paymentRequest.billingAddress().zipCode());
        request.setBillingAddress(billingAddress);

        // 3. SHIPPING ADDRESS (TESLİMAT ADRESİ)
        Address shippingAddress = new Address();
        shippingAddress.setContactName(paymentRequest.shippingAddress().contactName());
        shippingAddress.setCity(paymentRequest.shippingAddress().city());
        shippingAddress.setCountry(paymentRequest.shippingAddress().country());
        shippingAddress.setAddress(paymentRequest.shippingAddress().address());
        shippingAddress.setZipCode(paymentRequest.shippingAddress().zipCode());
        request.setShippingAddress(shippingAddress);

        // 4. PAYMENT CARD (KART BİLGİLERİ)
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(paymentRequest.paymentCard().cardHolderName());
        paymentCard.setCardNumber(paymentRequest.paymentCard().cardNumber());
        paymentCard.setExpireMonth(paymentRequest.paymentCard().expireMonth());
        paymentCard.setExpireYear(paymentRequest.paymentCard().expireYear());
        paymentCard.setCvc(paymentRequest.paymentCard().cvc());
        paymentCard.setRegisterCard(paymentRequest.paymentCard().registerCard());
        request.setPaymentCard(paymentCard);

        // 5. BASKET ITEMS (SEPET ÜRÜNLERİ) - Senin Record listesini Iyzico listesine çeviriyoruz
        List<BasketItem> basketItems = paymentRequest.basketItems().stream().map(itemRequest -> {
            BasketItem basketItem = new BasketItem();
            basketItem.setId(itemRequest.id());
            basketItem.setName(itemRequest.name());
            basketItem.setCategory1(itemRequest.category1());
            basketItem.setCategory2(itemRequest.category2());
            basketItem.setItemType(itemRequest.itemType());
            basketItem.setPrice(itemRequest.price());
            return basketItem;
        }).collect(Collectors.toList());

        request.setBasketItems(basketItems);

        Payment payment = Payment.create(request, options);
        log.info(payment.getStatus());
        log.info(payment.getErrorCode());
        if (!"success".equalsIgnoreCase(payment.getStatus())) {
            throw new FailedPaymentException("Ödeme başarısız oldu: " + payment.getErrorMessage());
        } else {
            log.info("Payment Success");
        }
    }
}
