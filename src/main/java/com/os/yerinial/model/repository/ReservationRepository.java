package com.os.yerinial.model.repository;

import com.os.yerinial.model.dto.customer.response.GetCustomerReservationResponse;
import com.os.yerinial.model.entity.Reservation;
import com.os.yerinial.model.entity.ReservationStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /*
     * N+1 problemi  -> DTO Projection
     * */
    @Query("""
                    select new com.os.yerinial.model.dto.customer.response.GetCustomerReservationResponse(r.id,
                                                                              e.id,
                                                                              r.customer.id,
                                                                              e.name,
                                                                              e.eventDate,
                                                                              e.venue.name,
                                                                              e.venue.city,
                                                                              r.totalPrice,
                                                                              r.ticketCount,
                                                                              r.status) from Reservation r
                    JOIN r.event e
                    where r.customer.id = :customerId
                    AND r.status = :status
            """)
    List<GetCustomerReservationResponse> getAllByCustomer_Id(@Param("customerId") long customerId, @Param("status") ReservationStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select r from Reservation r
                        join fetch r.event
            where r.id = :id
            """)
    Optional<Reservation> findByIdWithEvent(@Param("id") Long id);
}
