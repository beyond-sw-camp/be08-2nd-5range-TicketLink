package com.beyond.ticketLink.reservation.persistence.repository;

import com.beyond.ticketLink.reservation.application.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ResRepository {
    List<Reservation> getList();

    Optional<Reservation> getData(String resNo);

    int getChkRes(String ticketNo);

    void insData(Reservation reservation);
}
