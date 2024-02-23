package com.openclassrooms.safetynetalerts.repositories.firestation;

import com.openclassrooms.safetynetalerts.models.Firestation;

import java.util.List;

public interface FirestationRepository {

    List<Firestation> getAllFirestation();
    Boolean saveFiresation(Firestation firestation);

    Firestation updateFirestation(Firestation toUpdate);

    Boolean deleteFirestation(Firestation toDelete);

    List<String> getAddressesByStationNumber(int stationNumber);
}
