package com.fdmgroup.qolab.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fdmgroup.qolab.model.Room;
import com.fdmgroup.qolab.model.Trainer;
import com.fdmgroup.qolab.repository.TrainerRepository;

/**
 * Controller class. 
 * 
 * @since 2020-10-23
 */
@Controller
@SessionAttributes(value= {"trainerLoggedIn", "activeRoom"}, types= {Trainer.class, Room.class})
public class AuthenticationController {
	
	@Autowired
	private Trainer trainer;

	@Autowired
	private TrainerRepository trainerRepo;

	@RequestMapping("/")
	public String showLogin(Model model) {
		model.addAttribute("trainer", trainer);
		model.addAttribute("message","");
		return "enter-room";
	}

	@RequestMapping("/login")
	public String showLoginSlug(Model model) {
		model.addAttribute("trainer", trainer);
		return "login";
	}

	@RequestMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("trainer", trainer);
		return "register";
	}

	@RequestMapping("/starter")
	public String showHome(Model model) {
		return "starter";
	}

	@RequestMapping("/registerTrainer")
	public String registerTrainer(@ModelAttribute Trainer trainer, Model model) {
		if(trainerRepo.findByUserName(trainer.getUsername()) != null) {
			System.out.println("****Registration Unsuccessful****\n" + trainer);
			model.addAttribute("registerErrorMsg", "Trainer already exists");
			return "register";
		}
		trainerRepo.save(trainer);
		System.out.println("****Registration Successful****\n" + trainer);
		model.addAttribute("trainer", trainer);
		return "login";
	}

	@RequestMapping("/loginTrainer")
	public String loginTrainer(@ModelAttribute Trainer trainer, Model model,HttpServletRequest request) throws CloneNotSupportedException {
		HttpSession session = request.getSession();
		Trainer trainerResult = trainerRepo.findByUserName(trainer.getUsername());

		if (trainerResult != null && trainerResult.getPassword().equals(trainer.getPassword())) {
			session.setAttribute("trainerLoggedIn", trainerResult);
			model.addAttribute("trainerLoggedIn", trainerResult);
			session.setAttribute("trainerUsername", trainer.getUsername());
			return "starter";
		}

		model.addAttribute("errorMsg","Invalid Login credentials");
		System.out.println("****Login Unsuccessful****\n" + trainer);
		return "login";
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/";
    }
}