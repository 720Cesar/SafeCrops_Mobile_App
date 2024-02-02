package com.example.safecrops;

public class Plagas {

    String id, nombreEnfermedad, cultivoEnfermedad, descripcionEnfermedad, curaEnfermedad;

    public Plagas(){}

    public Plagas(String id, String nombreEnfermedad, String cultivoEnfermedad, String descripcionEnfermedad, String curaEnfermedad) {
        this.id = id;
        this.nombreEnfermedad = nombreEnfermedad;
        this.cultivoEnfermedad = cultivoEnfermedad;
        this.descripcionEnfermedad = descripcionEnfermedad;
        this.curaEnfermedad = curaEnfermedad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreEnfermedad() {
        return nombreEnfermedad;
    }

    public void setNombreEnfermedad(String nombreEnfermedad) {
        this.nombreEnfermedad = nombreEnfermedad;
    }

    public String getCultivoEnfermedad() {
        return cultivoEnfermedad;
    }

    public void setCultivoEnfermedad(String cultivoEnfermedad) {
        this.cultivoEnfermedad = cultivoEnfermedad;
    }

    public String getDescripcionEnfermedad() {
        return descripcionEnfermedad;
    }

    public void setDescripcionEnfermedad(String descripcionEnfermedad) {
        this.descripcionEnfermedad = descripcionEnfermedad;
    }

    public String getCuraEnfermedad() {
        return curaEnfermedad;
    }

    public void setCuraEnfermedad(String curaEnfermedad) {
        this.curaEnfermedad = curaEnfermedad;
    }
}
