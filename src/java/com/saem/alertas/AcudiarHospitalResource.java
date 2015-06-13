/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.alertas;

import com.hibernate.dao.HospitalDAO;
import com.hibernate.dao.PacienteDAO;
import com.hibernate.dao.PeticionesEntrantesDAO;
import com.hibernate.dao.UsuarioDAO;
import com.hibernate.model.Contactos;
import com.hibernate.model.Hospitales;
import com.hibernate.model.Pacientes;
import com.hibernate.model.PeticionesEntrantes;
import com.hibernate.model.Usuarios;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
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
import org.hibernate.Session;

/**
 * REST Web Service
 *
 * @author Alejandro
 */
@Path("acudiarAlHospital")
public class AcudiarHospitalResource {

    @Context
    private UriInfo context;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final PeticionesEntrantesDAO peticionEntranteDAO = new PeticionesEntrantesDAO();
    private final HospitalDAO hospitalDAO = new HospitalDAO();

    private List<Usuarios> listUsuarios;
    private List<PeticionesEntrantes> listPeticiones;

    Usuarios userPaciente = new Usuarios();
    Pacientes paciente = new Pacientes();
    Contactos contacto = new Contactos();
    PeticionesEntrantes peticionEntrante = new PeticionesEntrantes();
    Hospitales hospital = new Hospitales();
    String codigoHospital;
    String latitudUsuario;
    String longitudUsuario;
    String nombreUsuario;
    String nss;

    String mensajeError = "";

    String idPeticionEntrante;
    String prioridadAlta = "1";
    String prioridadMedia = "2";
    String prioridadBaja = "3";
    String statusPP = "PP";
    String estatus;
    String recuperarEstatus;

    //campos json retorno
    private String tituloAlert;
    private String textoAlert;
    private String estatusMensaje;

    /**
     * Creates a new instance of AcudiarAlHospitalResource
     */
    public AcudiarHospitalResource() {
    }

    @POST
    @Path("/mostrarDatosAccesoPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response acudirAlHospital(@FormParam("nombreUsuario") String nombreUsu, @FormParam("latitudUsuario") String latUsu, @FormParam("longitudUsuario") String longUsu) throws ParseException {
        nombreUsuario = nombreUsu;
        latitudUsuario = latUsu;
        longitudUsuario = longUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        System.out.println("--->Entro a datos pacientes");
        Boolean envioPeticion = false;
        String contactosPaciente = "";
        listUsuarios = usuarioDAO.listarById(nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                nss = paciente.getNss();
                nombreUsuario = userPaciente.getNombreUsuario();
                Set contactos = paciente.getContactoses();
                int i = 0;
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
//        NotificadorSMS sms = new NotificadorSMS("Estoy en este Hospital", contactosPaciente);
//        sms.enviarSMS();
        //Generamos el codigo de Historial Clinico
        Calendar cal = Calendar.getInstance();
        idPeticionEntrante = cal.get(Calendar.YEAR) + "" + (cal.get(Calendar.MONTH) + 1) + "" + cal.get(Calendar.DAY_OF_MONTH) + "" + cal.get(Calendar.HOUR) + "" + cal.get(Calendar.MINUTE) + "" + cal.get(Calendar.SECOND) + "" + cal.get(Calendar.MILLISECOND);

        //Buscamos el hospital que se encargara del paciente
        hospital = hospitalDAO.findById(codigoHospital);

        //Fecha de Registro
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaRegistro = hourdateFormat.format(date);
        date = hourdateFormat.parse(fechaRegistro);

        peticionEntrante.setIdPeticionesEntrantes(idPeticionEntrante);
        peticionEntrante.setFechaRegistro(date);
        peticionEntrante.setEstatus(statusPP);
        peticionEntrante.setLatitudPaciente(latitudUsuario);
        peticionEntrante.setLongitudPaciente(longitudUsuario);
        peticionEntrante.setPrioridad(prioridadBaja);

        peticionEntrante.setHospitales(hospital);
        peticionEntrante.setPacientes(paciente);

        listPeticiones = peticionEntranteDAO.finByHospitalNss(s, nss);

        if (listPeticiones.isEmpty()) {
            if (peticionEntranteDAO.save(peticionEntrante)) {
                estatusMensaje = "exito";
            } else {
                estatusMensaje = "error";
                peticionEntranteDAO.delete(peticionEntrante);
            }
        } else {
            System.out.println("Hay peticiones echas por el usuario: " + nombreUsuario);
            estatusMensaje = "peticionEnviada";
        }

        jb.add("estatusMensaje", estatusMensaje);
        return Response.ok(jb.build()).build();
    }

    /**
     * Retrieves representation of an instance of
     * com.saem.alertas.AcudiarHospitalResource
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
     * AcudiarHospitalResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
