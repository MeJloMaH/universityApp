package ua.com.foxminded.task20.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.task20.model.Group;
import ua.com.foxminded.task20.model.Lesson;
import ua.com.foxminded.task20.service.GroupService;
import ua.com.foxminded.task20.service.LessonService;
import ua.com.foxminded.task20.service.RoomService;
import ua.com.foxminded.task20.service.StudentService;
import ua.com.foxminded.task20.service.SubjectService;
import ua.com.foxminded.task20.service.TeacherService;

@Controller
@RequestMapping("/search")
public class SearchController {

	protected final StudentService studentService;
	protected final GroupService groupService;
	protected final LessonService lessonService;
	protected final TeacherService teacherService;
	protected final SubjectService subjectService;
	protected final RoomService roomService;

	public SearchController(StudentService studentService, GroupService groupService,
			LessonService lessonService, TeacherService teacherService, SubjectService subjectService,
			RoomService roomService) {

		this.studentService = studentService;
		this.groupService = groupService;
		this.lessonService = lessonService;
		this.teacherService = teacherService;
		this.subjectService = subjectService;
		this.roomService = roomService;
	}

	@GetMapping("/lesson/by_group_name")
	public String byGroupName(Model model) {
		model.addAttribute("group", new Group());
		model.addAttribute("groups", groupService.findAll());		
		return "searching/lesson/by_group_name";
	}

	@GetMapping("/lesson/by_date")
	public String byDate(Model model) {
		model.addAttribute("lesson", new Lesson());
		return "searching/lesson/by_date";
	}

	@GetMapping("/lesson/by_date_and_groupRef")
	public String byDateAndGroupRef(Model model) {
		model.addAttribute("lesson", new Lesson());
		model.addAttribute("groups", groupService.findAll());
		return "searching/lesson/by_date_and_groupRef";
	}

	@PostMapping("/lesson/result_by_group_name")
	public String findByGroupName(@ModelAttribute Group group, Model model) {
						
		List<Lesson> lessons = lessonService.findByGroupRef(group.getId());		
		
		model.addAttribute("lessons", lessons);

		return "searching/lesson/result_by_group_name";
	}

	@PostMapping("/lesson/result_by_date")
	public String findByDate(@ModelAttribute Lesson lesson, Model model) {

		LocalDate date = lesson.getDate();

		List<Lesson> lessons = lessonService.findByDate(date);		

		model.addAttribute("lessons", lessons);

		model.addAttribute("date", date);		

		return "searching/lesson/result_by_date";
	}

	@PostMapping("/lesson/result_by_date_and_groupRef")
	public String findByDateAndGroupRef(@ModelAttribute Lesson lesson, Model model) {
		
		Optional<Group> group = 
		groupService.findById(lesson.getGroup().getId());
		
		if(group.isEmpty()) {
			model.addAttribute("error", String.format("Group with name=%s not found", lesson.getGroup().getName()));
            return byDateAndGroupRef(model);
		}

		List<Lesson> list = lessonService.findByDateAndGroupRef(lesson.getDate(), group.get().getId());
		
		model.addAttribute("lessons", list);

		return "searching/lesson/result_by_date_and_groupRef";
	}

}
