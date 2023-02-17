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

import ua.com.foxminded.task20.controller.entities.GroupController;
import ua.com.foxminded.task20.model.Group;
import ua.com.foxminded.task20.service.GroupService;
import ua.com.foxminded.task20.service.LessonService;
import ua.com.foxminded.task20.validation.GroupValidator;

@WebMvcTest(controllers = {GroupController.class})
class GroupControllerTest  {
	
	@MockBean
	GroupService groupService;

    @MockBean
    GroupValidator groupValidator;
	
	@MockBean
	LessonService lessonService;
	
	@MockBean
    UserDetailsService userDetailsService;
	
	@MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "guest")
    void shouldGetEmptyListOfGroups() throws Exception {
        mockMvc.perform(get("/groups"))
        		.andExpect(status().isOk())
        		.andExpect(content().string(containsString("No Groups available")));
    }
    
    @Test
    void shouldRedirectUnauthorisedUser() throws Exception {
        mockMvc.perform(get("/groups"))
                .andExpect(status().is3xxRedirection());
    }
    
    @Test
    @WithMockUser(username = "guest")
    void shouldGetListOfRooms() throws Exception {
        when(groupService.findAll()).thenReturn(Arrays.asList(
                new Group("Alpha"),
                new Group("Beta"),
                new Group("Gamma")
        ));
        mockMvc.perform(get("/groups"))
                .andDo(print()) // for debug
                .andExpect(status().isOk())
                .andExpect(model().attribute("groups", hasSize(3)))
                .andExpect(content().string(containsString("Alpha")))
                .andExpect(content().string(containsString("Beta")))
                .andExpect(content().string(containsString("Gamma")));
    }
}