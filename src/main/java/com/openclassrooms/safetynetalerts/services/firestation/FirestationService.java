package com.openclassrooms.safetynetalerts.services.firestation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.dto.PersonsCoveredByFirestation;

import java.util.List;

public interface FirestationService {

    List<Firestation> getAllFirestation();
    Boolean saveFiresation(Firestation firestation);

    Firestation updateFirestation(Firestation toUpdate);

    Boolean deleteFirestation(Firestation toDelete);

    PersonsCoveredByFirestation getPersonCoveredByFirestation(int stationNumber) throws JsonProcessingException;
}
