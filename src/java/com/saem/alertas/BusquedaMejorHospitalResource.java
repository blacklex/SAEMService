/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.alertas;

import com.hibernate.dao.HospitalDAO;
import com.hibernate.dao.UsuarioDAO;
import com.hibernate.model.DatosClinicos;
import com.hibernate.model.EnfermedadesCronicas;
import com.hibernate.model.Hospitales;
import com.hibernate.model.Pacientes;
import com.hibernate.model.Usuarios;
import com.persistencia.owl.OWLConsultas;
import java.util.ArrayList;
import java.util.Iterator;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.json.simple.JSONValue;

/**
 * REST Web Service
 *
 * @author sergio
 */
@Path("busquedaMejorHospital")
public class BusquedaMejorHospitalResource {

    @Context
    private UriInfo context;

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
    Double menor;

    //campos json retorno
    String tituloAlert;
    String textoAlert;
    String estatusMensaje;
    String mensajeError = "";

    List listaHospitalesCercanos = new LinkedList();
    /**
     * Creates a new instance of BusquedaMejorHospitalResource
     */
    public BusquedaMejorHospitalResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.saem.alertas.BuscarHospitalesResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/buscarMejorHospital")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarMejorHospital(@Context HttpServletRequest servletRequest,@FormParam("latitudUsuario") String latUsu, @FormParam("longitudUsuario") String longUsu, @FormParam("distancia") String distanciaUsu, @FormParam("nombreUsuario") String nombreUsuario) {
        String ONTOLOGIA =servletRequest.getSession().getServletContext().getInitParameter("ontologiaurl");
        
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        latitudUsuario = latUsu;
        longitudUsuario = longUsu;
        distancia = distanciaUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        ArrayList<Hospitales> listaTemp = new ArrayList<Hospitales>();
        ArrayList<EnfermedadesCronicas> listaPacienteEnfermedades = new ArrayList<EnfermedadesCronicas>();
        
        //Boolean hayHospitales = false;
        
        HospitalDAO hospitalDAO = new HospitalDAO();
        listaTemp = (ArrayList<Hospitales>) hospitalDAO.findAll(s);

        latUsuario = Double.parseDouble(latitudUsuario);
        longUsuario = Double.parseDouble(longitudUsuario);
        distanciaRango = Double.parseDouble(distancia);
        menor = distanciaRango;
        //-----------RECUPERAR ENFERMEDADES DEL PACIENTE --------------------
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuarios pacienteUsuario = usuarioDAO.findById(s,nombreUsuario);
        
        Pacientes paciente = (Pacientes) pacienteUsuario.getPacienteses().iterator().next();
        
        DatosClinicos pacienteDatosClinicos  = (DatosClinicos) paciente.getDatosClinicoses().iterator().next();
        Iterator pacienteEnfermedades = pacienteDatosClinicos.getEnfermedadesCronicases().iterator();
        
        while(pacienteEnfermedades.hasNext()){
            EnfermedadesCronicas enfermedad = (EnfermedadesCronicas) pacienteEnfermedades.next();
            listaPacienteEnfermedades.add(enfermedad);
        }
        
        if(listaPacienteEnfermedades==null)
            listaPacienteEnfermedades = new ArrayList<EnfermedadesCronicas>();
        else if(listaPacienteEnfermedades.isEmpty())
            listaPacienteEnfermedades = new ArrayList<EnfermedadesCronicas>();
        
        //-----------FIN RECUPERAR ENFERMEDADES DEL PACIENTE ----------------

        for(Hospitales hospTem : listaTemp) {
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
if(distanciaFinal < menor) {
            if(distanciaFinal >= 0 && distanciaFinal <= distanciaRango) {
                
                hospitales.put("lt", latitudY);
                hospitales.put("ln", longitudX);
                hospitales.put("titulo", nombreHospital);
                hospitales.put("codigo", hospTem.getCodigoHospital());
                hospitales.put("tel", hospTem.getLada()+"-"+hospTem.getTelefono());
                hospitales.put("atiendeEnfermedad", "0");
                ArrayList<String> listaEnfermedadesHospital = (ArrayList<String>) consultor.hospitalAtiendeEnfermedad(nombreHospitalTemp);
                if(listaEnfermedadesHospital==null)
                    listaEnfermedadesHospital = new ArrayList<String>();
                
                
                    for(String enfermedadHospTemp : listaEnfermedadesHospital){
                    for(EnfermedadesCronicas enfermedadPaacienteTemp : listaPacienteEnfermedades){
                        if(enfermedadHospTemp.equals(enfermedadPaacienteTemp.getNombre())) {
                            hospitales.remove("atiendeEnfermedad");
                            hospitales.put("atiendeEnfermedad", "1");
                            break;
                        }
                    }
                }
                    menor = distanciaFinal;
                    
                    
                listaHospitalesCercanos.add(hospitales);

                System.out.println("El hospital " + nombreHospital + " esta dentro de la zona");
            } 
            else 
                System.out.println("El hospital " + nombreHospital + " no esta dentro de la zona");

            System.out.println("Distancia desde el usuario hasta el hospital----->" + (Math.round(distanciaFinal))+"km");

            System.out.println("********************************************************************************");

        }}
        System.out.println("La menor diatancia es:" + Math.round(menor));
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
