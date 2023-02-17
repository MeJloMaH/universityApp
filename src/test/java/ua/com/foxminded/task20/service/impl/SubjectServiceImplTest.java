package ua.com.foxminded.task20.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import ua.com.foxminded.task20.model.Subject;
import ua.com.foxminded.task20.repository.SubjectRepository;
import ua.com.foxminded.task20.security.config.GeneralSecurityConfig;
import ua.com.foxminded.task20.security.config.SecurityConfig;
import ua.com.foxminded.task20.service.SubjectService;
import ua.com.foxminded.task20.service.impl.SubjectServiceImpl;

@SpringBootTest(classes = {SubjectServiceImpl.class, SecurityConfig.class, GeneralSecurityConfig.class})
class SubjectServiceImplTest {

    @MockBean
    SubjectRepository subjectRepository;

    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    SubjectService subjectService;

    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutWriteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> subjectService.save(new Subject("test")));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"write"})
    void shouldAllowUserWithWriteAuthority() {
        subjectService.save(new Subject("test"));
    }
    
    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutDeleteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> subjectService.deleteById(1L));
    }
   
    
    @Test
    @WithMockUser(username = "user", authorities = {"delete"})
    void shouldAllowUserWithDeleteAuthority() {
    	subjectService.deleteById(1L);
    }
}
