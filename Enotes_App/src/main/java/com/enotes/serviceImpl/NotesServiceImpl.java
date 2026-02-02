package com.enotes.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.Repository.NotesRepository;
import com.enotes.model.Notes;
import com.enotes.model.User;
import com.enotes.service.NotesService;

@Service
public  class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesrepository;
	@Override
	public Notes SaveNotes(Notes note) {
		  Notes savednotes=notesrepository.save(note);
		return savednotes;
	}
	@Override
	public List<Notes> getNotesByUser(User user) {
	    return notesrepository.findByUserId(user.getId());
	}
	@Override
	public Notes getNotesById(int id) {
     Notes note=notesrepository.findById(id).get();
         return note;
	}
	@Override
	public Boolean deleteNote(int id) {
		Notes note=notesrepository.findById(id).orElse(null);
		if(!ObjectUtils.isEmpty(note)) {
	            notesrepository.deleteById(id) ; 
			return true;
		}
		 
		return false;
	}
	
}
