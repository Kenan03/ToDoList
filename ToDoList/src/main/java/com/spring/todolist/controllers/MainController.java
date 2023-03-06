package com.spring.todolist.controllers;

import com.spring.todolist.modals.Comment;
import com.spring.todolist.modals.Notes;
import com.spring.todolist.modals.Person;
import com.spring.todolist.modals.Trash;
import com.spring.todolist.repositories.NoteRepository;
import com.spring.todolist.repositories.PersonRepository;
import com.spring.todolist.service.CommentService;
import com.spring.todolist.service.NoteService;
import com.spring.todolist.service.PersonDetailsService;
import com.spring.todolist.service.TrashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
public class MainController {
    private final CommentService commentService;
    private final TrashService trashService;
    private final NoteService noteService;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public MainController(CommentService commentService, TrashService trashService, NoteService noteService, PersonDetailsService personDetailsService, PersonRepository personRepository, NoteRepository noteRepository) {

        this.commentService = commentService;
        this.trashService = trashService;
        this.noteService = noteService;
        this.personDetailsService = personDetailsService;}

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/settings")
    public String settings(Model model) throws Exception {
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());
        model.addAttribute("personInfo", person1);
        return "settings";
    }

    @GetMapping("/updateNote")
    public String updateNote(Model model, @RequestParam int updateNote) {
        Optional<Notes> note = noteService.findById(updateNote);
        if(note.get().getaBoolean())
        {
            model.addAttribute("updateBold", note);
        }
        else {
            model.addAttribute("update", note);
        }
        return "updateNote";
    }

    @PostMapping("/updateNote")
    public String update(Model model, @RequestParam int id, @RequestParam String nameNote, @RequestParam String description, @RequestParam String date, @RequestParam LocalTime time, @RequestParam Boolean button) {
        LocalDate cd = LocalDate.parse(date);
        Optional<Notes> note = noteService.findById(id);
        noteService.findById(id).get().setNotes_name(nameNote);
        noteService.findById(id).get().setDescription(description);
        noteService.findById(id).get().setTimeAndDate(cd);
        noteService.findById(id).get().setTime(time);
        noteService.findById(id).get().setaBoolean(button);
        noteService.save(note.get());
        return info(model, id);
    }

    @PostMapping("/info")
    public String info(Model model, @RequestParam int info) {
        List<Comment> comments = new ArrayList<>();
        Optional<Notes> note = noteService.findById(info);
        for (int i = 0; i < commentService.findAll().size(); i++) {
            int value = commentService.findAll().get(i).getNoteOwner().getId();
            if (info == value) {
                Comment comment = commentService.findAll().get(i);
                comments.add(comment);
            }
        }
        model.addAttribute("comments", comments);
        if(note.get().getaBoolean()) {
            model.addAttribute("noteInfoBold", note);
        }
        else{
            model.addAttribute("noteInfo", note);
        }
        return "info";
    }

    @PostMapping("/delete")
    public String delete(Model model, @RequestParam int idNote, @RequestParam String nameNote, @RequestParam String descriptionNote, @RequestParam String dateNote, @RequestParam String createDate, @RequestParam String time, @RequestParam Boolean button) throws Exception {
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());
        LocalDate date = LocalDate.parse(dateNote);
        LocalDate dateCreate = LocalDate.parse(createDate);
        LocalTime time1 = LocalTime.parse(time);
        Trash trash = new Trash(nameNote, descriptionNote, date, dateCreate, person1.getId(), time1, button);
        trashService.saveTrash(trash);
        Notes note = noteService.findById(idNote).get();
        noteService.delete(note);
        return "redirect:/auth/home";
    }

    @GetMapping("/trash")
    public String delete(Model model) throws Exception {
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());
        List<Trash> trashesBold = new ArrayList<>();
        List<Trash> trashesNotBold = new ArrayList<>();
        Trash trash = new Trash();
        for (int i = 0; i < trashService.findAll().size(); i++) {
            int id = trashService.findAll().get(i).getPerson_id();
            if (id == person1.getId() && trashService.findAll().get(i).getaBoolean()) {
                trash = trashService.findAll().get(i);
                trashesBold.add(trash);
            }
            else if (id == person1.getId() && !trashService.findAll().get(i).getaBoolean()) {
                trash = trashService.findAll().get(i);
                trashesNotBold.add(trash);
            }
        }
        if(trashesNotBold.size() == 0 && trashesBold.size() == 0)
        {
            model.addAttribute("nothing", "You don't have trashes");
        }
        if(trashesBold.size() != 0) {
            model.addAttribute("trashesBold", trashesBold);
        }
        if(trashesNotBold.size() != 0) {
            model.addAttribute("trashesNotBold", trashesNotBold);
        }
        return "trash";
    }

    @PostMapping("/comment")
    public String comment(Model model, @RequestParam int noteId, @RequestParam String comment) throws Exception {
        if(!comment.equals("")) {
            Notes note = noteService.findById(noteId).get();
            Comment comment1 = new Comment(comment, note);
            commentService.save(comment1);
        }
        return info(model, noteId);
    }

    @PostMapping("/findNote")
    public String find(Model model, @RequestParam String find) throws Exception {
        if(find.equals("")){
            return "redirect:/auth/home";
        }
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());
        List<Notes> n = new ArrayList<>();
        for(int i = 0; i < noteService.findAll().size(); i++){
            if(noteService.findAll().get(i).getOwner().getId() == person1.getId()){
                n.add(noteService.findAll().get(i));
            }
        }
        List<Notes> noteBold = new ArrayList<>();
        List<Notes> noteNotBold = new ArrayList<>();
        for(int i = 0; i < n.size(); i++){
            if(n.get(i).getNotes_name().contains(find) && n.get(i).getaBoolean()){
                noteBold.add(n.get(i));
            }
            else if(n.get(i).getNotes_name().contains(find) && !n.get(i).getaBoolean()){
                noteNotBold.add(n.get(i));
            }
        }
        if(noteBold.size() == 0 && noteNotBold.size() == 0)
        {
            return "redirect:/auth/home";
        }
        if(noteBold.size() != 0) {
            model.addAttribute("fineNoteBold", noteBold);
        }
        if(noteNotBold.size() != 0)
        {
            model.addAttribute("fineNoteNotBold", noteNotBold);

        }
        return "findNote";
    }

    @GetMapping("/newNote")
    public String newNote(Model model) throws Exception {
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        return "NewNote";
    }

    @PostMapping("/newNote")
    public String table(Model model, @RequestParam String header, @RequestParam String description, @RequestParam String dateReady, @RequestParam LocalTime time, @RequestParam Boolean button) throws Exception {
        System.out.println(button);
        LocalDate ready = LocalDate.parse(dateReady);
        LocalDate create = LocalDate.now();
        System.out.println(create);
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());
        Notes note = new Notes(header, description, ready, person1, create, time, button);
        noteService.save(note);
        return "redirect:/auth/home";
    }

}