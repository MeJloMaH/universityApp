package ua.com.foxminded.task20.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.task20.controller.entities.StudentController;
import ua.com.foxminded.task20.model.Group;
import ua.com.foxminded.task20.model.Student;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.security.enums.Status;
import ua.com.foxminded.task20.service.GroupService;
import ua.com.foxminded.task20.service.StudentService;
import ua.com.foxminded.task20.validation.StudentValidator;


@WebMvcTest(controllers = {StudentController.class})
class StudentControllerTest {

    @MockBean
    StudentService studentService;

    @MockBean
    StudentValidator studentValidator;
    
    @MockBean
    GroupService groupService;

    @MockBean
    UserDetailsService userDetailsService;
    
    @MockBean
    PasswordEncoder passwordEncoder;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "guest")
    void shouldGetEmptyListOfStudents() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No Students available")));
    }
    
    @Test
    void shouldRedirectUnauthorisedUser() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "guest")
    void shouldGetListOfStudents() throws Exception {  	
    	 when(studentService.findAll()).thenReturn(Arrays.asList(
                 new Student(1L, "Alpha", "Alpha", "Alpha", Role.STUDENT, Status.ACTIVE, new Group(1L, "")),
                 new Student(2L, "Beta", "Beta", "Beta", Role.STUDENT, Status.ACTIVE, new Group(2L, "")),
                 new Student(3L, "Gamma", "Gamma", "Gamma", Role.STUDENT, Status.ACTIVE, new Group(3L, ""))
         ));
    	 
    	 when(groupService.findAll()).thenReturn(Arrays.asList(
                 new Group(1L, ""),
                 new Group(2L, ""),
                 new Group(3L, "")
         ));
            
        mockMvc.perform(get("/students"))
                .andDo(print()) // for debug
                .andExpect(status().isOk())
                .andExpect(model().attribute("students", hasSize(3)))
                .andExpect(content().string(containsString("Alpha")))
                .andExpect(content().string(containsString("Beta")))
                .andExpect(content().string(containsString("Gamma")));
    }
}