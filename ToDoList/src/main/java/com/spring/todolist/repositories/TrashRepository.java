package com.spring.todolist.repositories;

import com.spring.todolist.modals.Trash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrashRepository extends JpaRepository<Trash, Integer> {
    List<Trash> findById(int id);
}
