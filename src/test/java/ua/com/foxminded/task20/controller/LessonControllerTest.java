package ua.com.foxminded.task20.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.task20.controller.entities.LessonController;
import ua.com.foxminded.task20.model.Group;
import ua.com.foxminded.task20.model.Lesson;
import ua.com.foxminded.task20.model.Room;
import ua.com.foxminded.task20.model.Subject;
import ua.com.foxminded.task20.model.Teacher;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.security.enums.Status;
import ua.com.foxminded.task20.service.GroupService;
import ua.com.foxminded.task20.service.LessonService;
import ua.com.foxminded.task20.service.RoomService;
import ua.com.foxminded.task20.service.SubjectService;
import ua.com.foxminded.task20.service.TeacherService;
import ua.com.foxminded.task20.validation.LessonValidator;

@WebMvcTest(controllers = {LessonController.class})
class LessonControllerTest {

    @MockBean
    LessonService lessonService;

    @MockBean
    LessonValidator lessonValidator;
    
    @MockBean
    TeacherService teacherService;
    
    @MockBean
    SubjectService subjectService;
    
    @MockBean
    RoomService roomService;
    
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
    void shouldGetEmptyListOfLessons() throws Exception {
        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No Lessons available")));
    }
    
    @Test
    void shouldRedirectUnauthorisedUser() throws Exception {
        mockMvc.perform(get("/lessons"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "guest")
    void shouldGetListOfLessons() throws Exception {
        when(lessonService.findAll()).thenReturn(Arrays.asList(
                new Lesson(1L, "Alpha", 
                		new Group(1L, "Group1"), 1, LocalDate.of(2000, 3, 4), 
                		new Teacher(1L, "Teacher1", "Teacher1", "Teacher1", Role.TEACHER, Status.ACTIVE), 
                		new Subject(1L, "Subject1"), 
                		new Room(1L, "Room1", "1")),
                
                new Lesson(2L, "Beta", 
                		new Group(2L, "Group2"), 1, LocalDate.of(2000, 3, 5),
                		new Teacher(2L, "Teacher2", "Teacher2", "Teacher2", Role.TEACHER, Status.ACTIVE), 
                		new Subject(2L, "Subject2"), 
                		new Room(2L, "Room2", "2")),
                
                new Lesson(3L, "Gamma", 
                		new Group(3L, "Group3"), 1, LocalDate.of(2000, 3, 6), 
                		new Teacher(3L, "Teacher3", "Teacher3", "Teacher3", Role.TEACHER, Status.ACTIVE), 
                		new Subject(3L, "Subject3"), 
                		new Room(3L, "Room3", "3"))
        ));
        when(groupService.findAll()).thenReturn(Arrays.asList(
                new Group(1L, "Group1"),
                new Group(2L, "Group2"),
                new Group(3L, "Group3")
        ));      
        when(teacherService.findAll()).thenReturn(Arrays.asList(
        		new Teacher(1L, "Teacher1", "Teacher1", "Teacher1", Role.TEACHER, Status.ACTIVE),
        		new Teacher(2L, "Teacher2", "Teacher2", "Teacher2", Role.TEACHER, Status.ACTIVE),
        		new Teacher(3L, "Teacher3", "Teacher3", "Teacher3", Role.TEACHER, Status.ACTIVE)
        ));
        when(subjectService.findAll()).thenReturn(Arrays.asList(
                new Subject(1L, "Subject1"),
                new Subject(2L, "Subject2"),
                new Subject(3L, "Subject3")
        ));
        when(roomService.findAll()).thenReturn(Arrays.asList(
                new Room(1L, "Room1", "1"),
                new Room(2L, "Room2", "2"),
                new Room(3L, "Room3","3")
        ));
        
        mockMvc.perform(get("/lessons"))
                .andDo(print()) // for debug
                .andExpect(status().isOk())
                .andExpect(model().attribute("lessons", hasSize(3)))                
               
                .andExpect(content().string(containsString("Alpha")))
                .andExpect(content().string(containsString("Beta")))
                .andExpect(content().string(containsString("Gamma")))
                .andExpect(content().string(containsString("2000-03-04")))
                .andExpect(content().string(containsString("2000-03-05")))
                .andExpect(content().string(containsString("2000-03-06")));
    }
}