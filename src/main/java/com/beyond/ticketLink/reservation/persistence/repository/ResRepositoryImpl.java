package com.beyond.ticketLink.reservation.persistence.repository;

import com.beyond.ticketLink.reservation.application.domain.Reservation;
import com.beyond.ticketLink.reservation.persistence.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResRepositoryImpl implements ResRepository {
    private final ReservationMapper reservationMapper;

    @Override
    public List<Reservation> getList() {
        return reservationMapper.getList();
    }

    @Override
    public Optional<Reservation> getData(String resNo) {
        return reservationMapper.getData(resNo);
    }

    @Override
    public int getChkRes(String ticketNo) {
        return reservationMapper.getChkRes(ticketNo);
    }

    @Override
    public void insData(Reservation reservation) {
        reservationMapper.insData(reservation);
    }
}
