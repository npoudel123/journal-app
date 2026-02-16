package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public boolean saveNewUser(User user){

        //we are doing this because if we are only updating username
        // in that case it will not encode already encoded password
        //Instead of using this, I create a new method below called UpdateUser and comment out this
//        if (!user.getPassword().startsWith("$2a$")) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Failed to save user", e);
            log.debug("test debug log level");
            return false;
        }
    }


    public void createNewAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN","USER"));
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
    public List<User> finalALlUser(){
        return userRepository.findAll();
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }


    public void deleteUserById(ObjectId id){
        userRepository.deleteById(id);
    }
    public void deleteUserByUserName(String username){
        userRepository.deleteByUserName(username);
    }

}
