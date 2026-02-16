package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;

    public PublicController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user){
        boolean created = userService.saveNewUser(user);

        if (created) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User created successfully");
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("User creation failed");
    }

}
