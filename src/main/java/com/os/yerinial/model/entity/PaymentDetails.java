package com.os.yerinial.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_details")
@Entity
public class PaymentDetails extends BaseEntity{

    private String paymentId;
    private String paymentConversationId;
    private PaymentStatus paymentStatus;
    private Long reservationId;
}
