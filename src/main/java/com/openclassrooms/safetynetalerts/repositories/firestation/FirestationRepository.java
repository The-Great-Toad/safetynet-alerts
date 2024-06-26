package com.openclassrooms.safetynetalerts.repositories.firestation;

import com.openclassrooms.safetynetalerts.domain.Firestation;

import java.util.List;

public interface FirestationRepository {

    List<Firestation> getAllFirestation();
    Boolean saveFiresation(Firestation firestation);

    Firestation updateFirestation(Firestation toUpdate);

    Boolean deleteFirestation(Firestation toDelete);

    List<String> getAddressesByStationNumber(int stationNumber);

    List<Firestation> getFirestationByAddress(String address);

    List<Firestation> getFirestationByStationNumber(Integer station);
}
