package com.spring.todolist.service;

import com.spring.todolist.modals.Notes;
import com.spring.todolist.modals.Person;
import com.spring.todolist.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Optional<Notes> findById(int id){
        return this.noteRepository.findById(id);
    }

    public List<Notes> findAll(){
        return this.noteRepository.findAll();
    }
    public void delete(Notes note){
        this.noteRepository.delete(note);
    }

    public void save(Notes note){
        this.noteRepository.save(note);
    }


}