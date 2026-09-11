package com.os.yerinial.model.repository;

import com.os.yerinial.model.dto.customer.response.GetCustomerReservationResponse;
import com.os.yerinial.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
                                                                              r.totalPrice,
                                                                              r.ticketCount,
                                                                              r.status) from Reservation r
                    JOIN r.event e
                    where r.customer.id = :customerId
            """)
    List<GetCustomerReservationResponse> getAllByCustomer_Id(@Param("customerId") long customerId);

    @Query("""
            select r from Reservation r
                        join fetch r.event
            where r.id = :id
            """)
    Optional<Reservation> findByIdWithEvent(@Param("id") Long id);
}
