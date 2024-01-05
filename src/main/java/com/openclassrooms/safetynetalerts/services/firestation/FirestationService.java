package com.openclassrooms.safetynetalerts.services.firestation;

import com.openclassrooms.safetynetalerts.models.Firestation;

public interface FirestationService {

    Boolean saveFiresation(Firestation firestation);

    Firestation updateFirestation(Firestation toUpdate);

    Boolean deleteFirestation(Firestation toDelete);
}
