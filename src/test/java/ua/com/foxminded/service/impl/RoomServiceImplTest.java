package ua.com.foxminded.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import ua.com.foxminded.security.config.GeneralSecurityConfig;
import ua.com.foxminded.security.config.SecurityConfig;
import ua.com.foxminded.model.Room;
import ua.com.foxminded.repository.RoomRepository;
import ua.com.foxminded.service.RoomService;

@SpringBootTest(classes = {RoomServiceImpl.class, SecurityConfig.class, GeneralSecurityConfig.class})
class RoomServiceImplTest {

    @MockBean
    RoomRepository roomRepository;

    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    RoomService roomService;

    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutWriteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> roomService.save(new Room("name", "location")));
    }
    
    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutDeleteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> roomService.deleteById(1L));
    }
    
    @Test
    @WithMockUser(username = "user", authorities = {"write"})
    void shouldAllowUserWithWriteAuthority() {
        roomService.save(new Room("name", "location"));
    }
      
    @Test
    @WithMockUser(username = "user", authorities = {"delete"})
    void shouldAllowUserWithDeleteAuthority() {
        roomService.deleteById(1L);
    }

}