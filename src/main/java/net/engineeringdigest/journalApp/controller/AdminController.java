package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUser = userService.finalALlUser();
        if (allUser != null && !allUser.isEmpty()) {
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        }
        return new ResponseEntity<>("Users not found", HttpStatus.NOT_FOUND);

    }

    @PostMapping("/create-admin")
    public void createUser(@RequestBody User user) {

        userService.createNewAdmin(user);

    }
}
