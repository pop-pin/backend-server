package com.poppin.backendserver.repository;

import com.poppin.backendserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
