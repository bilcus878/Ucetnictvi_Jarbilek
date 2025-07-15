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
    private PersonMapper personMapper;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);
        return personMapper.toDTO(entity);
    }

    @Override
    public void removePerson(long personId) {
        PersonEntity person = fetchPersonById(personId);
        person.setHidden(true);
        personRepository.save(person);
    }

    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findByHidden(false)
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPerson(long personId) {
        return personMapper.toDTO(fetchPersonById(personId));
    }

    @Override
    public PersonDTO updatePerson(long id, PersonDTO personDTO) {
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

    // region: Private helper
    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
    // endregion
}
