package com.spring.todolist.controllers;

import com.spring.todolist.modals.Notes;
import com.spring.todolist.modals.Person;
import com.spring.todolist.service.NoteService;
import com.spring.todolist.service.PersonDetailsService;
import com.spring.todolist.service.RegistrationService;
import com.spring.todolist.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final PersonDetailsService personDetailsService;
    private final NoteService noteService;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, PersonDetailsService personDetailsService,  NoteService noteService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.personDetailsService = personDetailsService;

        this.noteService = noteService;
    }

    @GetMapping("/signin")
    public String sign(){
        return "signIn";
    }

    @GetMapping("/login")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "LogIn";
    }

    @PostMapping("/login")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "LogIn";
        }
        registrationService.register(person);
        LocalDate creaty = LocalDate.now();
        Notes note = new Notes("Hey " + person.getUsername() + "!", "Thank you for registration!!", creaty, person, creaty, LocalTime.now(), false);
        noteService.save(note);
        return "redirect:/auth/signin";
    }

    @GetMapping("/home")
    public String hello(Model model)  throws Exception {
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());
        List<Notes> noteBold = new ArrayList<>();
        List<Notes> noteNotBold = new ArrayList<>();
        for(int i = 0; i < noteService.findAll().size(); i++){
            if(noteService.findAll().get(i).getOwner().getId() == person1.getId() && noteService.findAll().get(i).
                    getaBoolean()){
                noteBold.add(noteService.findAll().get(i));
            }
            else if(noteService.findAll().get(i).getOwner().getId() == person1.getId() && !noteService.findAll().get(i).
                    getaBoolean()){
                noteNotBold.add(noteService.findAll().get(i));
            }
        }
        if(noteBold.size() != 0) {
            model.addAttribute("noteBold", noteBold);
        }
        if(noteNotBold.size() != 0){
            model.addAttribute("noteNotBold", noteNotBold);

        }
        return "ToDoList";
    }
}