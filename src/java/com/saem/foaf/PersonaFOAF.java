/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.foaf;

/**
 *
 * @author Alejandro
 */
public class PersonaFOAF {
    private String nombrePersona;
    private String apellidosPersona;
    private String nombreUsuarioPersona;
    private String correoPersona;
    private String telefonoPersona;
    private String facebookPersona;

    public PersonaFOAF(String nombrePersona, String apellidosPersona, String nombreUsuarioPersona, String correoPersona, String telefonoPersona, String facebookPersona) {
        this.nombrePersona = nombrePersona;
        this.apellidosPersona = apellidosPersona;
        this.nombreUsuarioPersona = nombreUsuarioPersona;
        this.correoPersona = correoPersona;
        this.telefonoPersona = telefonoPersona;
        this.facebookPersona = facebookPersona;
    }
    
    public PersonaFOAF(){}
    

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getTelefonoPersona() {
        return telefonoPersona;
    }

    public void setTelefonoPersona(String telefonoPersona) {
        this.telefonoPersona = telefonoPersona;
    }

    public String getFacebookPersona() {
        return facebookPersona;
    }

    public void setFacebookPersona(String facebookPersona) {
        this.facebookPersona = facebookPersona;
    }

    public String getApellidosPersona() {
        return apellidosPersona;
    }

    public void setApellidosPersona(String apellidosPersona) {
        this.apellidosPersona = apellidosPersona;
    }

    public String getNombreUsuarioPersona() {
        return nombreUsuarioPersona;
    }

    public void setNombreUsuarioPersona(String nombreUsuarioPersona) {
        this.nombreUsuarioPersona = nombreUsuarioPersona;
    }

    public String getCorreoPersona() {
        return correoPersona;
    }

    public void setCorreoPersona(String correoPersona) {
        this.correoPersona = correoPersona;
    }
    
    
    
}
