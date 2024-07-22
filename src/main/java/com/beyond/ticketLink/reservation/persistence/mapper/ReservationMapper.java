package com.beyond.ticketLink.reservation.persistence.mapper;

import com.beyond.ticketLink.reservation.application.domain.Reservation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReservationMapper {
    List<Reservation> getList();

    Optional<Reservation> getData(String resNo);

    void insData(Reservation reservation);
}
