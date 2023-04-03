package ua.com.foxminded.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import ua.com.foxminded.repository.TeacherRepository;
import ua.com.foxminded.security.config.GeneralSecurityConfig;
import ua.com.foxminded.security.config.SecurityConfig;
import ua.com.foxminded.security.enums.Role;
import ua.com.foxminded.DTO.UserDTO;
import ua.com.foxminded.service.TeacherService;

@SpringBootTest(classes = {TeacherServiceImpl.class, SecurityConfig.class, GeneralSecurityConfig.class})
class TeacherServiceImplTest {

    @MockBean
    TeacherRepository teacherRepository;

    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    TeacherService teacherService;

    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutWriteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> teacherService.save(new UserDTO(null, "test", "test", "test", Role.TEACHER)));
    }
    
    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutDeleteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> teacherService.deleteById(1L));
    }
    
    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutAccessToUsersAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> teacherService.update(new UserDTO(null, "test", "test", "test", Role.TEACHER)));
    }
    
//  	↑ access  denied ↑
//  	↓ access allowed ↓

    @Test
    @WithMockUser(username = "user", authorities = {"access_to_teachers"})
    void shouldAllowUserWithAccessToTeachersAuthority() {
        teacherService.save(new UserDTO(null, "test", "test", "test", Role.TEACHER));
    }
       
    @Test
    @WithMockUser(username = "user", authorities = {"delete"})
    void shouldAllowUserWithDeleteAuthority() {
        teacherService.deleteById(1L);
    }
       
    @Test
    @WithMockUser(username = "user", authorities = {"access_to_teachers"})
    void shouldAllowUserWithoutAccessToTeachersAuthority() {
        teacherService.update(new UserDTO(null, "test", "test", "test", Role.TEACHER));
    }
}