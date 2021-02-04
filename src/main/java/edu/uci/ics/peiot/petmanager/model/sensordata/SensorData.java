package edu.uci.ics.peiot.petmanager.model.sensordata;

import java.sql.Timestamp;
import java.util.UUID;

import com.google.gson.JsonObject;

public class SensorData {
    public UUID dataProductUUID;
    public Timestamp time;
    public String sensorId;
    public JsonObject payload;

    public SensorData(UUID dataProductUUID,Timestamp time,String sensorId,JsonObject payload){
        this.dataProductUUID = dataProductUUID;
        this.time=time;
        this.sensorId=sensorId;
        this.payload=payload;
    }
}
