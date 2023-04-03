package ua.com.foxminded.controller.entities;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.model.Room;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.GroupService;
import ua.com.foxminded.service.LessonService;
import ua.com.foxminded.service.RoomService;
import ua.com.foxminded.service.SubjectService;
import ua.com.foxminded.service.TeacherService;
import ua.com.foxminded.validation.LessonValidator;

@Controller
@RequestMapping("/lessons")
public class LessonController {

	private LessonValidator validator;
	protected final LessonService lessonService;
	protected final RoomService roomService;
	protected final TeacherService teacherService;
	protected final SubjectService subjectService;
	protected final GroupService groupService;

	public LessonController(LessonService lessonService, RoomService roomService,
			TeacherService teacherService, SubjectService subjectService,
			GroupService groupService, LessonValidator validator) {
		this.lessonService = lessonService;
		this.roomService = roomService;
		this.teacherService = teacherService;
		this.subjectService = subjectService;
		this.groupService = groupService;
		this.validator = validator;
	}

	@GetMapping
	public String showLessons(Model model) {
		model.addAttribute("lessons", lessonService.findAll());

		return "lesson/all_lessons";
	}

	@GetMapping("/new")
	public String createLesson(Model model) {
		model.addAttribute("lesson", new Lesson());

		model.addAttribute("teachers", teacherService.findAll());
		model.addAttribute("subjects", subjectService.findAll());
		model.addAttribute("rooms", roomService.findAll());
		model.addAttribute("groups", groupService.findAll());
		return "lesson/edit_lesson";
	}

	@PostMapping()
	public String saveLesson(@ModelAttribute Lesson lesson, Model model) {

		if (validator.isValid(lesson)) {
			lesson.setId(null);
			lessonService.save(lesson);
			return showLessons(model);
			
		} else {
			model.addAttribute("errorName", validator.getConstraint());
			return createLesson(model);
		}
	}

	@GetMapping("/{id}")
	public String showLesson(@PathVariable Long id, Model model) {
		Optional<Lesson> lesson = lessonService.findById(id);
		if (lesson.isEmpty()) {
			model.addAttribute("error", String.format("Lesson with id=%d not found", id));
			return showLessons(model);
		}

		model.addAttribute("lesson", lesson.get());
		return "lesson/one_lesson";
	}

	@PostMapping("/{id}")
	public String updateLesson(@PathVariable Long id, @ModelAttribute Lesson lesson, Model model) {
		if (lessonService.findById(id).isEmpty()) {
			model.addAttribute("error", String.format("Lesson with id=%d not found", id));
		}else {
			if (validator.isValid(lesson)) {
				lesson.setId(id);
				lessonService.save(lesson);
			} else {
				model.addAttribute("errorName", validator.getConstraint());
				return createLesson(model);
			}
		}
		return showLessons(model);
	}

	@GetMapping("/{id}/edit")
	public String editLesson(@PathVariable Long id, Model model) {
		Optional<Lesson> lesson = lessonService.findById(id);
		if (lesson.isEmpty()) {
			model.addAttribute("error", String.format("Lesson with id=%d not found", id));
			return showLessons(model);
		}

		model.addAttribute("lesson", lesson.get());
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("teachers", teacherService.findAll());
		model.addAttribute("subjects", subjectService.findAll());
		model.addAttribute("rooms", roomService.findAll());
		return "lesson/edit_lesson";
	}

	@GetMapping("/{id}/delete")
	public String deleteLesson(@PathVariable Long id, Model model) {
		Optional<Lesson> lesson = lessonService.findById(id);
		if (lesson.isEmpty()) {
			model.addAttribute("error", String.format("Lesson with id=%d not found", id));
		} else {
			lessonService.deleteById(id);
		}
		return showLessons(model);
	}

	@PostMapping("/group/{id}")
	public String showLessonsByGroup(@ModelAttribute Group group, Model model) {

		model.addAttribute("lessons", lessonService.findByGroupRef(group.getId()));

		return "lesson/lessons_by_group";
	}

	@PostMapping("/room/{id}")
	public String showLessonsByRoom(@ModelAttribute Room room, Model model) {

		model.addAttribute("lessons", lessonService.findByRoomRef(room.getId()));

		return "lesson/lessons_by_room";
	}

	@PostMapping("/teacher/{id}")
	public String showLessonsByTeacher(@ModelAttribute Teacher teacher, Model model) {

		model.addAttribute("lessons", lessonService.findByTeacherRef(teacher.getId()));

		return "lesson/lessons_by_teacher";
	}
}
