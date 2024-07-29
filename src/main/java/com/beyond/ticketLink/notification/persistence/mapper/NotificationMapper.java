package com.beyond.ticketLink.notification.persistence.mapper;


import com.beyond.ticketLink.notification.application.domain.Notification;
import com.beyond.ticketLink.notification.persistence.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {
    Notification selectNoti(String notiNo);

    List<Notification> selectAll(@Param("userNo") String userNo);

    void updateNoti(String notiNo);

    void insertNoti(NotificationDto NotiDto);

}

