package com.enotes.controller;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.model.Notes;
import com.enotes.model.User;
import com.enotes.service.NotesService;
import com.enotes.service.userService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class userController {

	
	@Autowired
	private userService userservice;

	@Autowired
	private NotesService noteservice;
	@ModelAttribute//It ensures that the User object is added to the Model for every request.

	public User  getUser(Principal p,Model m) {
		if(p!=null) {
			String email=p.getName();
			User user=userservice.getUserByEmail(email);
			m.addAttribute("user", user);
			return user;
	}
		return null;
	}

		@GetMapping("/addnotes")
		public String addnotespage() {
			return "add_notes";
		}

		@GetMapping("/viewNotes")
		public String viewNotes(Principal p,Model m) {	
			User user=getUser(p,m);
			 List<Notes>notes= noteservice.getNotesByUser(user);

			 m.addAttribute("notes", notes);
			return "view_notes";
		}
		@GetMapping("/editnotes/{id}")
		public String editnotes(@PathVariable int id,Model m) {
		  Notes note=noteservice.getNotesById(id);

		  m.addAttribute("notes", note);
			return "edit_notes";
		}

		@PostMapping("/savenotes")
		public String savenote(@ModelAttribute Notes notes,HttpSession session,Principal p,Model m) {
			
			notes.setDate(LocalDate.now());
            notes.setUser(getUser(p,m));
            Notes savenotes=noteservice.SaveNotes(notes);
			if(savenotes==null) {
				session.setAttribute("errormessage", "There is problem in saving the notes");
			}
			else {
				session.setAttribute("successMsg", "notes saved successfully");
			}
			return "redirect:/user/addnotes";
		}

		@PostMapping("/saveeditednotes")
		public String saveeditnote(@ModelAttribute Notes notes,HttpSession session,Principal p,Model m) {
			int id=notes.getId();
			if(notes.getTitle()==null || notes.getContent()==null) {
				  Notes note=noteservice.getNotesById(id);
                    notes.setTitle(note.getTitle());
                    notes.setContent(notes.getContent());
			}
			notes.setDate(LocalDate.now());
            notes.setUser(getUser(p,m));
            Notes savenotes=noteservice.SaveNotes(notes);
			if(savenotes==null) {
				session.setAttribute("errormessage", "There is problem in saving the notes");
			}
			else {
				session.setAttribute("successMsg", "notes updated  successfully");
			}
			return "redirect:/user/addnotes";
		}

		@GetMapping("/deletenotes/{id}")
		public String Deletenotes(@PathVariable int id,HttpSession session) {
			  Boolean deletednotes= noteservice.deleteNote(id);
			  if(deletednotes) {
					session.setAttribute("successMsg", " notes Deleted  successfully");
			  }else {
					session.setAttribute("errormessage", "There is problem in saving the notes");
  
			  }
			return"redirect:/user/viewNotes";
		}
		
}
