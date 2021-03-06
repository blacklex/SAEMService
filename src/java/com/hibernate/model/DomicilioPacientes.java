package com.hibernate.model;
// Generated 2/06/2015 02:02:20 AM by Hibernate Tools 4.3.1




/**
 * DomicilioPacientes generated by hbm2java
 */
public class DomicilioPacientes  implements java.io.Serializable {


     private Long id;
     private Pacientes pacientes;
     private String calle;
     private String colonia;
     private String delegacion;
     private String entidadFederativa;
     private String codigoPostal;

    public DomicilioPacientes() {
    }

    public DomicilioPacientes(Pacientes pacientes, String calle, String colonia, String delegacion, String entidadFederativa, String codigoPostal) {
       this.pacientes = pacientes;
       this.calle = calle;
       this.colonia = colonia;
       this.delegacion = delegacion;
       this.entidadFederativa = entidadFederativa;
       this.codigoPostal = codigoPostal;
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public Pacientes getPacientes() {
        return this.pacientes;
    }
    
    public void setPacientes(Pacientes pacientes) {
        this.pacientes = pacientes;
    }
    public String getCalle() {
        return this.calle;
    }
    
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public String getColonia() {
        return this.colonia;
    }
    
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }
    public String getDelegacion() {
        return this.delegacion;
    }
    
    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }
    public String getEntidadFederativa() {
        return this.entidadFederativa;
    }
    
    public void setEntidadFederativa(String entidadFederativa) {
        this.entidadFederativa = entidadFederativa;
    }
    public String getCodigoPostal() {
        return this.codigoPostal;
    }
    
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }




}


