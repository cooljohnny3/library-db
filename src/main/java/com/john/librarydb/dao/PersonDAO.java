package com.john.librarydb.dao;

import com.john.librarydb.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDAO {
    int addPerson(UUID id, Person person);
    default int addPerson(Person person) {
        UUID id = UUID.randomUUID();
        return addPerson(id, person);
    }
    int deletePersonById(UUID id);
    int updatePersonById(UUID id, Person person);
    List<Person> getPeople();
    Optional<Person> selectPersonById(UUID id);
}
