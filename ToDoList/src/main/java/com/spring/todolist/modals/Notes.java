package com.spring.todolist.modals;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Notes")
public class Notes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "notes_name")
    private String notes_name;

    @Column(name = "description")
    private String description;

    @Column(name = "timeanddate")
    private LocalDate timeAndDate;

    @Column(name = "createdate")
    private LocalDate createDate;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "bold")
    private Boolean aBoolean;
    @OneToMany(mappedBy = "noteOwner")
    private List<Comment> commentList;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Notes(){}
    public Notes(String notes_name, String description, LocalDate timeAndDate, Person owner, LocalDate createDate, LocalTime time, Boolean aBoolean){
        this.aBoolean = aBoolean;
        this.time = time;
        this.notes_name = notes_name;
        this.description = description;
        this.timeAndDate = timeAndDate;
        this.owner = owner;
        this.createDate = createDate;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotes_name(String notes_name) {
        this.notes_name = notes_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeAndDate(LocalDate timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public int getId() {
        return id;
    }

    public String getNotes_name() {
        return notes_name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTimeAndDate() {
        return timeAndDate;
    }
    @Override
    public String toString(){
        return "Note{" +
                "id=" + id + ", person_id=" + owner + '\'' +
                ", note_name=" + notes_name + ", description " + description +
                ", time" + timeAndDate + "\'" + '}';
    }
}