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

import ua.com.foxminded.task20.controller.entities.TeacherController;
import ua.com.foxminded.task20.model.Teacher;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.security.enums.Status;
import ua.com.foxminded.task20.service.GroupService;
import ua.com.foxminded.task20.service.TeacherService;
import ua.com.foxminded.task20.validation.TeacherValidator;

@WebMvcTest(controllers = {TeacherController.class})
class TeacherControllerTest {

    @MockBean
    TeacherService teacherService;

    @MockBean
    TeacherValidator teacherValidator;
    
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
    void shouldGetEmptyListOfTeachers() throws Exception {
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No Teachers available")));
    }
    
    @Test
    void shouldRedirectUnauthorisedUser() throws Exception {
        mockMvc.perform(get("/teachers"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "guest")
    void shouldGetListOfTeachers() throws Exception {
        when(teacherService.findAll()).thenReturn(Arrays.asList(
                new Teacher(1L, "Alpha", "Alpha", "Alpha", Role.TEACHER, Status.ACTIVE),
                new Teacher(2L, "Beta", "Beta", "Beta", Role.TEACHER, Status.ACTIVE),
                new Teacher(3L, "Gamma", "Gamma", "Gamma", Role.TEACHER, Status.ACTIVE)
        ));
        mockMvc.perform(get("/teachers"))
                .andDo(print()) // for debug
                .andExpect(status().isOk())
                .andExpect(model().attribute("teachers", hasSize(3)))
                .andExpect(content().string(containsString("Alpha")))
                .andExpect(content().string(containsString("Beta")))
                .andExpect(content().string(containsString("Gamma")));
    }
}