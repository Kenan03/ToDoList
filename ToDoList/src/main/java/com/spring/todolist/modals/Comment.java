package com.spring.todolist.modals;

import javax.persistence.*;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "comments")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "notes_id", referencedColumnName = "id")
    private Notes noteOwner;

    public Comment(){}

    public Comment(String comment, Notes note){
        this.comment = comment;
        this.noteOwner = note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setNoteOwner(Notes noteOwner) {
        this.noteOwner = noteOwner;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Notes getNoteOwner() {
        return noteOwner;
    }

    @Override
    public String toString(){
        return "Note{" +
                "id=" + id + ", noteOwner=" + noteOwner + '\'' +
                ", comment" + comment + "\'" + '}';
    }
}