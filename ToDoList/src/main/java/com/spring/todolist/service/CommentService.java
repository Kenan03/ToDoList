package com.spring.todolist.service;

import com.spring.todolist.modals.Comment;
import com.spring.todolist.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll(){
        return this.commentRepository.findAll();
    }

    public void save(Comment comment){
        this.commentRepository.save(comment);
    }
}