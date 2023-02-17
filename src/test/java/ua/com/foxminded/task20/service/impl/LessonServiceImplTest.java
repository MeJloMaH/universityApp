package ua.com.foxminded.task20.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import ua.com.foxminded.task20.model.Lesson;
import ua.com.foxminded.task20.repository.LessonRepository;
import ua.com.foxminded.task20.security.config.GeneralSecurityConfig;
import ua.com.foxminded.task20.security.config.SecurityConfig;
import ua.com.foxminded.task20.service.LessonService;
import ua.com.foxminded.task20.service.impl.LessonServiceImpl;

@SpringBootTest(classes = {LessonServiceImpl.class, SecurityConfig.class, GeneralSecurityConfig.class})
class LessonServiceImplTest {

    @MockBean
    LessonRepository lessonRepository;

    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    LessonService lessonService;

    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutWriteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> lessonService.save(new Lesson()));
    }
    
    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutDeleteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> lessonService.deleteById(1L));
    }   

    @Test
    @WithMockUser(username = "user", authorities = {"write"})
    void shouldAllowUserWithWriteAuthority() {
        lessonService.save(new Lesson());
    }
    
    @Test
    @WithMockUser(username = "user", authorities = {"delete"})
    void shouldAllowUserWithDeleteAuthority() {
    	lessonService.deleteById(1L);
    }

}