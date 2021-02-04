package edu.uci.ics.peiot.petmanager.pet;

import com.google.gson.JsonObject;

public abstract class StatefulBlockingPET extends PET{
    public StatefulBlockingPET(JsonObject params){
        super(params);
    }
}
