package com.spring.todolist.repositories;

import com.spring.todolist.modals.Notes;
import com.spring.todolist.modals.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface NoteRepository extends JpaRepository<Notes, Integer>{
    Optional<Notes> findById(int id);
    void deleteNotesById(int id);
}