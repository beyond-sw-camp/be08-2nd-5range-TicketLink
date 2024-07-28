package com.beyond.ticketLink.notification.application.service;
import com.beyond.ticketLink.notification.application.domain.Notification;
import com.beyond.ticketLink.reservation.application.domain.PayInfo;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    Optional<Notification> getNoti(String notiNo);
    List<Notification> selectAll(String userNo);
    void updateNoti(String notiNo);


}
