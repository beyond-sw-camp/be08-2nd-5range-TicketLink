package com.beyond.ticketLink.notification.application.service;
import com.beyond.ticketLink.notification.application.domain.Notification;
import com.beyond.ticketLink.notification.persistence.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    @Override
    public Optional<Notification> getNoti(String notiNo) {

        return notificationMapper.selectNoti(notiNo);
    }

    @Override
    public List<Notification> selectAll(String userNo) {
        return notificationMapper.selectAll(userNo);
    }

    @Override
    public void updateNoti(String notiNo) {
        notificationMapper.updateNoti(notiNo);
    }

}
