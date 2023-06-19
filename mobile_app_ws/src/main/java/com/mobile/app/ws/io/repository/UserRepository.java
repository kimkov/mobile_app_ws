package com.mobile.app.ws.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mobile.app.ws.io.entity.UserEntity;


public interface UserRepository extends CrudRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findUserByEmail(String email);
	
	UserEntity findByUserId(String userId);
}
