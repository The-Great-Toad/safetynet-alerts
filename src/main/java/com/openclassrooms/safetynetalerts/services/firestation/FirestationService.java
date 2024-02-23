package com.openclassrooms.safetynetalerts.services.firestation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.dto.HomeDto;
import com.openclassrooms.safetynetalerts.models.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.models.dto.ResidentAndFirestationDto;

import java.util.List;

public interface FirestationService {

    List<Firestation> getAllFirestation();

    Boolean saveFiresation(Firestation firestation);

    Firestation updateFirestation(Firestation toUpdate);

    Boolean deleteFirestation(Firestation toDelete);

    PersonsCoveredByFirestation getPersonCoveredByFirestation(int stationNumber) throws JsonProcessingException;

    List<String> getAddressesByStationNumber(int stationNumber);

    ResidentAndFirestationDto getResidentAndFirestationDto(String address);

    HomeDto getHomeServedByStations(List<Integer> stations);
}
