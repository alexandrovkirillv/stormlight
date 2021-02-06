package com.secretNet.soulmate.repo;

import com.secretNet.soulmate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
