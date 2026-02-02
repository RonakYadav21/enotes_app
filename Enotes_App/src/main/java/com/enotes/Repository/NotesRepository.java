package com.enotes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.model.Notes;

import java.util.List;


public interface NotesRepository extends JpaRepository<Notes,Integer> {


	List<Notes> findByUserId(int id);
}
