package com.openclassrooms.safetynetalerts.services;

import com.openclassrooms.safetynetalerts.models.Firestation;

public interface FirestationService {

    Boolean saveFiresation(Firestation firestation);

    Firestation updateFirestation(Firestation toUpdate);

    Boolean deleteFirestation(Firestation toDelete);
}
