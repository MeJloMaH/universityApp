package ua.com.foxminded.task20.controller.entities;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.task20.model.Subject;
import ua.com.foxminded.task20.service.SubjectService;
import ua.com.foxminded.task20.validation.SubjectValidator;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

	private SubjectValidator validator;

	protected final SubjectService subjectService;

	public SubjectController(SubjectService subjectService, SubjectValidator validator) {
		this.subjectService = subjectService;
		this.validator = validator;
	}

	@GetMapping
	public String showSubjects(Model model) {
		model.addAttribute("subjects", subjectService.findAll());
		return "subject/all_subjects";

	}

	@GetMapping("/new")
	public String createSubject(Model model) {
		model.addAttribute("subject", new Subject());
		return "subject/edit_subject";
	}

	@PostMapping()
	public String saveSubject(@ModelAttribute Subject subject, Model model) {

		if (validator.isValid(subject)) {
			subject.setId(null);
			subjectService.save(subject);
			return showSubjects(model);
		} else {
			model.addAttribute("errorName", validator.getConstraint());
			return createSubject(model);
		}
	}

	@GetMapping("/{id}")
	public String showSubject(@PathVariable Long id, Model model) {
		Optional<Subject> subject = subjectService.findById(id);
		if (subject.isEmpty()) {
			model.addAttribute("error", String.format("Subject with id=%d not found", id));
			return showSubjects(model);
		}

		model.addAttribute("subject", subject.get());
		return "subject/one_subject";
	}

	@PostMapping("/{id}")
	public String updateSubject(@PathVariable Long id, @ModelAttribute Subject subject, Model model) {
		if (subjectService.findById(id).isEmpty()) {
			model.addAttribute("error", String.format("Subject with id=%d not found", id));
		} else {
			if (validator.isValid(subject)) {
				subject.setId(null);
				subjectService.save(subject);
			} else {
				model.addAttribute("errorName", validator.getConstraint());
				return createSubject(model);
			}
		}
		return showSubjects(model);
	}

	@GetMapping("/{id}/edit")
	public String editSubject(@PathVariable Long id, Model model) {
		Optional<Subject> subject = subjectService.findById(id);
		if (subject.isEmpty()) {
			model.addAttribute("error", String.format("Subject with id=%d not found", id));
			return showSubjects(model);
		}

		model.addAttribute("subject", subject.get());
		return "subject/edit_subject";
	}

	@GetMapping("/{id}/delete")
	public String deleteSubject(@PathVariable Long id, Model model) {
		Optional<Subject> subject = subjectService.findById(id);
		if (subject.isEmpty()) {
			model.addAttribute("error", String.format("Subject with id=%d not found", id));
		} else {
			subjectService.deleteById(id);
		}
		return showSubjects(model);
	}

}
