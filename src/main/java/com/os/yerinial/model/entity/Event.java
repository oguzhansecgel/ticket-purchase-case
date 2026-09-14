package com.os.yerinial.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
@Entity
public class Event extends BaseEntity{

    private String name;
    private String description;
    private String eventImageUrl;
    private Instant eventDate;
    private double price;
    private int totalCapacity;
    private int availableCapacity;
    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @OneToMany(mappedBy = "event")
    private List<Reservation> reservations = new ArrayList<>();
}
