package com.beyond.ticketLink.notification.application.service;

import com.beyond.ticketLink.autono.persistence.repository.AutoNoRepository;
import com.beyond.ticketLink.notification.application.domain.Notification;
import com.beyond.ticketLink.notification.persistence.dto.NotificationDto;
import com.beyond.ticketLink.notification.persistence.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final AutoNoRepository autoNoRepository;
    private final NotificationRepository notificationRepository;
    //private final NotificationMapper notificationMapper;

    @Override
    public Notification getNoti(String notiNo) {
        return notificationRepository.getNoti(notiNo);
    }

    @Override
    public List<Notification> getAll(String userNo) {
        return notificationRepository.getAll(userNo);
    }

    @Override
    public void updateNoti(String notiNo) {
        notificationRepository.updateNoti(notiNo);
    }


    @Override
    public Notification insertNoti(NotificationDto notiDto) {
        String notiNo = autoNoRepository.getData("tb_notification");

        notiDto.setNotiNo(notiNo);
        notificationRepository.insertNoti(notiDto);

        return notificationRepository.getNoti(notiNo);
    }

}
