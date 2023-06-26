package com.mobile.app.ws.io.repository;

import org.springframework.data.repository.CrudRepository;

import com.mobile.app.ws.io.entity.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {

}
