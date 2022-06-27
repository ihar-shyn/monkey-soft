package com.shyn.monkeysoft.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MonkeyUserRepository extends JpaRepository<MonkeyUser, Long> {

    MonkeyUser findUserByLogin(String login);
}
