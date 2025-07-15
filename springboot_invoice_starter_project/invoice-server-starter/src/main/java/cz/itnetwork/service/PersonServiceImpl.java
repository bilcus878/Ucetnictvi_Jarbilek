package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper; // Mapuje mezi PersonDTO a PersonEntity

    @Autowired
    private PersonRepository personRepository; // Repo pro práci s databází osob

    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        // Převede DTO na entitu, uloží ji do DB a vrátí zpět jako DTO
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);
        return personMapper.toDTO(entity);
    }

    @Override
    public void removePerson(long personId) {
        // Najde osobu podle ID, označí ji jako skrytou (hidden = true) a uloží změnu
        PersonEntity person = fetchPersonById(personId);
        person.setHidden(true);
        personRepository.save(person);
    }

    @Override
    public List<PersonDTO> getAll() {
        // Vrátí seznam všech osob, které nejsou skryté (hidden = false), převede je na DTO
        return personRepository.findByHidden(false)
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPerson(long personId) {
        // Najde osobu podle ID a vrátí ji jako DTO
        return personMapper.toDTO(fetchPersonById(personId));
    }

    @Override
    public PersonDTO updatePerson(long id, PersonDTO personDTO) {
        // Najde existující entitu podle ID, aktualizuje její data z DTO, uloží a vrátí aktualizované DTO
        PersonEntity existingPerson = fetchPersonById(id);

        existingPerson.setName(personDTO.getName());
        existingPerson.setIdentificationNumber(personDTO.getIdentificationNumber());
        existingPerson.setTaxNumber(personDTO.getTaxNumber());
        existingPerson.setAccountNumber(personDTO.getAccountNumber());
        existingPerson.setBankCode(personDTO.getBankCode());
        existingPerson.setIban(personDTO.getIban());
        existingPerson.setTelephone(personDTO.getTelephone());
        existingPerson.setMail(personDTO.getMail());
        existingPerson.setStreet(personDTO.getStreet());
        existingPerson.setZip(personDTO.getZip());
        existingPerson.setCity(personDTO.getCity());
        existingPerson.setCountry(personDTO.getCountry());
        existingPerson.setNote(personDTO.getNote());

        PersonEntity updatedPerson = personRepository.save(existingPerson);
        return personMapper.toDTO(updatedPerson);
    }

    private PersonEntity fetchPersonById(long id) {
        // Pomocná metoda, která najde osobu podle ID nebo hodí výjimku, pokud neexistuje
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }

}

