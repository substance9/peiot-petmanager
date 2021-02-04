package edu.uci.ics.peiot.petmanager.pet;

import com.google.gson.JsonObject;

public abstract class StatelessPET extends PET {
    public StatelessPET(JsonObject params){
        super(params);
    }
}
