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

import ua.com.foxminded.controller.entities.RoomController;
import ua.com.foxminded.model.Room;
import ua.com.foxminded.service.LessonService;
import ua.com.foxminded.service.RoomService;
import ua.com.foxminded.validation.RoomValidator;


@WebMvcTest(controllers = {RoomController.class})
class RoomControllerTest {

    @MockBean
    RoomService roomService;

    @MockBean
    RoomValidator roomValidator;
    
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
    void shouldGetEmptyListOfRooms() throws Exception {
        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No Rooms available")));
    }
    
    @Test
    void shouldRedirectUnauthorisedUser() throws Exception {
        mockMvc.perform(get("/rooms"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "guest")
    void shouldGetListOfRooms() throws Exception {
        when(roomService.findAll()).thenReturn(Arrays.asList(
                new Room(1L, "Alpha", "Loc1"),
                new Room(2L, "Beta", "Loc2"),
                new Room(3L, "Gamma", "Loc3")
        ));
        mockMvc.perform(get("/rooms"))
                .andDo(print()) // for debug
                .andExpect(status().isOk())
                .andExpect(model().attribute("rooms", hasSize(3)))
                .andExpect(content().string(containsString("Alpha")))
                .andExpect(content().string(containsString("Beta")))
                .andExpect(content().string(containsString("Gamma")));
    }
}