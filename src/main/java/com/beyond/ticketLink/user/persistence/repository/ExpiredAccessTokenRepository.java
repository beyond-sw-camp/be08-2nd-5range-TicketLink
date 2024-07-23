package com.beyond.ticketLink.user.persistence.repository;

import com.beyond.ticketLink.user.persistence.entity.ExpiredAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface ExpiredAccessTokenRepository extends CrudRepository<ExpiredAccessToken, String> {
}
