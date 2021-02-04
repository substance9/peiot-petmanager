package edu.uci.ics.peiot.petmanager.model.system;

import com.google.gson.JsonObject;

public class PETInstanceCreationRequest {
    private String dataProductUUIDStr;
    private String petName;
    private String petParamsJsonStr;

    public String getDataProductUUIDStr() {
        return dataProductUUIDStr;
    }

    public void setDataProductUUIDStr(String dataProductUUIDStr) {
        this.dataProductUUIDStr = dataProductUUIDStr;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetParamsJsonStr() {
        return petParamsJsonStr;
    }

    public void setPetParamsJsonStr(String petParamsJsonStr) {
        this.petParamsJsonStr = petParamsJsonStr;
    }

}
