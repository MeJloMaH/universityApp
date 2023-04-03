package ua.com.foxminded.service.generator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.security.enums.Role;
import ua.com.foxminded.DTO.UserDTO;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.model.Room;
import ua.com.foxminded.model.Subject;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.GroupService;
import ua.com.foxminded.service.LessonService;
import ua.com.foxminded.service.RoomService;
import ua.com.foxminded.service.StudentService;
import ua.com.foxminded.service.SubjectService;
import ua.com.foxminded.service.TeacherService;
import ua.com.foxminded.service.UserService;

@Component
public class DataGenerator implements ApplicationRunner {

    private final GroupService groupService;
    private final LessonService lessonService;
    private final RoomService roomService;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final UserService userService;

    @Value("${system.user.password}")
    private String systemUserPassword;
    private static final String[] FirstNames = {"Ivan", "Petya", "Kolya", "Dmitriy", "Andrey", "Zahar", "Nazar",
            "Olya", "Maria", "Nadiya", "Christina", "Oleh", "Roman", "Natalya", "Anton", "Stepan", "Mihail", "Myron",
            "Sergei", "Tetiana"};

    private static final String[] LastNames = {"Ivanov", "Petrov", "Alexandrov", "Dmitrov", "Andreev", "Zacharov",
            "Nazarov", "Olejnik", "Marianov", "Zhdanov", "Christiforov", "Olehov", "Romanov", "Natanov", "Antonov",
            "Stepanov", "Maydanov", "Myronov", "Sergeev", "Tayanov"};

    private static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private static final String[] Rooms = {"Gymnasium", "Swimming-pool", "School library", "School museum",
            "Classroom for Chemistry", "Classroom for Biology", "Classroom for Physics", "Classroom for Geography",
            "Workshops", "Room for manual works for girls"};

    private static final String[] Subjects = {"Math", "Physics", "Economics", "Geography", "Geology", "Geometry",
            "Chemistry", "Biology", "Algebra", "Literatura"};

    public DataGenerator(GroupService groupService, LessonService lessonService, RoomService roomService,
                         StudentService studentService, SubjectService subjectService, TeacherService teacherService, UserService userService) {

        this.groupService = groupService;
        this.lessonService = lessonService;
        this.roomService = roomService;
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.userService = userService;
    }

    private static final int GROUPS_COUNT = 5;
    private static final int DAY_COUNT = 30;
    private static final int LESSONS_PER_DAY_COUNT = 4;
    private static final int STUDENTS_COUNT = 20;
    private static final int LESSONS_COUNT = 100;
    private static final int TEACHERS_COUNT = 5;
    private static final int ROOMS_COUNT = 7;
    private static final int SUBJECTS_COUNT = 10;

    List<Group> groups = new ArrayList<>();
    List<Room> rooms = new ArrayList<>();
    List<Subject> subjects = new ArrayList<>();
    List<UserDTO> students = new ArrayList<>();
    List<UserDTO> teachers = new ArrayList<>();


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        userService.doWithSystemUser(this::generate);
    }

    private void generate() {
        Random random = new Random();


        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < GROUPS_COUNT; i++) {
            String groupName = Character.toString(alphabet[random.nextInt(26)])
                    + Character.toString((char) alphabet[random.nextInt(26)]) + "-"
                    + ThreadLocalRandom.current().nextInt(10, 99);
            if (names.contains(groupName)) {
                i--;
                continue;
            }
            Group gr = new Group(groupName);
            groups.add(gr);
            names.add(groupName);
        }


        for (int i = 0; i < STUDENTS_COUNT; i++) {

            String studentName = FirstNames[random.nextInt(20)] + " " + LastNames[random.nextInt(20)];
            String login = "s" + i;
            String password = "s" + i;

            UserDTO st = new UserDTO(studentName, login, password);
            students.add(st);
        }

        for (int i = 0; i < TEACHERS_COUNT; i++) {
            String teacherName = FirstNames[random.nextInt(20)] + " " + LastNames[random.nextInt(20)];
            String login = "t" + i;
            String password = "t" + i;

            UserDTO teacher = new UserDTO(teacherName, login, password);

            teachers.add(teacher);
        }

        for (int i = 0; i < ROOMS_COUNT; i++) {
            String roomName = Rooms[random.nextInt(10)];
            String roomLocation = String.valueOf(i);

            Room r = new Room(roomName, roomLocation);
            rooms.add(r);
        }

        for (String subject : Subjects) {
            Subject sbj = new Subject(subject);
            subjects.add(sbj);
        }

        if (groupService.findAll().isEmpty()) {
            for (Group gr : groups) {
                groupService.save(gr);
            }
        }

        if (studentService.findAll().isEmpty()) {
            for (UserDTO st : students) {
                studentService.register(st);
            }
        }

        if (teacherService.findAll().isEmpty()) {
            for (UserDTO teacher : teachers) {
                teacherService.save(teacher);
            }
            userService.save(new UserDTO(null, "admin", "admin", "admin", "admin", "no", Role.ADMIN));
            userService.save(new UserDTO(null, "user", "user", "user", "user", "no", Role.USER));
            teacherService.save(new UserDTO(null, "teacher", "teacher", "teacher", "teacher", "no", Role.TEACHER));
            studentService.save(new UserDTO(null, "student", "student", "student", "student", "no", Role.STUDENT));
        }

        if (roomService.findAll().isEmpty()) {
            for (Room r : rooms) {
                roomService.save(r);
            }
        }

        if (subjectService.findAll().isEmpty()) {
            for (Subject s : subjects) {
                subjectService.save(s);
            }
        }

        if (lessonService.findAll().isEmpty()) {

            for (int i = 0; i < LESSONS_COUNT; i++) {
                String lessonName = Subjects[random.nextInt(10)];
                Subject subject = subjects.get(random.nextInt(SUBJECTS_COUNT));
                Room room = rooms.get(random.nextInt(ROOMS_COUNT));
                Group group = groups.get(random.nextInt(GROUPS_COUNT));
                Teacher teacher = teacherService.findByLogin(teachers.get(random.nextInt(TEACHERS_COUNT)).getLogin()).get();

                lessonService.save(
                        new Lesson(lessonName,
                                group,
                                random.nextInt(LESSONS_PER_DAY_COUNT),
                                LocalDate.of(2022, 9, 1).plusDays(i),
                                teacher,
                                subject,
                                room)
                );
            }

        }
    }

}
