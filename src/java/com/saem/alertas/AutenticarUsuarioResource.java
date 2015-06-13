/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.alertas;

import com.hibernate.dao.UsuarioDAO;
import com.hibernate.model.Usuarios;
import java.text.ParseException;
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
@Path("autenticarUsuario")
public class AutenticarUsuarioResource {

    @Context
    private UriInfo context;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    //campos del formulario
    private String formNombreUsuario;
    private String formClave;
    private String formCheckRecordarme;

    private String tituloAlert = "";
    private String textoAlert = "";
    private String estatusMensaje = "";

    /**
     * Creates a new instance of AutenticarUsuarioResource
     */
    public AutenticarUsuarioResource() {
    }

    @POST
    @Path("/auntenticarUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response auntenticarUsuario(@FormParam("formNombreUsuario") String nombreUsu, @FormParam("formClave") String claveUsu) throws ParseException {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("\n\nEntro al execute" + formNombreUsuario + "  " + formClave + "  " + formCheckRecordarme);
        Usuarios usuario = usuarioDAO.findById(s, formNombreUsuario);

        if (usuario == null) {
            tituloAlert = "Error al Iniciar Sesion";
            textoAlert = "El Usuario o Clave de Acceso no son validos.";
            estatusMensaje = "errorLogin";
            s.close();
            jb.add("tituloAlert", tituloAlert);
            jb.add("textoAlert", textoAlert);
            jb.add("estatusMensaje", estatusMensaje);
            return Response.ok(jb.build()).build();
        }

        String nombreUsuario = usuario.getNombreUsuario();
        String clave = usuario.getClave();

        if (!(nombreUsuario.equals(formNombreUsuario) && clave.equals(formClave))) {
            tituloAlert = "Error al Iniciar Sesion";
            textoAlert = "El Usuario o Clave de Acceso no son validos.";
            estatusMensaje = "errorLogin";
            s.close();
            jb.add("tituloAlert", tituloAlert);
            jb.add("textoAlert", textoAlert);
            jb.add("estatusMensaje", estatusMensaje);
            return Response.ok(jb.build()).build();
        }

        tituloAlert = "Exito";
        textoAlert = "Exito";
        estatusMensaje = "exitoLogin";
        jb.add("tituloAlert", tituloAlert);
        jb.add("textoAlert", textoAlert);
        jb.add("estatusMensaje", estatusMensaje);

        s.close();
        return Response.ok(jb.build()).build();
    }

    /**
     * Retrieves representation of an instance of
     * com.saem.alertas.AutenticarUsuarioResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of
     * AutenticarUsuarioResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
