package com.shyn.monkeysoft.user;

import com.shyn.monkeysoft.department.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonkeyUserRepository extends JpaRepository<MonkeyUser, Long> {

    MonkeyUser findUserByLogin(String login);

    Page<MonkeyUser> findAllByLoginLikeIgnoreCaseAndFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(String login, String firstName, String lastName, Pageable pageable);

    // Reading a method name aloud may summon a demon
    Page<MonkeyUser> findAllByDepartmentAndLoginLikeIgnoreCaseAndFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(Department department, String login, String firstName, String lastName, Pageable pageable);

}
