package ua.com.foxminded.controller.entities;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.model.Room;
import ua.com.foxminded.service.RoomService;
import ua.com.foxminded.validation.RoomValidator;

@Controller
@RequestMapping("/rooms")
public class RoomController {

	private RoomValidator validator;

	protected final RoomService roomService;

	public RoomController(RoomService roomService, RoomValidator validator) {
		this.roomService = roomService;
		this.validator = validator;
	}

	@GetMapping
	public String showRooms(Model model) {
		model.addAttribute("rooms", roomService.findAll());
		return "room/all_rooms";
	}

	@GetMapping("/new")
	public String createRoom(Model model) {
		model.addAttribute("room", new Room());
		return "room/edit_room";
	}

	@PostMapping()
	public String saveRoom(@ModelAttribute Room room, Model model) {

		if (validator.isValid(room)) {
			room.setId(null);
			roomService.save(room);
			return showRooms(model);
		} else {
			model.addAttribute("errorName", validator.getConstraint());
			return createRoom(model);
		}

	}

	@GetMapping("/{id}")
	public String showRoom(@PathVariable Long id, Model model) {
		Optional<Room> room = roomService.findById(id);
		if (room.isEmpty()) {
			model.addAttribute("error", String.format("Room with id=%d not found", id));
			return showRooms(model);
		}

		model.addAttribute("room", room.get());
		return "room/one_room";
	}

	@PostMapping("/{id}")
	public String updateRoom(@PathVariable Long id, @ModelAttribute Room room, Model model) {
		if (roomService.findById(id).isEmpty()) {
			model.addAttribute("error", String.format("Room with id=%d not found", id));
		} else {
			if (validator.isValid(room)) {
				room.setId(id);
				roomService.save(room);
			}else {
				model.addAttribute("errorName", validator.getConstraint());
				return createRoom(model);
			}
		}
		return showRooms(model);
	}

	@GetMapping("/{id}/edit")
	public String editRoom(@PathVariable Long id, Model model) {
		Optional<Room> room = roomService.findById(id);
		if (room.isEmpty()) {
			model.addAttribute("error", String.format("Room with id=%d not found", id));
			return showRooms(model);
		}

		model.addAttribute("room", room.get());
		return "room/edit_room";
	}

	@GetMapping("/{id}/delete")
	public String deleteRoom(@PathVariable Long id, Model model) {
		Optional<Room> room = roomService.findById(id);
		if (room.isEmpty()) {
			model.addAttribute("error", String.format("Room with id=%d not found", id));
		} else {
			roomService.deleteById(id);
		}
		return showRooms(model);
	}

}
