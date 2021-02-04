package edu.uci.ics.peiot.petmanager.pet;

import java.util.List;

import com.google.gson.JsonObject;

import edu.uci.ics.peiot.petmanager.model.sensordata.SensorData;

public abstract class PET {
    public JsonObject params;

    public PET(JsonObject params){
        this.params = params;
    }

    public abstract SensorData processData(SensorData inData);
    public abstract List<SensorData> processDataBatch(SensorData[] inDataBatch);
}
