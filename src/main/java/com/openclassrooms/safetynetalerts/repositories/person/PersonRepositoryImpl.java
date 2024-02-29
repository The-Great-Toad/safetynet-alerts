package com.openclassrooms.safetynetalerts.repositories.person;

import com.openclassrooms.safetynetalerts.exceptions.PersonNotFoundException;
import com.openclassrooms.safetynetalerts.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);

    public static List<Person> people;

    @Override
    public List<Person> getListPersons() {
        return people;
    }

    @Override
    public boolean savePerson(Person p) {

        String fullName = p.getFirstName() + " " + p.getLastName();
        if (people.contains(p)) {
            logger.error("[PersonRepositoryImpl] Error while adding {} to the list\nPerson:\n{}", fullName, p);
            throw new IllegalArgumentException("%s already in the list!".formatted(fullName));
        }
        logger.info("[PersonRepositoryImpl] - Adding {} to the list: {}", fullName, p);
        return people.add(p);
    }

    @Override
    public Person updatePerson(Person toUpdate) {

        String fullName = toUpdate.getFirstName() + " " + toUpdate.getLastName();
        for (Person currentPerson : people) {
            if ( (fullName).equals(currentPerson.getFirstName() + " " + currentPerson.getLastName()) ) {
                int index = people.indexOf(currentPerson);
                people.set(index, toUpdate);
                logger.info("[PersonRepositoryImpl] Updating {}'s details:\nOld details: {}\nNew details: {}", fullName, currentPerson, toUpdate);
                return people.get(index);
            }
        }
        throw new PersonNotFoundException("%s not found!".formatted(fullName));
    }

    @Override
    public Person deletePerson(Person toDelete) {

        String fullName = toDelete.getFirstName() + " " + toDelete.getLastName();
        int index = 0;
        for (Person currentPerson : people) {
            if ( (fullName).equals(currentPerson.getFirstName() + " " + currentPerson.getLastName()) ) {
                logger.info("[PersonRepositoryImpl] Deleting {} from list", fullName);
                return people.remove(index);
            }
            index++;
        }
        throw new PersonNotFoundException("%s not found!".formatted(fullName));
    }

    @Override
    public List<Person> getPersonsByLastName(String lastName) {
        return people.stream()
                .filter(person -> {
                    if (Objects.nonNull(person.getLastName())) {
                        return person.getLastName().equalsIgnoreCase(lastName);
                    }
                    return false;
                })
                .toList();
    }

    @Override
    public List<Person> getPersonByAddress(String address) {
        return people.stream()
                .filter(person -> {
                    if (Objects.nonNull(person.getAddress())) {
                        return person.getAddress().equals(address);
                    }
                    return false;
                })
                .toList();
    }
}
