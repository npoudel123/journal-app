package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;



    @Test
    public void testFindByUserName(){
        User user = new User();
        user.setUserName("testOne");
        user.setPassword("passwordOne");
        user.setRoles(Arrays.asList("USER"));
        when(userRepository.findByUserName(anyString())).thenReturn(user);

        User getUser = userService.findByUserName("testOne");

        assertNotNull(getUser);
        assertEquals("testOne", getUser.getUserName());
        verify(userRepository, times(1)).findByUserName("testOne");


    }

    //parameterized test example
    //****************************************************************
    //below first two case will pass and last case will fail
//    @ParameterizedTest
//    @CsvSource({
//            "1,1,2",
//            "4,5,9",
//            "3,3,5"
//    })
//    public void paramererizedTest(int a, int b, int expected){
//        assertEquals(a+b, expected);
//    }
}
