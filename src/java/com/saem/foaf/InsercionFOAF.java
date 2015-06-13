/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.foaf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alejandro
 */
public class InsercionFOAF {

    private final Model model = ModelFactory.createDefaultModel();
    private final String BASE_URI = "http://www.saem.com/foaf/pacientes#";
    private String archivoRDF = "";
    private PersonaFOAF persona;

    public InsercionFOAF(String rutaArchivoRDF) {
        archivoRDF = rutaArchivoRDF;
        }

    
    public InsercionFOAF(String nombrePersona, String telefonoPersona, String facebookPersona, String apellidosPersona, String nombreUsuarioPersona, String correoPersona, String rutaArchivoRDF) {
        archivoRDF = rutaArchivoRDF;
        persona = new PersonaFOAF(nombrePersona, apellidosPersona, nombreUsuarioPersona, correoPersona, telefonoPersona, facebookPersona);
    }

    public Boolean insertarAmigo(String nombreAmigo, String apellidosAmigo, String telefonoAmigo, String correoAmigo, String facebookAmigo) {
        //FileManager.get().readModel(model, archivoRDF);

        InputStream in = FileManager.get().open(archivoRDF);
         if (in == null) {
             System.out.println("-->ERROR no se pudo abrir el archivo");
             return false;
         }
         model.read(in, "");
         
        Resource r = model.getResource(BASE_URI + persona.getNombreUsuarioPersona());
        Resource r2 = model.createResource(BASE_URI +correoAmigo, FOAF.Person);
        r2.addProperty(FOAF.mbox, model.createResource("mailto:" + correoAmigo));
        r2.addProperty(FOAF.phone, model.createResource("tel:" + telefonoAmigo));
        r2.addProperty(FOAF.holdsAccount, model.createResource("http://" + facebookAmigo));
        r2.addProperty(FOAF.name, nombreAmigo);
        r2.addProperty(FOAF.family_name, apellidosAmigo);

        r.addProperty(FOAF.knows, r2);
        
        return guardarFOAF();
        
    }
    
    public Boolean insertarAmigo(PersonaFOAF amigo, String nombrePersona) {
        //FileManager.get().readModel(model, archivoRDF);

        InputStream in = FileManager.get().open(archivoRDF);
         if (in == null) {
             System.out.println("-->ERROR no se pudo abrir el archivo");
             return false;
         }
         model.read(in, "");
         System.out.println("--------> "+nombrePersona+"  "+amigo.getCorreoPersona());
        Resource r = model.getResource(BASE_URI + nombrePersona);
        Resource r2 = model.createResource(BASE_URI + amigo.getCorreoPersona(), FOAF.Person);
        r2.addProperty(FOAF.mbox, model.createResource("mailto:" + amigo.getCorreoPersona()));
        r2.addProperty(FOAF.phone, model.createResource("tel:" + amigo.getTelefonoPersona()));
        r2.addProperty(FOAF.holdsAccount, model.createResource("http://" + amigo.getFacebookPersona()));
        r2.addProperty(FOAF.name, amigo.getNombrePersona());
        r2.addProperty(FOAF.family_name, amigo.getApellidosPersona());

        r.addProperty(FOAF.knows, r2);
        
        return guardarFOAF();
        
    }

    public Boolean insertarPersona() {
        //FileManager.get().readModel(model, archivoRDF);

        InputStream in = FileManager.get().open(archivoRDF);
         if (in == null) {
             System.out.println("-->ERROR no se pudo abrir el archivo");
             return false;
         }
         model.read(in, "");
        
        Resource r = model.createResource(BASE_URI + persona.getNombreUsuarioPersona());
        r.addProperty(FOAF.nick, persona.getNombreUsuarioPersona());
        r.addProperty(FOAF.phone, model.createResource("tel:" + persona.getTelefonoPersona()));
        r.addProperty(FOAF.holdsAccount, model.createResource("http://" + persona.getFacebookPersona()));
        r.addProperty(FOAF.mbox, model.createResource("mailto:" + persona.getCorreoPersona()));
        r.addProperty(RDF.type, FOAF.Person);
        r.addProperty(FOAF.name, persona.getNombrePersona());
        r.addProperty(FOAF.family_name, persona.getApellidosPersona());

        return guardarFOAF();
        
    }

    private Boolean guardarFOAF() {

        OutputStream out = null;

        try {
            out = new FileOutputStream(archivoRDF);
            model.write(out, "RDF/XML-ABBREV");
            out.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InsercionFOAF.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(InsercionFOAF.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public String getArchivoRDF() {
        return archivoRDF;
    }

    public void setArchivoRDF(String archivoRDF) {
        this.archivoRDF = archivoRDF;
    }

    
    
    public PersonaFOAF getPersona() {
        return persona;
    }

    public void setPersona(PersonaFOAF persona) {
        this.persona = persona;
    }
    
    

}
