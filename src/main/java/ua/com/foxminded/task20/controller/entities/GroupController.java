package ua.com.foxminded.task20.controller.entities;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.task20.model.Group;
import ua.com.foxminded.task20.service.GroupService;
import ua.com.foxminded.task20.validation.GroupValidator;

@Controller
@RequestMapping("/groups")
public class GroupController {

	private GroupValidator validator;

	protected final GroupService groupService;

	public GroupController(GroupService groupService, GroupValidator validator) {
		this.groupService = groupService;
		this.validator = validator;
	}

	@GetMapping
	public String showGroups(Model model) {
		model.addAttribute("groups", groupService.findAll());
		return "group/all_groups";
	}

	@GetMapping("/new")
	public String createGroup(Model model) {
		model.addAttribute("group", new Group());
		return "group/edit_group";
	}

	@PostMapping
	public String saveGroup(@ModelAttribute Group group, Model model) {

		if (validator.isValid(group)) {
			group.setId(null);
			groupService.save(group);

			return showGroups(model);
		} else {
			model.addAttribute("errorName", validator.getConstraint());
			return createGroup(model);
		}

	}

	@GetMapping("/{id}")
	public String showGroup(@PathVariable Long id, Model model) {
		Optional<Group> group = groupService.findById(id);
		if (group.isEmpty()) {
			model.addAttribute("error", String.format("Group with id=%d not found", id));
			return showGroups(model);
		}

		model.addAttribute("group", group.get());
		return "group/one_group";
	}

	@PostMapping("/{id}")
	public String updateGroup(@PathVariable Long id, @ModelAttribute Group group, Model model) {
		if (groupService.findById(id).isEmpty()) {
			model.addAttribute("error", String.format("Group with id=%d not found", id));
		} else {
			if (validator.isValid(group)) {
				group.setId(id);
				groupService.save(group);
				
			}else {
				model.addAttribute("errorName", validator.getConstraint());
				return createGroup(model);
			}
		}
		return showGroups(model);
	}

	@GetMapping("/{id}/edit")
	public String editGroup(@PathVariable Long id, Model model) {
		Optional<Group> group = groupService.findById(id);
		if (group.isEmpty()) {
			model.addAttribute("error", String.format("Group with id=%d not found", id));
			return showGroups(model);
		}

		model.addAttribute("group", group.get());
		return "group/edit_group";
	}

	@GetMapping("/{id}/delete")
	public String deleteGroup(@PathVariable Long id, Model model) {
		Optional<Group> group = groupService.findById(id);
		if (group.isEmpty()) {
			model.addAttribute("error", String.format("Group with id=%d not found", id));
		} else {
			groupService.deleteById(id);
		}
		return showGroups(model);
	}

}
