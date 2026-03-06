package net.engineeringdigest.journalApp.repository;


import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryImplTest {

//This test is just for to check mongodb query and criteria
    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    public void testFindUser(){
        List<User> users = userRepositoryImpl.getUserForSentimentAnalysis();
        Assertions.assertNotNull(users);
        assertEquals("kausila", users.get(0).getUserName());

    }

}
