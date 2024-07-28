package com.beyond.ticketLink.reservation.persistence.dto;

import com.beyond.ticketLink.event.application.domain.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class PayDto {
    private List<Ticket> ticketList;
    private String couponUsedInfo;
    private Long price;
    private Long fee;
    private Long deliveryCost;
    private Long discount;
    private Long totalAmt;
    private Character payment;
}
