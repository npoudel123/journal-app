package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
//
//    @GetMapping
//    public List<User> getAllUser(){
//        return userService.finalALlUser();
//    }


    @PutMapping("/update-username")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInfo = userService.findByUserName(username);
        userInfo.setUserName(user.getUserName());
        userInfo.setPassword(userInfo.getPassword());
        userService.saveUser(userInfo);
        return new ResponseEntity<>("Update Successfully", HttpStatus.NO_CONTENT);

    }
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUserAndPassword(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInfo = userService.findByUserName(username);
        userInfo.setUserName(user.getUserName());
        userInfo.setPassword(user.getPassword());
        userService.saveNewUser(userInfo);
        return new ResponseEntity<>("Update Successfully", HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUserByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @PutMapping("/{username}")
//    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
//        User userInfo = userService.findByUserName(username);
//        if(userInfo != null){
//            userInfo.setUserName(user.getUserName());
//            userInfo.setPassword(user.getPassword());
//
//        }
//        try{
//            userService.saveUser(userInfo);
//            return new ResponseEntity<>("Update Successfully", HttpStatus.NO_CONTENT);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//    }



}
