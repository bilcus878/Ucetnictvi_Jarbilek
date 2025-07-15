package cz.itnetwork.controller;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/persons")
    public PersonDTO addPerson(@RequestBody PersonDTO personDTO) {
        return personService.addPerson(personDTO);
    }

    @GetMapping("/persons")
    public List<PersonDTO> getPersons() {
        return personService.getAll();
    }

    @GetMapping("/persons/{personId}")
    public PersonDTO getPersonById(@PathVariable long personId) {
        return personService.getPerson(personId);
    }

    @DeleteMapping("/persons/{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    @PutMapping("/persons/{personId}")
    public PersonDTO updatePerson(@PathVariable long personId, @RequestBody PersonDTO personDTO) {
        return personService.updatePerson(personId, personDTO);
    }
}
