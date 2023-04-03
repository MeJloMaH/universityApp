package ua.com.foxminded.controller;

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

import ua.com.foxminded.controller.entities.SubjectController;
import ua.com.foxminded.model.Subject;
import ua.com.foxminded.service.SubjectService;
import ua.com.foxminded.validation.SubjectValidator;


@WebMvcTest(controllers = {SubjectController.class})
class SubjectControllerTest {

    @MockBean
    SubjectService subjectService;

    @MockBean
    SubjectValidator subjectValidator;
    
    @MockBean
    UserDetailsService userDetailsService;
    
    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "guest")
    void shouldGetEmptyListOfSubjects() throws Exception {
        mockMvc.perform(get("/subjects"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No Subjects available")));
    }
    
    @Test
    void shouldRedirectUnauthorisedUser() throws Exception {
        mockMvc.perform(get("/subjects"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "guest")
    void shouldGetListOfSubjects() throws Exception {
        when(subjectService.findAll()).thenReturn(Arrays.asList(
                new Subject(1L, "Alpha"),
                new Subject(2L, "Beta"),
                new Subject(3L, "Gamma")
        ));
        mockMvc.perform(get("/subjects"))
                .andDo(print()) // for debug
                .andExpect(status().isOk())
                .andExpect(model().attribute("subjects", hasSize(3)))
                .andExpect(content().string(containsString("Alpha")))
                .andExpect(content().string(containsString("Beta")))
                .andExpect(content().string(containsString("Gamma")));
    }
}