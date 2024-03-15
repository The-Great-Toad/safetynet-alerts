package com.openclassrooms.safetynetalerts;

import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.domain.dto.ResidentDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

public class TestUtils {

    public static MedicalRecord getMedicalRecord() throws ParseException {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setBirthdate(getDate());
        medicalRecord.setMedications(List.of("Med1", "Med2"));
        medicalRecord.setAllergies(List.of("Allergy1", "Allergy2"));
        return medicalRecord;
    }

    public static Date getDate() throws ParseException {
        return getDate(null);
    }
    public static Date getDate(String date) throws ParseException {
        String dateString = Objects.nonNull(date) ? date : "03/06/2010";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(dateString);
    }

    public static List<Person> createPersonList() {
        Person p1 = new Person("Erick", "Pattisson");
        Person p2 = new Person("Sam", "Embet");
        Person p3 = new Person("Todd", "You");
        Person p4 = new Person("Brad", "Rie");

        return asList(p1, p2, p3, p4);
    }

    public static Person createPerson() {
        return Person.builder()
                .firstName("Bob")
                .lastName("Sponge")
                .build();
    }

    protected static ResidentDto createResidentDto() {
        return ResidentDto.builder()
                .lastName("Todd")
                .phone("123-456-789")
                .age(15)
                .medications(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();
    }

    protected static MedicalRecord createMedicalRecord() throws ParseException {
        return MedicalRecord.builder()
                .firstName("Bob")
                .lastName("Sponge")
                .birthdate(getDate("03/06/1984"))
                .medications(List.of("aznol:350mg","hydrapermazol:100mg"))
                .allergies(List.of("nillacilan"))
                .build();
    }

    public Firestation createFireStation() {
        return Firestation.builder()
                .address("123 Test")
                .station(2)
                .build();
    }

    public List<Firestation> createFirestationsList() {
        Firestation f1 = new Firestation("123 rue 123", 1);
        Firestation f2 = new Firestation("456 rue 456", 2);
        Firestation f3 = new Firestation("789 rue 789", 3);
        Firestation f4 = new Firestation("032 rue 032", 4);

        return List.of(f1, f2, f3, f4);
    }
}
