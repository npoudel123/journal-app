package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal/v1")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    public JournalEntryController(JournalEntryService journalEntryService, UserService userService){
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }
    //Pass username and password through Auth(Basic Auth)
    @GetMapping
    public ResponseEntity<?> getAllJournalOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> newJournalList = user.getJournalEntries();
        if(!newJournalList.isEmpty()){
            return new ResponseEntity<>(newJournalList, HttpStatus.OK);
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No journal entries found for user: "+username);
    }

    //Pass username and password through Auth(Basic Auth)
    @PostMapping
    public ResponseEntity<JournalEntry>  createJournalEntryByUser(@RequestBody JournalEntry journalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            journalEntryService.saveEntry(journalEntry, username);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        return user.getJournalEntries().stream()
                .filter(entry -> entry.getId().equals(id))
                .findFirst()
                .map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteJournalById(id, username);
        if(removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> journalList = user.getJournalEntries().stream().filter(x-> x.getId().equals(id)).collect(Collectors.toList());

        if(!journalList.isEmpty()){
            Optional<JournalEntry> entry = journalEntryService.findJournalById(id);
            if(entry.isPresent()){
                JournalEntry updateEntry = entry.get();
                updateEntry.setTitle(journalEntry.getTitle() == null || journalEntry.getTitle().isEmpty() ?updateEntry.getTitle():journalEntry.getTitle());
                updateEntry.setContent(journalEntry.getContent() == null || journalEntry.getContent().isEmpty() ? updateEntry.getContent(): journalEntry.getContent());

                journalEntryService.saveEntry(updateEntry);
                return new ResponseEntity<>(updateEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
