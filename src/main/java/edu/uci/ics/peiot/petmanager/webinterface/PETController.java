package edu.uci.ics.peiot.petmanager.webinterface;

import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uci.ics.peiot.petmanager.model.system.PETInstanceCreationRequest;
import edu.uci.ics.peiot.petmanager.model.system.PETInstanceCreationStatus;
import edu.uci.ics.peiot.petmanager.service.PETInstanceRegistry;


@RestController
@CrossOrigin()
@RequestMapping(value = "/pet")
public class PETController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PETInstanceRegistry petInstanceRegistry;

    @PutMapping(value = "/instance")
    public ResponseEntity<?> createPETInstance(@RequestBody String creationReq) {
        // UUID dataProductUUID = UUID.fromString(creationReq.getDataProductUUIDStr());
        // String petName = creationReq.getPetName();
        // String paramsJsonStr = creationReq.getPetParamsJsonStr();
        JsonObject creationReqJson = new Gson().fromJson(creationReq, JsonObject.class);
        UUID dataProductUUID = UUID.fromString(creationReqJson.get("dataProductUUIDStr").getAsString());
        String petName = creationReqJson.get("petName").getAsString();
        JsonObject params = creationReqJson.get("petParams").getAsJsonObject();

        //logger.debug("PET parameters: " + paramsJsonStr);

        

        PETInstanceCreationStatus creationStatus = petInstanceRegistry.createNewPETInstance(dataProductUUID, petName, params);

        if (creationStatus.statusCode == 0){
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(creationStatus.message, HttpStatus.NOT_ACCEPTABLE);
        }
        
    }
    
}