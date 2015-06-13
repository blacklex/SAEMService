/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.alertas;

import com.hibernate.dao.HospitalDAO;
import com.hibernate.model.Hospitales;
import com.persistencia.owl.OWLConsultas;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
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
import org.json.simple.JSONValue;

/**
 * REST Web Service
 *
 * @author Alejandro
 */
@Path("busquedaHospitales")
public class BuscarHospitalesResource {

    @Context private UriInfo context;
    
    String BASE_URI = "http://www.serviciomedico.org/ontologies/2014/serviciomedico";
    
    String latitudUsuario;
    String longitudUsuario;
    String distancia;
    String hospitalesCercanos;

    Double latUsuario;
    Double longUsuario;
    Double distanciaRango;
    Double distanciaLatitud;
    Double distanciaLongitud;
    Double a;
    Double c;
    Double distanciaFinal;

    //campos json retorno
    String tituloAlert;
    String textoAlert;
    String estatusMensaje;
    String mensajeError = "";

    List listaHospitalesCercanos = new LinkedList();

    
    /**
     * Creates a new instance of BusquedaHospitalesResource
     */
    public BuscarHospitalesResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.saem.alertas.BuscarHospitalesResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/buscarHospitales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarHospitales(@Context HttpServletRequest servletRequest,@FormParam("latitudUsuario") String latUsu, @FormParam("longitudUsuario") String longUsu, @FormParam("distancia") String distanciaUsu) {
        String ONTOLOGIA =servletRequest.getSession().getServletContext().getInitParameter("ontologiaurl");
        
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        latitudUsuario = latUsu;
        longitudUsuario = longUsu;
        distancia = distanciaUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        ArrayList<Hospitales> listaTemp = new ArrayList<Hospitales>();
        
        //Boolean hayHospitales = false;
        HospitalDAO hospitalDAO = new HospitalDAO();
        listaTemp = (ArrayList<Hospitales>) hospitalDAO.findAll(s);

        latUsuario = Double.parseDouble(latitudUsuario);
        longUsuario = Double.parseDouble(longitudUsuario);
        distanciaRango = Double.parseDouble(distancia);

        for (Hospitales hospTem : listaTemp) {
            String nombreHospital = hospTem.getNombre();
            String nombreHospitalTemp = nombreHospital;
            nombreHospitalTemp = nombreHospitalTemp.replaceAll("\\s+", "");

            System.out.println("Hospital nombre normal--->" + nombreHospital);
            System.out.println("Hospital nombre sin espacios--->" + nombreHospitalTemp);

            OWLConsultas consultor = new OWLConsultas(ONTOLOGIA, BASE_URI);
            consultor.hospitalseUbicaEnDireccion(nombreHospitalTemp);
            consultor.getCoordenadaYDireccion("Direccion" + nombreHospitalTemp);

            String latitudY = consultor.getCoordenadaYDireccion("Direccion" + nombreHospitalTemp).get(0);
            String longitudX = consultor.getCoordenadaXDireccion("Direccion" + nombreHospitalTemp).get(0);

            distanciaLatitud = (Double.parseDouble(latitudY) - latUsuario) * Math.PI / 180;
            distanciaLongitud = (Double.parseDouble(longitudX) - longUsuario) * Math.PI / 180;
            a = Math.sin(distanciaLatitud / 2) * Math.sin(distanciaLatitud / 2) + Math.cos(latUsuario * Math.PI / 180) * Math.cos(Double.parseDouble(latitudY) * Math.PI / 180) * Math.sin(distanciaLongitud / 2) * Math.sin(distanciaLongitud / 2);
            c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            distanciaFinal = 6371.0 * c;

            Map hospitales = new LinkedHashMap();

            if (Math.round(distanciaFinal) >= 0 && Math.round(distanciaFinal) <= distanciaRango) {
                hospitales.put("lt", latitudY);
                hospitales.put("ln", longitudX);
                hospitales.put("titulo", nombreHospital);
                hospitales.put("codigo", hospTem.getCodigoHospital());
                listaHospitalesCercanos.add(hospitales);

                System.out.println("El hospital " + nombreHospital + " esta dentro de la zona");
            } else {
                System.out.println("El hospital " + nombreHospital + " no esta dentro de la zona");
            }

            System.out.println("Distancia desde el usuario hasta el hospital----->" + (Math.round(distanciaFinal)) + "km");

            System.out.println("********************************************************************************");

        }

        if (listaHospitalesCercanos.isEmpty()) {
            estatusMensaje = "vacio";
            s.close();
            return Response.ok(Json.createObjectBuilder().add("estatusMensaje", estatusMensaje).build()).build();
        } else {
            hospitalesCercanos = JSONValue.toJSONString(listaHospitalesCercanos);
            System.out.println(hospitalesCercanos);
        }

        System.out.println("Latitud: " + Double.parseDouble(latitudUsuario) + "\n"
                + "Longitud: " + Double.parseDouble(longitudUsuario) + "\n"
                + "Distancia: " + Double.parseDouble(distancia));
        System.out.println("********************************************************************************");
        s.close();
        jb.add("hospitalesCercanos", hospitalesCercanos);
        return Response.ok(jb.build()).build();
    }

    @GET
    @Produces("application/json")
    public JsonObject getJson() {
        return Json.createObjectBuilder().add("hola", "cosa").build();
        //TODO return proper representation object

    }

    /**
     * PUT method for updating or creating an instance of
     * BuscarHospitalesResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
