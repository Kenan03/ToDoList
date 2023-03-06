package com.spring.todolist.service;

import com.spring.todolist.modals.Trash;
import com.spring.todolist.repositories.TrashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrashService {
    private final TrashRepository trashRepository;

    @Autowired
    public TrashService(TrashRepository trashRepository){
        this.trashRepository = trashRepository;
    }

    public void saveTrash(Trash trash){
        this.trashRepository.save(trash);
    }

    public List<Trash> findAll(){
        return this.trashRepository.findAll();
    }
}