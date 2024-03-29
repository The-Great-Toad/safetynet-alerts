package com.openclassrooms.safetynetalerts.configuration.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppInfo implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> details = new HashMap<>();
        details.put("name","SafetyNet Alerts");
        details.put("description","Le but de cet application est d'envoyer des informations aux systèmes de services d'urgences.");
        details.put("version","v1.0");
        builder.withDetails(details);
    }
}
