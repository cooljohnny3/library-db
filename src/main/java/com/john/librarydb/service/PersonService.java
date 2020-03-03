package com.john.librarydb.service;

import com.john.librarydb.dao.PersonDAO;
import com.john.librarydb.model.Person;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {
    private final PersonDAO personDAO;

    public PersonService(@Qualifier("mySQL") PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public int addPerson(Person person) {
        return personDAO.addPerson(person);
    }

    public int deletePersonById(UUID id) {
        return personDAO.deletePersonById(id);
    }

    public int updatePersonById(UUID id, Person person) {
        return personDAO.updatePersonById(id, person);
    }

    public List<Person> getPeople() {
        return personDAO.getPeople();
    }

    public Optional<Person> selectPersonById(UUID id) {
        return personDAO.selectPersonById(id);
    }
}
