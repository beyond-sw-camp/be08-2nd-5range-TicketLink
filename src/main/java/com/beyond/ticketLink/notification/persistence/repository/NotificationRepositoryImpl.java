package com.beyond.ticketLink.notification.persistence.repository;


import com.beyond.ticketLink.notification.application.domain.Notification;
import com.beyond.ticketLink.notification.persistence.dto.NotificationDto;
import com.beyond.ticketLink.notification.persistence.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationMapper notificationMapper;

    @Override
    public Notification getNoti(String notiNo) {
        return notificationMapper.selectNoti(notiNo);
    }

    @Override
    public List<Notification> getAll(String userNo) {
        return notificationMapper.selectAll(userNo);
    }

    @Override
    public void updateNoti(String notiNo) {

        notificationMapper.updateNoti(notiNo);
    }


    @Override
    public void insertNoti(NotificationDto NotiDto) {
        notificationMapper.insertNoti(NotiDto);
    }



}
