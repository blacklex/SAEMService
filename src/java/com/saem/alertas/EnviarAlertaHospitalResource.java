/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.alertas;

import com.hibernate.dao.HospitalDAO;
import com.hibernate.dao.PeticionesSalientesDAO;
import com.hibernate.dao.UsuarioDAO;
import com.hibernate.model.Contactos;
import com.hibernate.model.Hospitales;
import com.hibernate.model.Pacientes;
import com.hibernate.model.PeticionesSalientes;
import com.hibernate.model.Usuarios;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
@Path("enviarAlertaHospital")
public class EnviarAlertaHospitalResource {

    @Context
    private UriInfo context;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final PeticionesSalientesDAO peticionSalienteDAO = new PeticionesSalientesDAO();
    private final HospitalDAO hospitalDAO = new HospitalDAO();

    private List<Usuarios> listUsuarios;
    private List<PeticionesSalientes> listPeticiones;

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

    String mensajeError = "";

    String idPeticionSaliente;
    String prioridadAlta = "1";
    String prioridadMedia = "2";
    String prioridadBaja = "3";
    String statusPP = "PP";
    String estatus;
    String recuperarEstatus;

    //campos json retorno
    private String estatusMensaje;

    /**
     * Creates a new instance of EnviarAlertaHospitalResource
     */
    public EnviarAlertaHospitalResource() {
    }

    @POST
    @Path("/enviarAlertaHospital")
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarAlertaHospital(@FormParam("nombreUsuario") String nombreUsu, @FormParam("latitudUsuario") String latUsu, @FormParam("longitudUsuario") String longUsu,@FormParam("codigoHospital") String codigoHosp) throws ParseException {
        nombreUsuario = nombreUsu;
        latitudUsuario = latUsu;
        longitudUsuario = longUsu;
        codigoHospital = codigoHosp;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        System.out.println("--->Entro a datos pacientes");
        Boolean envioPeticion = false;
        String contactosPaciente = "";
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                nss = paciente.getNss();
                nombreUsuario = userPaciente.getNombreUsuario();
                Set contactos = paciente.getContactoses();
                int i = 0;
                System.out.println("---> "+nss+"  "+nombreUsuario);
                for (Iterator iterator3 = contactos.iterator(); iterator3.hasNext();) {
                    contacto = (Contactos) iterator3.next();
                    if (contactos.size() == 1) {
                        contactosPaciente += contacto.getCelular();
                    } else if (i == 0) {
                        contactosPaciente += contacto.getCelular();
                        i++;
                    } else {
                        contactosPaciente += "," + contacto.getCelular();
                    }
                }
            }
        }
//        System.out.println(contactosPaciente);
//        NotificadorSMS sms = new NotificadorSMS("Estoy en este este lugar", contactosPaciente);
//        sms.enviarSMS();
        //Generamos el codigo de Historial Clinico
        Calendar cal = Calendar.getInstance();
        idPeticionSaliente = cal.get(Calendar.YEAR) + "" + (cal.get(Calendar.MONTH) + 1) + "" + cal.get(Calendar.DAY_OF_MONTH) + "" + cal.get(Calendar.HOUR) + "" + cal.get(Calendar.MINUTE) + "" + cal.get(Calendar.SECOND) + "" + cal.get(Calendar.MILLISECOND);

        //Buscamos el hospital que se encargara del paciente
        hospital = hospitalDAO.findById(s, codigoHospital);

        //Fecha de Registro
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaRegistro = hourdateFormat.format(date);
        date = hourdateFormat.parse(fechaRegistro);
        
        peticionSaliente.setIdPeticionesSalientes(idPeticionSaliente);
        peticionSaliente.setFechaRegistro(date);
        peticionSaliente.setEstatus(statusPP);
        peticionSaliente.setLatitudPaciente(latitudUsuario);
        peticionSaliente.setLongitudPaciente(longitudUsuario);
        peticionSaliente.setPrioridad(prioridadAlta);

        peticionSaliente.setHospitales(hospital);
        peticionSaliente.setPacientes(paciente);

        listPeticiones = peticionSalienteDAO.finByHospitalNss(s, nss);
        
        if (listPeticiones.isEmpty()) {
            if (peticionSalienteDAO.save(peticionSaliente)) {
                jb.add("idPeticionSaliente", idPeticionSaliente);
                estatusMensaje = "exito";
            } else {
                estatusMensaje = "error";
                peticionSalienteDAO.delete(peticionSaliente);
            }
        } else {
            System.out.println("Hay peticiones echas por el usuario: " + nombreUsuario);
            estatusMensaje = "peticionEnviada";
        }
        s.close();
        jb.add("estatusMensaje", estatusMensaje);
        return Response.ok(jb.build()).build();
    }

    /**
     * Retrieves representation of an instance of
     * com.saem.alertas.EnviarAlertaHospitalResource
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
     * EnviarAlertaHospitalResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
