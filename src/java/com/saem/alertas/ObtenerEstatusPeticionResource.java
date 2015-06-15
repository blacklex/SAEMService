/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.alertas;

import com.hibernate.dao.PeticionesSalientesDAO;
import com.hibernate.dao.UsuarioDAO;
import com.hibernate.model.Contactos;
import com.hibernate.model.Hospitales;
import com.hibernate.model.Pacientes;
import com.hibernate.model.PeticionesSalientes;
import com.hibernate.model.Usuarios;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Session;

/**
 * REST Web Service
 *
 * @author Alejandro
 */
@Path("obtenerEstatusPeticion")
public class ObtenerEstatusPeticionResource {

    @Context
    private UriInfo context;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private List<Usuarios> listUsuarios;

    Usuarios userPaciente = new Usuarios();
    Pacientes paciente = new Pacientes();
    Contactos contacto = new Contactos();
    PeticionesSalientes peticionSaliente = new PeticionesSalientes();
    Hospitales hospital = new Hospitales();
    String codigoHospital;
    String latitudUsuario;
    String longitudUsuario;
    String nombreUsuario;
    String nss;
    String idPet;
    String comentario;

    String mensajeError = "";

    String idPeticionSaliente;
    String prioridadAlta = "1";
    String prioridadMedia = "2";
    String prioridadBaja = "3";
    String statusPP = "PP";
    String estatus;
    String recuperarEstatus;

    /**
     * Creates a new instance of ObtenerEstatusPeticionResource
     */
    public ObtenerEstatusPeticionResource() {
    }

    @POST
    @Path("/obtenerEstatusPeticion")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEstatusPeticion(@FormParam("nombreUsuario") String nombreUsu, @FormParam("idPeticionS") String idPeticion) {
        System.out.println("---->IDPET "+idPeticion);
        System.out.println("---->IDUSU "+nombreUsu);
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set peticionesSalientes = paciente.getPeticionesSalienteses();
                for (Iterator iterato3 = peticionesSalientes.iterator(); iterato3.hasNext();) {
                    peticionSaliente = (PeticionesSalientes) iterato3.next();
                    if(peticionSaliente.getIdPeticionesSalientes().equals(idPeticion)) {
                        System.out.println("-NSS-->"+paciente.getNss());
                        idPet = idPeticion;
                        estatus = peticionSaliente.getEstatus();
                        comentario = peticionSaliente.getComentario();
                    }
                }

            }
        }

        if (estatus == null) {
            System.out.println("no hay peticiones");
            s.close();
            jb.add("recuperarEstatus", "");
            return Response.ok(jb.build()).build();
        }

        if (estatus.equals("PA")) {
            System.out.println("Peticion atendida");
            recuperarEstatus = estatus;
        }

        if (estatus.equals("PR")) {
            System.out.println("Peticion Rechazada");
            recuperarEstatus = estatus;
        }

        if (estatus.equals("PNR")) {
            System.out.println("Peticion no atendida");
            recuperarEstatus = estatus;
        }

        if (estatus.equals("PP")) {
            System.out.println("Peticion pediente");
            recuperarEstatus = estatus;
        }
        if(comentario==null)
            comentario="";
        
        s.close();
        jb.add("recuperarEstatus", recuperarEstatus);
        jb.add("idPeticionSaliente", idPet);
        jb.add("comentario", comentario);
        return Response.ok(jb.build()).build();

    }

    
    @POST
    @Path("/obtenerEstatusPeticionInicio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEstatusPeticionInicio(@FormParam("nombreUsuario") String nombreUsu) {
        System.out.println("---> Entro a pet ini"+nombreUsu);
        idPeticionSaliente="";
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        PeticionesSalientesDAO peticionesSalientesDAO = new PeticionesSalientesDAO();
        nombreUsuario = nombreUsu;
        String nss = ((Pacientes)usuarioDAO.findById(s, nombreUsuario).getPacienteses().iterator().next()).getNss();
        System.out.println("nss "+nss);
        if(nss==null)
            nss="";
          
        JsonObjectBuilder jb = Json.createObjectBuilder();
        
        ArrayList<PeticionesSalientes> petSalPaciente = (ArrayList<PeticionesSalientes>) peticionesSalientesDAO.finByHospitalNss(s, nss);
        if(petSalPaciente==null)
            petSalPaciente = new ArrayList<PeticionesSalientes>();
        
        if(petSalPaciente.size()>0)
            idPeticionSaliente = petSalPaciente.get(0).getIdPeticionesSalientes();
        else{
            jb.add("idPeticionSaliente", "");
            return Response.ok(jb.build()).build();
        }
        System.out.println("---> id pet inicio. "+idPeticionSaliente);
        s.close();
        jb.add("idPeticionSaliente", idPeticionSaliente);
        
        return Response.ok(jb.build()).build();

    }
    /**
     * Retrieves representation of an instance of
     * com.saem.alertas.ObtenerEstatusPeticionResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of
     * ObtenerEstatusPeticionResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
