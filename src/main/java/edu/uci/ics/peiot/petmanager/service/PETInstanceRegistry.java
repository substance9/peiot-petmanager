package edu.uci.ics.peiot.petmanager.service;

import java.util.HashMap;
import java.util.UUID;

import com.google.gson.JsonObject;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.peiot.petmanager.model.system.PETInstanceCreationStatus;
import edu.uci.ics.peiot.petmanager.pet.*;

@Component
public class PETInstanceRegistry {
    private HashMap<UUID, PET> petInstMap;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PETInstanceRegistry() {
        petInstMap = new HashMap<UUID, PET>();
    }

    public PETInstanceCreationStatus createNewPETInstance(UUID dataProductId, String PETName, JsonObject params){
        if (petInstMap.containsKey(dataProductId)){
            return new PETInstanceCreationStatus(1, "A PET instance for the Data Product is already existed");
        }

        PET newPETInstance = null;

        logger.debug("Trying to create PET: "+PETName);

        if (PETName.equals("pseudonymization")){
            newPETInstance = new Pseudonymization(params);
        // } else if (PETName == ""){
        } else{
            return new PETInstanceCreationStatus(1, "Unsupported PET");
        }

        if (newPETInstance == null){
            return new PETInstanceCreationStatus(1, "PET Instance Creation Failure, NULL is returned");
        }

        petInstMap.put(dataProductId, newPETInstance);
        return new PETInstanceCreationStatus(0, "Success");
    }

    public PET getPETInstanceByDataProductId(UUID dataProductUUID){
        logger.debug("Trying to get PET Instance for : "+dataProductUUID.toString());

        PET petInsta = petInstMap.get(dataProductUUID);
        return petInsta;
    }
}
