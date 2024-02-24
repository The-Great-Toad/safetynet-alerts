package com.openclassrooms.safetynetalerts.services.person;

import com.openclassrooms.safetynetalerts.models.MedicalRecord;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.models.dto.ChildDto;
import com.openclassrooms.safetynetalerts.models.dto.PersonInfoDto;

import java.util.List;

public interface PersonService {

    List<Person> getAllPerson();

    boolean savePerson(Person p);

    Person updatePerson(Person p);

    Person deletePerson(Person p);

    List<ChildDto> getChildrenByAddress(String address);

    List<String> getPhonesByFireStationNumber(int stationNumber);

    Integer calculateAge(MedicalRecord md);

    List<Person> getPersonsByAddress(String address);

    List<PersonInfoDto> getPersonInfoByFirstAndLastName(String firstName, String lastName);

    List<Person> getPersonsByLastName(String lastName);
}
