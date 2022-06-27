package com.shyn.monkeysoft.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MonkeyUserService implements UserDetailsService {

    private final MonkeyUserRepository monkeyUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MonkeyUserService(MonkeyUserRepository monkeyUserRepository, PasswordEncoder passwordEncoder) {
        this.monkeyUserRepository = monkeyUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<MonkeyUser> findMonkeysPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<MonkeyUser> monkeyUsers = monkeyUserRepository.findAll();
        List<MonkeyUser> result;

        if (monkeyUsers.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, monkeyUsers.size());
            result = monkeyUsers.subList(startItem, toIndex);
        }

        return new PageImpl<>(result, PageRequest.of(currentPage, pageSize), monkeyUsers.size());
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
