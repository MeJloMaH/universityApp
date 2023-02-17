package ua.com.foxminded.task20.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import ua.com.foxminded.task20.model.Group;
import ua.com.foxminded.task20.repository.GroupRepository;
import ua.com.foxminded.task20.security.config.GeneralSecurityConfig;
import ua.com.foxminded.task20.security.config.SecurityConfig;
import ua.com.foxminded.task20.service.GroupService;
import ua.com.foxminded.task20.service.impl.GroupServiceImpl;

@SpringBootTest(classes = {GroupServiceImpl.class, SecurityConfig.class, GeneralSecurityConfig.class})
class GroupServiceImplTest {

    @MockBean
    GroupRepository groupRepository;

    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    GroupService groupService;

    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutWriteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () -> groupService.save(new Group("test")));
    }
    
    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutDeleteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> groupService.deleteById(1L));
    }
    
    @Test
    @WithMockUser(username = "user", authorities = {"write"})
    void shouldAllowUserWithWriteAuthority() {
        groupService.save(new Group("test"));
    }
    
    @Test
    @WithMockUser(username = "user", authorities = {"delete"})
    void shouldAllowUserWithDeleteAuthority() {
    	groupService.deleteById(1L);
    }
    
   
}