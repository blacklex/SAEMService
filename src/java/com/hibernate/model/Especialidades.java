package com.hibernate.model;
// Generated 2/06/2015 02:02:20 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Especialidades generated by hbm2java
 */
public class Especialidades  implements java.io.Serializable {


     private int noEspecialidad;
     private String nombreEspecialidad;
     private Set hospitaleses = new HashSet(0);

    public Especialidades() {
    }

	
    public Especialidades(int noEspecialidad) {
        this.noEspecialidad = noEspecialidad;
    }
    public Especialidades(int noEspecialidad, String nombreEspecialidad, Set hospitaleses) {
       this.noEspecialidad = noEspecialidad;
       this.nombreEspecialidad = nombreEspecialidad;
       this.hospitaleses = hospitaleses;
    }
   
    public int getNoEspecialidad() {
        return this.noEspecialidad;
    }
    
    public void setNoEspecialidad(int noEspecialidad) {
        this.noEspecialidad = noEspecialidad;
    }
    public String getNombreEspecialidad() {
        return this.nombreEspecialidad;
    }
    
    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }
    public Set getHospitaleses() {
        return this.hospitaleses;
    }
    
    public void setHospitaleses(Set hospitaleses) {
        this.hospitaleses = hospitaleses;
    }




}

