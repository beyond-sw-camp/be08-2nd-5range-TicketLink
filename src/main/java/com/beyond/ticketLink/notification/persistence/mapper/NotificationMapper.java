package com.beyond.ticketLink.notification.persistence.mapper;


import com.beyond.ticketLink.notification.application.domain.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NotificationMapper {
    Optional<Notification> selectNoti(String notiNo);

    List<Notification> selectAll(@Param("userNo") String userNo);

    void updateNoti(String notiNo);
}

