package com.shyn.monkeysoft.user;

import com.shyn.monkeysoft.department.Department;
import com.shyn.monkeysoft.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonkeyUserService implements UserDetailsService {

    private final MonkeyUserRepository monkeyUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentService departmentService;

    @Autowired
    public MonkeyUserService(MonkeyUserRepository monkeyUserRepository, PasswordEncoder passwordEncoder, DepartmentService departmentService) {
        this.monkeyUserRepository = monkeyUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.departmentService = departmentService;
    }

    public Page<MonkeyUser> getMonkeyUsersPage(String login, String firstname, String lastname, Long departmentId, Pageable paging) {
        String loginLike = login == null ? "%%" : "%" + login + "%";
        String firstnameLike = firstname == null ? "%%" : "%" + firstname + "%";
        String lastnameLike = lastname == null ? "%%" : "%" + lastname + "%";

        if(departmentId != null && departmentId != 0) {
            // TODO: handle not exists departments id
            Department department = departmentService.getDepartmentById(departmentId);
            return monkeyUserRepository.findAllByDepartmentAndLoginLikeIgnoreCaseAndFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(department, loginLike, firstnameLike, lastnameLike,paging);
        } else {
            return monkeyUserRepository.findAllByLoginLikeIgnoreCaseAndFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(loginLike, firstnameLike, lastnameLike,paging);
        }

    }

    public void addMonkeyUserRaw(MonkeyUser monkeyUser) {
        monkeyUser.setEnabled(true);
        // TODO: encode password on front?
        String rawPassword = monkeyUser.getPassword();
        monkeyUser.setPassword(passwordEncoder.encode(rawPassword));
        monkeyUserRepository.save(monkeyUser);
    }

    public MonkeyUser findMonkeyById(Long id) {
        Optional<MonkeyUser> optionalMonkeyUser = monkeyUserRepository.findById(id);
        if (optionalMonkeyUser.isEmpty()) {
            throw new IllegalStateException("Monkey with id = " + id + " not exists");
        }

        return optionalMonkeyUser.get();
    }

    public void deleteById(Long id) {
        Optional<MonkeyUser> monkeyUserOptional = monkeyUserRepository.findById(id);
        if(monkeyUserOptional.isEmpty()) {
            throw new IllegalStateException("Monkey with id " + id + " is already fired");
        }
        MonkeyUser user = monkeyUserOptional.get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals(user.getLogin())) {
            throw new IllegalStateException("Can`t remove your own logged-in monkey");
        }

        monkeyUserRepository.deleteById(id);
    }

    public void saveMonkeyUserChanges(MonkeyUser monkeyUser) {
        //TODO: In more elegant way handle pass?
        MonkeyUser objectFromDB = findMonkeyById(monkeyUser.getId());
        monkeyUser.setPassword(objectFromDB.getPassword());
        monkeyUser.setEnabled(true);

        monkeyUserRepository.save(monkeyUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MonkeyUser monkeyUser = monkeyUserRepository.findUserByLogin(username);
        if(monkeyUser == null) {
            throw new UsernameNotFoundException(username);
        }

        return monkeyUser;
    }

}
