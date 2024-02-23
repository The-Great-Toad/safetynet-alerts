package com.openclassrooms.safetynetalerts.services.person;

import com.openclassrooms.safetynetalerts.models.MedicalRecord;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.models.dto.ChildDto;

import java.util.List;

public interface PersonService {

    List<Person> getAllPerson();

    boolean savePerson(Person p);

    Person updatePerson(Person p);

    Person deletePerson(Person p);

    List<ChildDto> getChildrenByAdress(String address);

    List<String> getPhonesByFirestationNumber(int stationNumber);

    Integer calculateAge(MedicalRecord md);

    List<Person> getPersonsByAddress(String address);
}
