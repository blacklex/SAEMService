/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.alertas;

import com.hibernate.dao.UsuarioDAO;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Alejandro
 */
@Path("busquedaHospitales")
public class BuscarHospitalesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BusquedaHospitalesResource
     */
    public BuscarHospitalesResource() {
    }

    /**
     * Retrieves representation of an instance of com.saem.alertas.BuscarHospitalesResource
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/sendemail")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmail(@FormParam("email") String email) {
        UsuarioDAO usuDAO = new UsuarioDAO();
       
        System.out.println(email);
        return Response.ok(Json.createObjectBuilder().add("hola", email+usuDAO.findById("HospitalGeneral").getTipoUsuario()).build()).build();
    }
    
    
    @GET
    @Produces("application/json")
    public JsonObject getJson() {
        return Json.createObjectBuilder().add("hola", "cosa").build();
        //TODO return proper representation object
        
    }

    /**
     * PUT method for updating or creating an instance of BuscarHospitalesResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
