package edu.uci.ics.peiot.petmanager.pet;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.peiot.petmanager.model.sensordata.SensorData;

public class Pseudonymization extends StatelessPET {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    String attributeToBeProcessed;

    public Pseudonymization(JsonObject params){
        super(params);
        //TODO: Error check
        attributeToBeProcessed = params.get("attributeToBeProcessed").getAsString();
    }

    public String getHashIdForStr(String macString){
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(macString.getBytes("UTF-8"), 0, macString.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    public SensorData processData(SensorData inData){
        logger.debug("Processing Data: "+ inData.payload.toString());
        logger.debug(" -- Randomizing the sensitive attribute: "+ attributeToBeProcessed);
        String attrRaw = inData.payload.get(attributeToBeProcessed).getAsString();
        String attrPsedonymized = getHashIdForStr(attrRaw);
        inData.payload.remove(attributeToBeProcessed);
        inData.payload.addProperty(attributeToBeProcessed, attrPsedonymized);
        return inData;
    }

    public List<SensorData> processDataBatch(SensorData[] inDataBatch){
        List<SensorData> outDataBatch = new LinkedList<SensorData>();
        for (SensorData inData: inDataBatch){
            outDataBatch.add(processData(inData));
        }
        return outDataBatch;
    }
}
