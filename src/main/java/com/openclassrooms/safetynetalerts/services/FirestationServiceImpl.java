package com.openclassrooms.safetynetalerts.services;

import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.repositories.FirestationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationServiceImpl implements FirestationService {

    @Autowired
    private FirestationRepositoryImpl firestationRepositoryImpl;

    public List<Firestation> getAllFirestation() {
        return firestationRepositoryImpl.getAllFirestation();
    }

    @Override
    public Boolean saveFiresation(Firestation firestation) {
        return firestationRepositoryImpl.saveFiresation(firestation);
    }

    @Override
    public Firestation updateFirestation(Firestation toUpdate) {
        return firestationRepositoryImpl.updateFirestation(toUpdate);
    }

    @Override
    public Boolean deleteFirestation(Firestation toDelete) {
        return firestationRepositoryImpl.deleteFirestation(toDelete);
    }
}
