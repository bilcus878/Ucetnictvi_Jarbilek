package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;

import java.util.List;

public interface PersonService {

    // Přidá novou osobu podle dat z personDTO a vrátí vytvořený objekt PersonDTO
    PersonDTO addPerson(PersonDTO personDTO);

    // Odstraní osobu podle jejího ID
    void removePerson(long id);

    // Vrátí seznam všech osob jako List<PersonDTO>
    List<PersonDTO> getAll();

    // Vrátí osobu podle zadaného ID
    PersonDTO getPerson(long personId);

    // Aktualizuje existující osobu podle ID novými daty z personDTO a vrátí aktualizovaný PersonDTO objekt
    PersonDTO updatePerson(long id, PersonDTO personDTO);

    }

