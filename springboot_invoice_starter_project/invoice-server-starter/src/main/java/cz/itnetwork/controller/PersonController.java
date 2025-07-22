package cz.itnetwork.controller;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // HTTP POST metoda pro vytvoření nové osoby
    @PostMapping
    public PersonDTO addPerson(@Valid @RequestBody PersonDTO personDTO) {
        return personService.addPerson(personDTO);
    }

    // HTTP GET metoda pro získání seznamu všech osob
    @GetMapping
    public List<PersonDTO> getPersons() {
        return personService.getAll();
    }

    // HTTP GET metoda pro získání osoby podle jejího ID
    @GetMapping("/{personId}")
    public PersonDTO getPersonById(@PathVariable long personId) {
        return personService.getPerson(personId);
    }

    // HTTP DELETE metoda pro smazání osoby podle ID
    @DeleteMapping("/{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    // HTTP PUT metoda pro aktualizaci osoby podle ID s novými daty
    @PutMapping("/{personId}")
    public PersonDTO updatePerson(@PathVariable long personId, @Valid @RequestBody PersonDTO personDTO) {
        return personService.updatePerson(personId, personDTO);
    }

    @GetMapping("/statistics")
    public List<PersonStatisticsDTO> getPersonStatistics() {
        return personService.getPersonStatistics();
    }

}




