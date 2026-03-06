package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    private AppCache appCache;

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

    @GetMapping("clear-app-cache")
    public void clearCache(){
        appCache.init();
    }
}
