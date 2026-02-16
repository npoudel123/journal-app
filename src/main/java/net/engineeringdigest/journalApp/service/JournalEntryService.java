package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    //-----------Commented out below line because I am using slf4j annotaion-----------
//    private static final Logger log = LoggerFactory.getLogger(JournalEntryService.class);

//    @Autowired
    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;

    //Using constructor injection
    public JournalEntryService( JournalEntryRepository journalEntryRepository, UserService userService){
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try {
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
//            user.setUserName(null); //This line is only for transactional test
            userService.saveUser(user);
        }catch (Exception e){
            log.error("Failed to save a journal {} :", journalEntry, e);
            throw new RuntimeException("Error occured while saving entry");
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);

    }

    public List<JournalEntry> findAllJournal(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findJournalById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteJournalById(ObjectId id, String username){
        boolean removed = false;
        User user = userService.findByUserName(username);
        removed = user.getJournalEntries().removeIf((x) -> x.getId().equals(id));
        try {
            if(removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("Exception occured while deleting journal ",e);
            throw new RuntimeException("An error occured while deleting journal",e);
        }
        return  removed;
    }
}
