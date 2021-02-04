package edu.uci.ics.peiot.petmanager.model.system;

import java.sql.Timestamp;
import java.util.UUID;

import edu.uci.ics.peiot.petmanager.model.sensordata.SensorData;

public class ProcessedDataResponse {
    public UUID dataProductUUID;
    public Timestamp time;
    public String sensorId;
    public String payloadStr;

    public ProcessedDataResponse(SensorData sensorData){
        dataProductUUID = sensorData.dataProductUUID;
        time = sensorData.time;
        sensorId = sensorData.sensorId;
        payloadStr = sensorData.payload.toString();
    }
}
