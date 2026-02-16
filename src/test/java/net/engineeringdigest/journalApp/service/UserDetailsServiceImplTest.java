package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;


    @Test
    public void shouldReturnUserWhenUserExists(){

        //-----------Arrange----------
        User user = new User();
        user.setUserName("tom");
        user.setPassword("tom");
        user.setRoles(Collections.singletonList("USER"));
        when(userRepository.findByUserName(anyString())).thenReturn(user);

        //------Act to get arraned user----------
        UserDetails userDetails = userDetailsService.loadUserByUsername("tom");

        //-------Assert--------
        assertNotNull(userDetails);
        assertEquals("tom", userDetails.getUsername());


    }
}
