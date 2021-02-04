package edu.uci.ics.peiot.petmanager.webinterface;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uci.ics.peiot.petmanager.model.sensordata.SensorData;

import edu.uci.ics.peiot.petmanager.model.system.PETInstanceCreationStatus;
import edu.uci.ics.peiot.petmanager.model.system.ProcessedDataResponse;
import edu.uci.ics.peiot.petmanager.pet.PET;
import edu.uci.ics.peiot.petmanager.pet.PET;
import edu.uci.ics.peiot.petmanager.service.PETInstanceRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin()
@RequestMapping(value = "/data")
public class DataController {
    @Autowired
    PETInstanceRegistry petInstanceRegistry;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> processData(@RequestBody String dataProcessReq) {
        // UUID dataProductUUID = UUID.fromString(creationReq.getDataProductUUIDStr());
        // String petName = creationReq.getPetName();
        // String paramsJsonStr = creationReq.getPetParamsJsonStr();
        JsonObject dataProcessReqJson = new Gson().fromJson(dataProcessReq, JsonObject.class);
        UUID dataProductUUID = UUID.fromString(dataProcessReqJson.get("dataProductUUIDStr").getAsString());
        String sensorId = dataProcessReqJson.get("sensorId").getAsString();
        Timestamp time = Timestamp.valueOf(dataProcessReqJson.get("time").getAsString());
        JsonObject payload = dataProcessReqJson.get("payload").getAsJsonObject();

        SensorData inData = new SensorData(dataProductUUID,time,sensorId,payload);

        PET petInstance = petInstanceRegistry.getPETInstanceByDataProductId(dataProductUUID);

        if (petInstance == null){
            return new ResponseEntity<String>("PET Instance for the data product not found", HttpStatus.NOT_ACCEPTABLE);
        }

        SensorData outData = petInstance.processData(inData);

        ProcessedDataResponse retData = new ProcessedDataResponse(outData);
        return new ResponseEntity<ProcessedDataResponse>(retData, HttpStatus.OK);
        
    }

    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public ResponseEntity<?> processDataBatch(@RequestBody String dataProcessReq) {
        logger.debug("Got Data Batch");
        JsonObject[] dataProcessReqJsonArray = new Gson().fromJson(dataProcessReq, JsonObject[].class);
        int numOfDataItems = dataProcessReqJsonArray.length;
        SensorData[] inDataBatch = new SensorData[numOfDataItems];

        logger.debug("Batch Length: " + Integer.toString(numOfDataItems));
        int index = 0;

        for (JsonObject dataProcessReqJson: dataProcessReqJsonArray){
            logger.debug("data time: "+ dataProcessReqJson.get("time").getAsString());
            SensorData inData = new SensorData(UUID.fromString(dataProcessReqJson.get("dataProductUUIDStr").getAsString()),
                                                Timestamp.valueOf(dataProcessReqJson.get("time").getAsString()),
                                                dataProcessReqJson.get("sensorId").getAsString(),
                                                dataProcessReqJson.get("payload").getAsJsonObject());
            inDataBatch[index] = inData;
            index = index + 1;
        }

        //TODO: can a batch includes data from different data product?

        PET petInstance = petInstanceRegistry.getPETInstanceByDataProductId(inDataBatch[0].dataProductUUID);

        if (petInstance == null){
            return new ResponseEntity<String>("PET Instance for the data product not found", HttpStatus.NOT_ACCEPTABLE);
        }

        List<SensorData> outDataList = petInstance.processDataBatch(inDataBatch);

        List<ProcessedDataResponse> retDataList = new ArrayList<ProcessedDataResponse>();

        for(SensorData outData: outDataList){
            ProcessedDataResponse retData = new ProcessedDataResponse(outData);
            retDataList.add(retData);
        }

        
        return new ResponseEntity<List<ProcessedDataResponse>>(retDataList, HttpStatus.OK);
        
    }

}