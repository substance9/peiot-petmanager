package edu.uci.ics.peiot.petmanager.pet;

import com.google.gson.JsonObject;

public abstract class StatefulNonBlockingPET extends PET {
    public StatefulNonBlockingPET(JsonObject params){
        super(params);
    }
}
