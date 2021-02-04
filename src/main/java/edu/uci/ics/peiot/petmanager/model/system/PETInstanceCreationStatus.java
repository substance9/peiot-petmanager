package edu.uci.ics.peiot.petmanager.model.system;

public class PETInstanceCreationStatus {
    public int statusCode;

    public String message;

    public PETInstanceCreationStatus(int statusCode, String message ){
        this.message = message;
        this.statusCode = statusCode;
    }

    public PETInstanceCreationStatus(){
        
    }
}
