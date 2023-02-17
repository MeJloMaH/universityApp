package ua.com.foxminded.task20.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import ua.com.foxminded.task20.DTO.UserDTO;
import ua.com.foxminded.task20.repository.StudentRepository;
import ua.com.foxminded.task20.security.config.GeneralSecurityConfig;
import ua.com.foxminded.task20.security.config.SecurityConfig;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.service.StudentService;
import ua.com.foxminded.task20.service.impl.StudentServiceImpl;

@SpringBootTest(classes = {StudentServiceImpl.class, SecurityConfig.class, GeneralSecurityConfig.class})
class StudentServiceImplTest {

    @MockBean
    StudentRepository studentRepository;

    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    StudentService studentService;

    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutWriteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> studentService.save(new UserDTO(null, "test", "test", "test", Role.STUDENT)));
    }
    
    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutDeleteAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> studentService.deleteById(1L));
    }
    
    @Test
    @WithMockUser("user")
    void shouldDenyUserWithoutAccessToUsersAuthority() {
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () 
        		-> studentService.update(new UserDTO(null, "test", "test", "test", Role.STUDENT)));
    }
    
//  	↑ access  denied ↑
//  	↓ access allowed ↓
    
    @Test
    @WithMockUser(username = "user", authorities = {"write"})
    void shouldAllowUserWithWriteAuthority() {
        studentService.save(new UserDTO(null, "test", "test", "test", Role.STUDENT));
    }
    
    @Test
    @WithMockUser("user")
    void shouldAllowUserWithoutAuthorities() {
        studentService.register(new UserDTO(null, "test", "test", "test", Role.STUDENT));
    }
    
    @Test
    @WithMockUser(username = "user", authorities = {"delete"})
    void shouldAllowUserWithDeleteAuthority() {
        studentService.deleteById(1L);
    }
    
    @Test
    @WithMockUser(username = "user", authorities = {"access_to_users"})
    void shouldAllowUserWithoutAccessToUsersAuthority() {
        studentService.update(new UserDTO(null, "test", "test", "test", Role.STUDENT));
    }
}