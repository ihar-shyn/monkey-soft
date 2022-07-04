package com.shyn.monkeysoft.api;

import com.shyn.monkeysoft.user.MonkeyUser;
import com.shyn.monkeysoft.user.MonkeyUserRepository;
import com.shyn.monkeysoft.user.MonkeyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserApiController {

    private final MonkeyUserService monkeyUserService;

    @Autowired
    public UserApiController(MonkeyUserService monkeyUserService) {
        this.monkeyUserService = monkeyUserService;
    }

    @GetMapping("users")
    public ResponseEntity<Map<String, Object>> getAllMonkeyUsers(@RequestParam(required = false) String login,
                                                                 @RequestParam(required = false) String firstname,
                                                                 @RequestParam(required = false) String lastname,
                                                                 @RequestParam(required = false) Long departmentId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size) {
        List<MonkeyUser> monkeyUsers;
        Pageable paging = PageRequest.of(page, size);
        Page<MonkeyUser> monkeyPage = monkeyUserService.getMonkeyUsersPage(login, firstname, lastname, departmentId, paging);

        monkeyUsers = monkeyPage.getContent(); // TODO: protect password hashes
        Map<String, Object> response = new HashMap<>();
        response.put("monkeys", monkeyUsers);
        response.put("currentPage", monkeyPage.getNumber());
        response.put("totalItems", monkeyPage.getTotalElements());
        response.put("totalPages", monkeyPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Map<String, Object>> removeMonkey(@PathVariable("id") Long userId) {
        try {
            monkeyUserService.deleteById(userId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (IllegalStateException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
