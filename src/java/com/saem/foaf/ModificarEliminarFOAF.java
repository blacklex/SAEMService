/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.foaf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.util.FileManager;
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
public class ModificarEliminarFOAF {

    private final Model model = ModelFactory.createDefaultModel();
    private final String BASE_URI = "http://www.saem.com/foaf/pacientes#";
    private String archivoRDF = "";

    private String nombrePersona;

    public ModificarEliminarFOAF(String nombrePersona, String rutaArchivoRDF) {
        archivoRDF = rutaArchivoRDF;
        this.nombrePersona = nombrePersona;
    }

    public Boolean modificarAmigo(String emailAmigo, PersonaFOAF amigo) {
        
        InputStream in = FileManager.get().open(archivoRDF);
        if (in == null) {
            System.out.println("-->ERROR no se pudo abrir el archivo");
            return false;
        }
        model.read(in, "");
        eliminarAmigo(emailAmigo);
        InsercionFOAF insercion = new InsercionFOAF(archivoRDF);

        return insercion.insertarAmigo(amigo, nombrePersona);
    }

    public Boolean eliminarPersona() {
        //FileManager.get().readModel(model, archivoRDF);

        InputStream in = FileManager.get().open(archivoRDF);
        if (in == null) {
            System.out.println("-->ERROR no se pudo abrir el archivo");
            return false;
        }
        model.read(in, "");

        Resource person = model.getResource(BASE_URI + nombrePersona);

        // get all objects of foaf:knows where I am the subject
        StmtIterator myFriends = person.listProperties(FOAF.knows);
        while (myFriends.hasNext()) {
            myFriends.nextStatement().getResource().removeProperties();

        }

        person.removeProperties();

        return guardarFOAF();
    }

    public Boolean eliminarAmigo(String emailAmigo) {
        //FileManager.get().readModel(model, archivoRDF);

        InputStream in = FileManager.get().open(archivoRDF);
        if (in == null) {
            System.out.println("-->ERROR no se pudo abrir el archivo");
            return false;
        }
        model.read(in, "");

        Resource person = model.getResource(BASE_URI + nombrePersona);

        // get all objects of foaf:knows where I am the subject
        StmtIterator myFriends = person.listProperties(FOAF.knows);
        while (myFriends.hasNext()) {
            Statement s = myFriends.nextStatement();
            Resource r = s.getResource();
            System.out.println("--->" + r.toString());
            if (r.getProperty(FOAF.mbox).getResource().toString().split(":")[1].equals(emailAmigo)) {
                myFriends.remove();
                r.removeProperties();
            }

        }

        return guardarFOAF();
    }

    public String getArchivoRDF() {
        return archivoRDF;
    }

    public void setArchivoRDF(String archivoRDF) {
        this.archivoRDF = archivoRDF;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    private Boolean guardarFOAF() {

        OutputStream out = null;

        try {
            out = new FileOutputStream("C:\\Users\\Alejandro\\Desktop\\ejemplo2.rdf");
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

}
