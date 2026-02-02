package com.enotes.service;

import java.util.List;

import com.enotes.model.Notes;
import com.enotes.model.User;

public interface NotesService {
public Notes SaveNotes(Notes note);

public List<Notes> getNotesByUser(User user);

public Notes getNotesById(int id);

public Boolean deleteNote(int id);
}
