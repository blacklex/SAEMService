package com.hibernate.model;
// Generated 2/06/2015 02:02:20 AM by Hibernate Tools 4.3.1




/**
 * DomicilioHospitales generated by hbm2java
 */
public class DomicilioHospitales  implements java.io.Serializable {


     private Long id;
     private Hospitales hospitales;
     private String calle;
     private String numero;
     private String colonia;
     private String delegacion;
     private String entidadFederativa;
     private String codigoPostal;

    public DomicilioHospitales() {
    }

    public DomicilioHospitales(Hospitales hospitales, String calle, String numero, String colonia, String delegacion, String entidadFederativa, String codigoPostal) {
       this.hospitales = hospitales;
       this.calle = calle;
       this.numero = numero;
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
    public Hospitales getHospitales() {
        return this.hospitales;
    }
    
    public void setHospitales(Hospitales hospitales) {
        this.hospitales = hospitales;
    }
    public String getCalle() {
        return this.calle;
    }
    
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public String getNumero() {
        return this.numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
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


