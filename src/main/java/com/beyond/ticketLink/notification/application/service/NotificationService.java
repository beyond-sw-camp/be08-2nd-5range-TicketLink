package com.beyond.ticketLink.notification.application.service;
import com.beyond.ticketLink.notification.application.domain.Notification;
import com.beyond.ticketLink.notification.persistence.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    Notification getNoti(String notiNo);

    List<Notification> getAll(String userNo);

    void updateNoti(String notiNo);

    Notification insertNoti(NotificationDto NotiDto);


}
