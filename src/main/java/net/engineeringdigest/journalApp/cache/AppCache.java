package net.engineeringdigest.journalApp.cache;

import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    private final ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> APP_CACHE;

    public AppCache(ConfigJournalAppRepository configJournalAppRepository){
        this.configJournalAppRepository = configJournalAppRepository;
    }

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> allEntity = configJournalAppRepository.findAll();
//        for (ConfigJournalAppEntity entity : allEntity){
//            appCache.put(entity.getKey(), entity.getValue());
//        }

        //instead above foreach, I am using stream
        if(!allEntity.isEmpty()) {
            allEntity.forEach(entity -> APP_CACHE.put(entity.getKey(), entity.getValue()));
        }
    }
}
