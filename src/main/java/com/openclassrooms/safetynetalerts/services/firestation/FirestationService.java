package com.openclassrooms.safetynetalerts.services.firestation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.domain.dto.HomeDto;
import com.openclassrooms.safetynetalerts.domain.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.domain.dto.ResidentAndFirestationDto;

import java.util.List;

public interface FirestationService {

    List<Firestation> getAllFireStation();

    Boolean saveFireStation(Firestation firestation);

    Firestation updateFireStation(Firestation toUpdate);

    Boolean deleteFireStation(Firestation toDelete);

    PersonsCoveredByFirestation getPersonCoveredByFireStation(int stationNumber) throws JsonProcessingException;

    List<String> getAddressesByStationNumber(int stationNumber);

    ResidentAndFirestationDto getResidentAndFireStationDto(String address);

    HomeDto getHomeServedByStations(List<Integer> stations);
}
