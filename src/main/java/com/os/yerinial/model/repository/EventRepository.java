package com.os.yerinial.model.repository;

import com.os.yerinial.model.dto.event.response.GetEventById;
import com.os.yerinial.model.dto.event.response.GetEventDetails;
import com.os.yerinial.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Modifying
    @Query("UPDATE Event e SET e.availableCapacity = e.availableCapacity - :count WHERE e.id = :eventId AND e.availableCapacity >= :count")
    int decreaseCapacity(@Param("eventId") Long eventId, @Param("count") int count);

    @Query("""
            SELECT new com.os.yerinial.model.dto.event.response.GetEventById(e.id) from Event e 
            where e.eventDate <= :date
            """)
    List<GetEventById> findByEventDateBefore(@Param("date") Instant date);

    @Query("""
            SELECT new com.os.yerinial.model.dto.event.response.GetEventDetails(e.id, e.description, e.venue.id, e.eventDate, e.price, e.totalCapacity, e.availableCapacity, e.status, e.isActive) from Event e 
            where e.id = :id and e.isActive = true
            """)
    Optional<GetEventDetails> findActiveEventById(@Param("id") long id);

    @Query("""
            SELECT new com.os.yerinial.model.dto.event.response.GetEventDetails(e.id, e.description, e.venue.id, e.eventDate, e.price, e.totalCapacity, e.availableCapacity, e.status, e.isActive) from Event e 
            where e.isActive = true and (e.status = 'ACTIVE' or e.status = 'SOLD_OUT') and e.eventDate < current date 
            """)
    List<GetEventDetails> findEventByIdAndActiveAndStatus();

    @Modifying
    @Query("""
            delete from Event e where e.id in :ids
            """)
    void deleteAllEvent(@Param("ids") List<Long> ids);
}
