package ua.com.foxminded.task20.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import ua.com.foxminded.task20.DTO.UserDTO;
import ua.com.foxminded.task20.repository.UserRepository;
import ua.com.foxminded.task20.security.config.GeneralSecurityConfig;
import ua.com.foxminded.task20.security.config.SecurityConfig;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.service.UserService;
import ua.com.foxminded.task20.service.impl.UserServiceImpl;

@SpringBootTest(classes = {UserServiceImpl.class, SecurityConfig.class, GeneralSecurityConfig.class})
class UserServiceImplTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutAccessToUsersAuthority() {
    	
    	
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> userService.save(new UserDTO(null, "test", "test", "test", Role.USER)));
        
        AccessDeniedException ex1 = assertThrows(AccessDeniedException.class, () 
        		-> userService.update(new UserDTO(null, "test", "test", "test", Role.USER)));
        
        AccessDeniedException ex2 = assertThrows(AccessDeniedException.class, () 
        		-> userService.changeUserType(new UserDTO(null, "test", "test", "test", Role.ADMIN)));
    }
    
    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutDeleteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> userService.deleteById(1L));
    }
    
    
    
//  	↑ access  denied ↑
//  	↓ access allowed ↓

    @Test
    @WithMockUser(username = "user", authorities = {"access_to_users"})
    void shouldAllowUserWithAccessToUsersAuthority() {
        userService.save(new UserDTO(null, "test", "test", "test", Role.USER));
        
        userService.update(new UserDTO(null, "test", "test", "test", Role.USER));
        
        userService.changeUserType(new UserDTO(null, "test", "test", "test", Role.ADMIN));
    }
     
    @Test
    @WithMockUser(username = "user", authorities = {"delete"})
    void shouldAllowUserWithDeleteAuthority() {
        userService.deleteById(1L);
    }    
        
    @Test
    @WithMockUser("user")
    void shouldAllowUserWithoutAuthorities() {
    	userService.register(new UserDTO(null, "test", "test", "test", Role.USER));
    }
}