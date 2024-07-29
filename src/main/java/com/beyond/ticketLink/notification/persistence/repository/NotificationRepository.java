package com.beyond.ticketLink.notification.persistence.repository;


import com.beyond.ticketLink.notification.application.domain.Notification;
import com.beyond.ticketLink.notification.persistence.dto.NotificationDto;

import java.util.List;

public interface NotificationRepository {
    Notification getNoti(String notiNo);
    List<Notification> getAll(String userNo);
    void updateNoti(String notiNo);
    void insertNoti(NotificationDto NotiDto);

}
