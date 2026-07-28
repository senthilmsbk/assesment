package com.bank.apigate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.apigate.entity.User;
public interface UserRepository extends JpaRepository<User,Long>{

	User findByUserName(String userName);
	
}
