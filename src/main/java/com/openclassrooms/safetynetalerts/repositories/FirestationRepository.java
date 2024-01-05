package com.openclassrooms.safetynetalerts.repositories;

import com.openclassrooms.safetynetalerts.models.Firestation;

public interface FirestationRepository {

    Boolean saveFiresation(Firestation firestation);

    Firestation updateFirestation(Firestation toUpdate);

    Boolean deleteFirestation(Firestation toDelete);
}
