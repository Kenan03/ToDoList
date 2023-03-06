package com.spring.todolist.repositories;

import com.spring.todolist.modals.Comment;
import com.spring.todolist.modals.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findById(int id);
    List<Comment> findAllByNoteOwner(Notes noteOwner);
}