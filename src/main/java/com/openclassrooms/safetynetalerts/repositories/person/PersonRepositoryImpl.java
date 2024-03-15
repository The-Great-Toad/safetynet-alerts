package com.openclassrooms.safetynetalerts.repositories.person;

import com.openclassrooms.safetynetalerts.configuration.exceptions.PersonNotFoundException;
import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.repositories.DataObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class PersonRepositoryImpl implements PersonRepository {


    private static final Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);
    private final String loggingNameRef = "[PersonRepository]";

    @Autowired
    private DataObjectRepository dataObjectRepository;

    @Override
    public List<Person> getListPersons() {
        return dataObjectRepository.getPeople();
    }

    @Override
    public boolean savePerson(Person p) {
        List<Person> people = getListPersons();

        String fullName = p.getFirstName() + " " + p.getLastName();
        if (people.contains(p)) {
            final String error = String.format("%s - ERROR - %s already in the list - %s", loggingNameRef, fullName, p);
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        logger.debug("{} - Adding {} to the list - {}", loggingNameRef, fullName, p);
        return people.add(p);
    }

    @Override
    public Person updatePerson(Person toUpdate) {
        List<Person> people = getListPersons();
        String fullName = toUpdate.getFirstName() + " " + toUpdate.getLastName();

        for (Person currentPerson : people) {
            if ( (fullName).equals(currentPerson.getFirstName() + " " + currentPerson.getLastName()) ) {
                int index = people.indexOf(currentPerson);
                people.set(index, toUpdate);
                logger.debug("{} - Updating {}'s details - Before update: {} - After update: {}", loggingNameRef, fullName, currentPerson, toUpdate);
                return people.get(index);
            }
        }
        final String error = String.format("%s - ERROR - %s not found - %s", loggingNameRef, fullName, toUpdate);
        logger.error(error);
        throw new PersonNotFoundException(error);
    }

    @Override
    public Person deletePerson(Person toDelete) {
        List<Person> people = getListPersons();
        String fullName = toDelete.getFirstName() + " " + toDelete.getLastName();
        int index = 0;

        for (Person currentPerson : people) {
            if ( (fullName).equals(currentPerson.getFirstName() + " " + currentPerson.getLastName()) ) {
                logger.debug("{} - Deleting {} from list", loggingNameRef, fullName);
                return people.remove(index);
            }
            index++;
        }
        final String error = String.format("%s - ERROR - %s not found - %s", loggingNameRef, fullName, toDelete);
        logger.error(error);
        throw new PersonNotFoundException(error);
    }

    @Override
    public List<Person> getPersonsByLastName(String lastName) {
        List<Person> people = getListPersons();

        List<Person> result = people.stream()
                .filter(person -> {
                    if (Objects.nonNull(person.getLastName())) {
                        return person.getLastName().equalsIgnoreCase(lastName);
                    }
                    return false;
                })
                .toList();

        if (result.isEmpty()) {
            logger.debug("{} - No match found for lastname: {}", loggingNameRef, lastName);
        }
        return result;
    }

    @Override
    public List<Person> getPersonByAddress(String address) {
        List<Person> people = getListPersons();

        List<Person> result =  people.stream()
                .filter(person -> {
                    if (Objects.nonNull(person.getAddress())) {
                        return person.getAddress().equals(address);
                    }
                    return false;
                })
                .toList();

        if (result.isEmpty()) {
            logger.debug("{} - No match found for address: {}", loggingNameRef, address);
        }
        return result;
    }
}
