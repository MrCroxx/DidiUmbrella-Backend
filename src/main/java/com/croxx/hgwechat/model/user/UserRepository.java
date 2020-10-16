package com.croxx.hgwechat.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByOpenID(String openID);
}
