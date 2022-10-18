package com.fdmgroup.qolab.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fdmgroup.qolab.Utility.CloneClass;
import com.fdmgroup.qolab.Utility.HibernateProxyTypeAdapter;
import com.fdmgroup.qolab.Utility.StringCompiler;
import com.fdmgroup.qolab.model.Room;
import com.fdmgroup.qolab.model.SourceFile;
import com.fdmgroup.qolab.model.Trainee;
import com.fdmgroup.qolab.model.Trainer;
import com.fdmgroup.qolab.repository.RoomRepository;
import com.fdmgroup.qolab.repository.TraineeRepository;
import com.fdmgroup.qolab.repository.TrainerRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Controller class. 
 * 
 *
 * @since 2020-10-23
 */
@Controller
@SessionAttributes(value= {"traineeLoggedIn", "activeRoom"}, types= {Trainee.class, Room.class})
public class TraineeController {
	
//	@Autowired
//	private Room room;

	@Autowired
	private RoomRepository roomRepo;
	
	@Autowired
	private TraineeRepository traineeRepository;

	@RequestMapping("/trainee_main")
	public String showTraineeMain() {
		return "trainee-main";
	}

	@RequestMapping("/show_trainee_main")
	public String showTraineeMain(Model model, @RequestParam String traineeName, HttpServletRequest request) throws CloneNotSupportedException {
		model.addAttribute("programOutput", new String());
		
		HttpSession session = request.getSession();
		Room activeRoom = (Room)  session.getAttribute("activeRoom");
		
		System.out.println(activeRoom);

		for (Trainee t : traineeRepository.findByRoomId(activeRoom.getRoomId())) {
			if (t.getName().equals(traineeName)) {
				model.addAttribute("message", "That Name Already Exists In This Room");
				return "enter-name";
			}
		}
		
		List<SourceFile> sourceFiles = new ArrayList<SourceFile>();
		sourceFiles.add(new SourceFile("public class Main {\r\n\tpublic static void main(String[] args){\r\n\t\r\n\t}\r\n}", "Main", null));
		Trainee newTrainee = new Trainee(traineeName, sourceFiles, activeRoom, false, false, false);
		traineeRepository.save(newTrainee);
		model.addAttribute("traineeLoggedIn", newTrainee);

		session.setAttribute("traineeName", newTrainee.getName());
		System.out.println("setting traineeRoomId to:" + activeRoom.getRoomId());
		session.setAttribute("traineeRoomId", activeRoom.getRoomId());
		
		return "redirect:/trainee_main";
	}

	@RequestMapping("/show_enter_name")
    public String enterRoom(Model model, @RequestParam String roomKey, HttpServletRequest request) {
        
		HttpSession session = request.getSession();
		
		Room room = roomRepo.findRoomByKey(roomKey);
        if (room != null) {
            session.setAttribute("activeRoom", room);
            model.addAttribute("message", "");
            return "enter-name";
        }
        else {
            model.addAttribute("message", "Room Does Not Exist");
            return "enter-room";
        }
    }
	
//	@RequestMapping("/compile_code")
//	public String compileCode(Model model, @ModelAttribute("traineeLoggedIn") Trainee traineeLoggedIn, @RequestParam String[] sourceFileNames, @RequestParam String[] sourceFileContents) {	
//
//		for(int i=0; i < sourceFileNames.length; i++) {
//			if (traineeLoggedIn.getSourceFiles().size() < i+1)
//				traineeLoggedIn.getSourceFiles().add(new SourceFile());
//			
//			traineeLoggedIn.getSourceFiles().get(i).setBodyText(sourceFileContents[i]);
//			traineeLoggedIn.getSourceFiles().get(i).setTitle(sourceFileNames[i]);
//		}
////		traineeLoggedIn.getSourceFiles().get(0).setBodyText(sourceFileContents[0]);
////		traineeLoggedIn.getSourceFiles().get(0).setTitle("Main");
////		traineeRepository.save(traineeLoggedIn);
//		
//		
//		StringCompiler stringCompiler = StringCompiler.getInstance();
////		System.out.println(sourceFileContents[0]);
//		String output = stringCompiler.compileAndRun(Integer.toString(traineeLoggedIn.getTraineeId()), traineeLoggedIn.getSourceFiles());
//		System.out.println(output);
//		model.addAttribute("programOutput", output);
//		return "trainee-main";
//	}
	
	
	@RequestMapping("/add_source_file")
	public String addSourceFile(Model model, @ModelAttribute("traineeLoggedIn") Trainee traineeLoggedIn, @RequestParam String sourceFileName) {
		
		boolean addClass = true;
		for(SourceFile s : traineeLoggedIn.getSourceFiles()) {
			if((s.getTitle()).equals(sourceFileName)) {
				model.addAttribute("addClassMessage", "Class already exists");
				addClass = false;
				break;
			}
		}
		
		if(addClass) {
			traineeLoggedIn.getSourceFiles().add(new SourceFile("public class "+sourceFileName+" {\r\n\t\r\n}", sourceFileName, null));
		}
		return "trainee-main";
	}
	
	@RequestMapping("/remove_source_file")
	public String removeSourceFile(Model model, @ModelAttribute("traineeLoggedIn") Trainee traineeLoggedIn, @RequestParam String sourceFileName) {
		List<SourceFile> sourceFiles = traineeLoggedIn.getSourceFiles();
		for(int i = 0; i < sourceFiles.size(); i++) {
			if((sourceFiles.get(i).getTitle()).equals(sourceFileName)) {
				sourceFiles.remove(i);
				break;
			}
		}
		
		return "trainee-main";
	}
	
	
}
