package com.fdmgroup.qolab.controllers;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.qolab.Utility.HibernateProxyTypeAdapter;
import com.fdmgroup.qolab.Utility.RoomCodeGenerator;
import com.fdmgroup.qolab.model.Challenge;
import com.fdmgroup.qolab.model.Trainer;
import com.fdmgroup.qolab.repository.ChallengeRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fdmgroup.qolab.model.Room;
import com.fdmgroup.qolab.model.Trainee;
import com.fdmgroup.qolab.repository.RoomRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Controller class. 
 * 
 *
 * @since 2020-10-23
 */
@Controller
@SessionAttributes(value= {"trainerLoggedIn", "roomPin", "room"}, types= {Trainer.class, String.class, Room.class})
public class TrainerController {
	
	@Autowired
	private ChallengeRepository challengeRepo;
	
	@Autowired
	private RoomRepository roomRepo;
	
	
	@RequestMapping(value="/trainer-page") 
	public String loadTrainerPage(Model model, @ModelAttribute("roomPin") String roomPin) {
		Room room = roomRepo.findRoomByKey(roomPin);
		
		List<Trainee> trainees = room.getTrainees();
		System.out.println(trainees);
		model.addAttribute("trainees", trainees);
		return "trainerRoom";
	}
	
	@RequestMapping(value="/load-all-challenge")
	public String loadAllChallenge(Model model) {
		model.addAttribute("challengeList", challengeRepo.findAll());
		return "load-room";
	}
	
	@RequestMapping(value="/load-room", method = RequestMethod.POST)
	public String loadChallenge(Model model, @ModelAttribute("trainerLoggedIn") Trainer trainerLoggedIn,  HttpServletRequest request) {
		String challengeName = request.getParameter("challengeName");
		String problemStatement = request.getParameter("problemStatement");
		Challenge challenge = new Challenge();
		challenge.setProblemStatement(problemStatement);
		challenge.setChallengeName(challengeName);
		challenge.setTrainer(trainerLoggedIn);
		challengeRepo.save(challenge);
		
		return "redirect:/load-all-challenge";
	}

	@GetMapping(value="/delete-load-room/{challengeId}")
	public String deleteChallenge(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("challengeId") int challengeId) {
		challengeRepo.deleteById(challengeId);
		return "redirect:/load-all-challenge";
	}
	
	@GetMapping(value="/launch-load-room/{challengeId}")
	public String launchChallenge(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("trainerLoggedIn") Trainer trainerLoggedIn, @PathVariable("challengeId") int challengeId) throws CloneNotSupportedException {
		System.out.println(challengeRepo.findById(challengeId));
		HttpSession session = request.getSession();
		Optional<Challenge> challenge = challengeRepo.findById(challengeId);
		if (challenge.isPresent()) {
			Challenge ch = challenge.get();
			String roomKey = RoomCodeGenerator.roomIdGenerator();
			model.addAttribute("roomPin", roomKey);
			Room room = new Room(roomKey, trainerLoggedIn, ch.getProblemStatement(), true);
			room.setTrainees(new ArrayList<Trainee>());
			room.setChallenge(ch);
			roomRepo.save(room);
			session.setAttribute("room", room);
			
			session.setAttribute("roomId", room.getRoomId());
			
			System.out.println(roomKey);
			return "redirect:/trainer-page";
		}
		return "redirect:/load-all-challenge";
	}
	
}
