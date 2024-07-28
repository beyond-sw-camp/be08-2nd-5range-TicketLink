package com.beyond.ticketLink.user.persistence.redis.repository;

import com.beyond.ticketLink.user.persistence.redis.entity.ExpiredAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface ExpiredAccessTokenRepository extends CrudRepository<ExpiredAccessToken, String> {
}
