/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.alertas;

import com.hibernate.dao.PacienteDAO;
import com.hibernate.dao.UsuarioDAO;
import com.hibernate.model.Alergias;
import com.hibernate.model.Cirugias;
import com.hibernate.model.Contactos;
import com.hibernate.model.DatosClinicos;
import com.hibernate.model.DatosPersonales;
import com.hibernate.model.Discapacidades;
import com.hibernate.model.DomicilioPacientes;
import com.hibernate.model.EnfermedadesCronicas;
import com.hibernate.model.Hospitales;
import com.hibernate.model.Medicacion;
import com.hibernate.model.Pacientes;
import com.hibernate.model.TelefonosPacientes;
import com.hibernate.model.Usuarios;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
@Path("obtenerInformacionPersonal")
public class ObtenerInformacionPersonalResource {

    @Context
    private UriInfo context;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();

    private List<Usuarios> listUsuarios;

    Usuarios userPaciente = new Usuarios();
    Pacientes paciente = new Pacientes();
    Hospitales hospital = new Hospitales();
    DomicilioPacientes domicilioPacientes = new DomicilioPacientes();
    TelefonosPacientes telefonosPacientes = new TelefonosPacientes();
    DatosPersonales datosPersonales = new DatosPersonales();
    Contactos contactos = new Contactos();
    DatosClinicos datosClinicos = new DatosClinicos();
    Alergias alergia = new Alergias();
    Cirugias cirugia = new Cirugias();
    Discapacidades discapacidad = new Discapacidades();
    Medicacion medicacion = new Medicacion();
    EnfermedadesCronicas enfermedadCronica = new EnfermedadesCronicas();

    private String codigoHospital;
    private Long noHistorial;
    private ArrayList<String> discapacidades = new ArrayList<>();
    private ArrayList<String> alergias = new ArrayList<>();
    private ArrayList<String> cirugias = new ArrayList<>();

    private String fechaNacimientoFormato;
    private String medicamentos;
    private String enfermedadesCronicas;
    private String contactosDelPaciente;

    //Acceso
    private String nombreUsuario; //Clave Primaria
    private String clave;
    //Datos 
    private String nss;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String unidadMedica;
    private String noConsultorio;
    private byte[] imagenPaciente;
    //Direccion
    Long idDomicilioPaciente;
    private String calle;
    private String colonia;
    private String delegacion;
    private String entidadFederativa;
    private String codigoPostal;
    //Telefonos
    private String telefonosDelPaciente;

    //Datos personales
    Long idDatosPersonalesPaciente;
    private String estadoCivil;
    private String curp;
    private String sexo;
    private Date fechaNacimiento;
    private String edad;
    private String peso;
    private String altura;
    private String talla;
    private String correo;
    private String facebook;
    //Datos clínicos
    private String drogas;
    private String alcohol;
    private String fuma;
    //campos json retorno
    private String mensajeError = "";

    /**
     * Creates a new instance of ObtenerInformacionPersonalResource
     */
    public ObtenerInformacionPersonalResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.saem.alertas.ObtenerInformacionPersonalResource
     *
     * @return an instance of javax.json.JsonObject
     */
    @GET
    @Produces("application/json")
    public JsonObject getJson() {
        return Json.createObjectBuilder().add("hola", "cosa").build();
    }

    /**
     * PUT method for updating or creating an instance of
     * ObtenerInformacionPersonalResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(JsonObject content) {
    }

    @POST
    @Path("/mostrarDatosAccesoPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarDatosAccesoPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        System.out.println("--->Entro a datos acceso pacientes");
        listUsuarios = usuarioDAO.listarById(s, nombreUsu);
        JsonObjectBuilder jb = Json.createObjectBuilder();
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            nombreUsuario = userPaciente.getNombreUsuario();
            clave = userPaciente.getClave();
            jb.add("nombreUsuario", nombreUsuario);
            jb.add("clave", clave);

        }
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarDatosPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarDatosPaciente(@FormParam("nombreUsuario") String nombreUsu) throws IOException {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("--->Entro a datos pacientes");
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();

            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                nss = paciente.getNss();
                nombre = paciente.getNombre();
                apellidoPaterno = paciente.getApellidoPaterno();
                apellidoMaterno = paciente.getApellidoMaterno();
                unidadMedica = paciente.getUnidadMedica();
                noConsultorio = paciente.getNoConsultorio();
                //imagenPaciente = paciente.getImagen();
                /*String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
                 System.out.println(filePath);
                 FileOutputStream image = new FileOutputStream(filePath+"imagenesPerfilPaciente/"+nombreUsuario+".jpeg");
                 image.write(imagenPaciente);*/
                nombreUsuario = userPaciente.getNombreUsuario();
                System.out.println(nss);
                System.out.println(nombre);
                System.out.println(apellidoPaterno);
                System.out.println(apellidoMaterno);
                System.out.println(unidadMedica);
                System.out.println(noConsultorio);
                System.out.println(nombreUsuario);
                codigoHospital = pacienteDAO.obtenerCogigoHospital(nss);
                System.out.println(codigoHospital);

                //image.close();
            }
            jb.add("nss", nss);
            jb.add("nombre", nombre);
            jb.add("apellidoPaterno", apellidoPaterno);
            jb.add("apellidoMaterno", apellidoMaterno);
            jb.add("unidadMedica", unidadMedica);
            jb.add("noConsultorio", noConsultorio);
            jb.add("nombreUsuario", nombreUsuario);
            jb.add("codigoHospital", codigoHospital);
        }
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarDatosDireccionPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarDatosDireccionPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("--->Entro a direccion pacientes");
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set domPaciente = paciente.getDomicilioPacienteses();
                for (Iterator iterator3 = domPaciente.iterator(); iterator3.hasNext();) {
                    domicilioPacientes = (DomicilioPacientes) iterator3.next();
                    idDomicilioPaciente = domicilioPacientes.getId();
                    calle = domicilioPacientes.getCalle();
                    colonia = domicilioPacientes.getColonia();
                    delegacion = domicilioPacientes.getDelegacion();
                    entidadFederativa = domicilioPacientes.getEntidadFederativa();
                    codigoPostal = domicilioPacientes.getCodigoPostal();
                    nss = paciente.getNss();
                    nombreUsuario = userPaciente.getNombreUsuario();
                    System.out.println(idDomicilioPaciente);
                    System.out.println(calle);
                    System.out.println(colonia);
                    System.out.println(delegacion);
                    System.out.println(entidadFederativa);
                    System.out.println(codigoPostal);
                    System.out.println(nss);
                    System.out.println(nombreUsuario);
                }
            }
        }
        jb.add("idDomicilioPaciente", idDomicilioPaciente);
        jb.add("calle", calle);
        jb.add("colonia", colonia);
        jb.add("delegacion", delegacion);
        jb.add("entidadFederativa", entidadFederativa);
        jb.add("codigoPostal", codigoPostal);
        jb.add("nss", nss);
        jb.add("nombreUsuario", nombreUsuario);
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarTelefonosPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarTelefonosPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("--->Entro a ver telefonos pacientes");
        String html = "";
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set telPaciente = paciente.getTelefonosPacienteses();
                int index = 0;
                for (Iterator iterator3 = telPaciente.iterator(); iterator3.hasNext();) {
                    telefonosPacientes = (TelefonosPacientes) iterator3.next();
                    html += "<div id=\"divTelefonoFijoPaciente\" class=\"form-group\">"
                            + "   <label for=\"telefonoPaciente\">Teléfono #" + (index + 1) + "</label>"
                            + "   <input readonly=\"true\" kl_virtual_keyboard_secure_input=\"on\" value=\"" + telefonosPacientes.getNumeroTelefono() + "\" name=\"numTelefono" + index + "\" id=\"numTelefono" + index + "\" class=\"form-control\" data-inputmask=\"&quot;mask&quot;: &quot;(99-99) 9999-9999&quot;\" data-mask=\"\" placeholder=\"No. Telefono\" type=\"text\">"
                            + "</div>";
                    System.out.println(index);
                    System.out.println("Telefono-->" + telefonosPacientes.getNumeroTelefono() + " Id: " + telefonosPacientes.getId() + " NSS:" + paciente.getNss());
                    index++;
                }
            }
        }

        telefonosDelPaciente = html;
        System.out.println(telefonosDelPaciente);
        jb.add("telefonosDelPaciente", telefonosDelPaciente);
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarDatosPersonalesPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarDatosPersonalesPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("--->Entro a ver datos personales pacientes");
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set datPerPaciente = paciente.getDatosPersonaleses();
                for (Iterator iterator3 = datPerPaciente.iterator(); iterator3.hasNext();) {
                    datosPersonales = (DatosPersonales) iterator3.next();
                    idDatosPersonalesPaciente = datosPersonales.getId();
                    estadoCivil = datosPersonales.getEstadoCivil();
                    curp = datosPersonales.getCurp();
                    sexo = datosPersonales.getSexo();
                    fechaNacimiento = datosPersonales.getFechaNacimiento();
                    DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    fechaNacimientoFormato = hourdateFormat.format(fechaNacimiento);
                    edad = datosPersonales.getEdad();
                    peso = datosPersonales.getPeso();
                    altura = datosPersonales.getAltura();
                    talla = datosPersonales.getTalla();
                    correo = datosPersonales.getCorreo();
                    facebook = datosPersonales.getFacebook();
                    nombreUsuario = userPaciente.getNombreUsuario();
                    nss = paciente.getNss();
                    System.out.println("fecha con formato--->" + fechaNacimientoFormato);
                    System.out.println(idDatosPersonalesPaciente);
                    System.out.println(estadoCivil);
                    System.out.println(curp);
                    System.out.println(sexo);
                    System.out.println(fechaNacimiento);
                    System.out.println(edad);
                    System.out.println(peso);
                    System.out.println(altura);
                    System.out.println(talla);
                    System.out.println(correo);
                    System.out.println(facebook);
                    System.out.println(nss);
                    System.out.println(nombreUsuario);
                }
            }
        }

        jb.add("fechaNacimientoFormato", fechaNacimientoFormato);
        jb.add("idDatosPersonalesPaciente", idDatosPersonalesPaciente);
        jb.add("estadoCivil", estadoCivil);
        jb.add("curp", curp);
        jb.add("sexo", sexo);
        jb.add("fechaNacimiento", fechaNacimiento.toString());
        jb.add("edad", edad);
        jb.add("peso", peso);
        jb.add("altura", altura);
        jb.add("talla", talla);
        jb.add("correo", correo);
        jb.add("facebook", facebook);
        jb.add("nss", nss);
        jb.add("nombreUsuario", nombreUsuario);
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarContactosPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarContactosPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("--->Entro a ver contactos pacientes");
        String html = "";
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set contacPaciente = paciente.getContactoses();
                int index = 0;
                for (Iterator iterator3 = contacPaciente.iterator(); iterator3.hasNext();) {
                    contactos = (Contactos) iterator3.next();
                    html += "<label for=\"nombreC\">Contacto #" + (index + 1) + "</label>" + "\n"
                            + "<div id=\"divNombreCPaciente" + index + "\" class=\"form-group\">" + "\n"
                            + "   <label for=\"nombreC\">Nombre</label>" + "\n"
                            + "   <input readonly=\"true\" kl_virtual_keyboard_secure_input=\"on\" value=\"" + contactos.getNombre() + "\" class=\"form-control\" name=\"nombreContacto" + index + "\" id=\"nombreContacto" + index + "\" placeholder=\"Nombre\" type=\"text\">" + "\n"
                            + "</div>" + "\n"
                            + "<div id=\"divApellidoPaternoCPaciente" + index + "\" class=\"form-group\">" + "\n"
                            + "   <label for=\"apellidoPaternoC\">Apellido Paterno</label>" + "\n"
                            + "   <input readonly=\"true\" kl_virtual_keyboard_secure_input=\"on\" value=\"" + contactos.getApellidoPaterno() + "\" class=\"form-control\" name=\"apellidoPaternoContacto" + index + "\" id=\"apellidoPaternoContacto" + index + "\" placeholder=\"Apellido Paterno\" type=\"text\">" + "\n"
                            + "</div>" + "\n"
                            + "<div id=\"divApellidoMaternoCPaciente" + index + "\" class=\"form-group\">" + "\n"
                            + "   <label for=\"apellidoMaternoC\">Apellido Materno</label>" + "\n"
                            + "   <input readonly=\"true\" kl_virtual_keyboard_secure_input=\"on\" value=\"" + contactos.getApellidoMaterno() + "\" class=\"form-control\" name=\"apellidoMaternoContacto" + index + "\" id=\"apellidoMaternoContacto" + index + "\" placeholder=\"Apellido Materno\" type=\"text\">" + "\n"
                            + "</div>" + "\n"
                            + "<div id=\"divParentescoCPaciente" + index + "\" class=\"form-group\">" + "\n"
                            + "   <label for=\"parentesco\">Parentesco</label>" + "\n"
                            + "   <input readonly=\"true\" kl_virtual_keyboard_secure_input=\"on\" value=\"" + contactos.getParentesco() + "\" class=\"form-control\" name=\"parentescoContacto" + index + "\" id=\"parentescoContacto" + index + "\" placeholder=\"Parentesco\" type=\"text\">" + "\n"
                            + "</div>" + "\n"
                            + "<div id=\"divCelularCPaciente" + index + "\" class=\"form-group\">" + "\n"
                            + "   <label for=\"celular\">Telefono Celular</label>" + "\n"
                            + "   <input readonly=\"true\" kl_virtual_keyboard_secure_input=\"on\" value=\"" + contactos.getCelular() + "\" name=\"celularContacto" + index + "\" id=\"celularContacto" + index + "\" class=\"form-control\" data-inputmask=\"&quot;mask&quot;: &quot;(99-99) 9999-9999&quot;\" data-mask=\"\" placeholder=\"Celular\" type=\"text\">" + "\n"
                            + "</div>" + "\n"
                            + "<div id=\"divFacebookCPaciente" + index + "\" class=\"form-group\">" + "\n"
                            + "   <label for=\"facebookC\">Facebook (www.facebook.com/alguien)</label>" + "\n"
                            + "   <input readonly=\"true\" kl_virtual_keyboard_secure_input=\"on\" value=\"" + contactos.getFacebook() + "\" class=\"form-control\" name=\"facebookContacto" + index + "\" id=\"facebookContacto" + index + "\" placeholder=\"Facebook\" type=\"text\">" + "\n"
                            + "</div>" + "\n"
                            + "<div id=\"divCorreoCPaciente" + index + "\" class=\"form-group\">" + "\n"
                            + "   <label for=\"correoC\">Email</label>" + "\n"
                            + "   <input readonly=\"true\" kl_virtual_keyboard_secure_input=\"on\" value=\"" + contactos.getCorreo() + "\" class=\"form-control\" name=\"correoContacto" + index + "\" id=\"correoContacto" + index + "\" placeholder=\"Email\" type=\"text\">" + "\n"
                            + "</div>" + "\n";
                    System.out.println(index);
                    System.out.println("Id: " + contactos.getId() + "\n"
                            + "Nombre: " + contactos.getNombre() + "\n"
                            + "ApellidoP: " + contactos.getApellidoPaterno() + "\n"
                            + "ApellidoM" + contactos.getApellidoMaterno() + "\n"
                            + "Parentesco: " + contactos.getParentesco() + "\n"
                            + "Celular:" + contactos.getCelular() + "\n"
                            + "Face: " + contactos.getFacebook() + "\n"
                            + "Correo:" + contactos.getCorreo() + "\n"
                            + "Nss: " + paciente.getNss());
                    index++;
                }
            }
        }
        contactosDelPaciente = html;
        jb.add("contactosDelPaciente", contactosDelPaciente);
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarDatosClinicosPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarDatosClinicosPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("--->Entro a ver datos clinicos pacientes");
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set datCliPaciente = paciente.getDatosClinicoses();
                for (Iterator iterator3 = datCliPaciente.iterator(); iterator3.hasNext();) {
                    datosClinicos = (DatosClinicos) iterator3.next();
                    noHistorial = datosClinicos.getNoHistorial();
                    if (datosClinicos.isUsoDrogas() == true) {
                        drogas = "1";
                    } else {
                        drogas = "0";
                    }
                    if (datosClinicos.isUsoAlcohol() == true) {
                        alcohol = "1";
                    } else {
                        alcohol = "0";
                    }
                    if (datosClinicos.isFumador() == true) {
                        fuma = "1";
                    } else {
                        fuma = "0";
                    }
                    nombreUsuario = userPaciente.getNombreUsuario();
                    nss = paciente.getNss();
                    System.out.println(noHistorial);
                    System.out.println("drogas " + drogas);
                    System.out.println("alcohol " + alcohol);
                    System.out.println("fuma " + fuma);
                    System.out.println(nombreUsuario);
                    System.out.println(nss);
                }
            }
        }
        jb.add("nombreUsuario", nombreUsuario);
        jb.add("nss", nss);
        jb.add("drogas", drogas);
        jb.add("alcohol", alcohol);
        jb.add("fuma", fuma);
        jb.add("noHistorial", noHistorial);
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarAlergiasPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarAlergiasPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        JsonArrayBuilder array = Json.createArrayBuilder();
        System.out.println("--->Entro a ver alergias pacientes");
        ArrayList<String> nombreAlergia = new ArrayList<>();
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set datCliPaciente = paciente.getDatosClinicoses();
                for (Iterator iterator3 = datCliPaciente.iterator(); iterator3.hasNext();) {
                    datosClinicos = (DatosClinicos) iterator3.next();
                    Set datClinicosAlergias = datosClinicos.getAlergiases();
                    for (Iterator iterator5 = datClinicosAlergias.iterator(); iterator5.hasNext();) {
                        alergia = (Alergias) iterator5.next();
                        nombreAlergia.add(alergia.getTipoAlergia() + ";" + alergia.getEspecificacion() + ";" + alergia.getId());
                    }
                }
            }
        }
        alergias = nombreAlergia;

        for (String alergiaTemp : alergias) {
            array.add(alergiaTemp);
        }

        System.out.println(alergias);
        jb.add("alergias", array.build());
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarCirugiasPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarCirugiasPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("--->Entro a ver cirugias pacientes");
        ArrayList<String> nombreCirugia = new ArrayList<>();
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set datCliPaciente = paciente.getDatosClinicoses();
                for (Iterator iterator3 = datCliPaciente.iterator(); iterator3.hasNext();) {
                    datosClinicos = (DatosClinicos) iterator3.next();
                    Set datClinicosCirugias = datosClinicos.getCirugiases();
                    for (Iterator iterator4 = datClinicosCirugias.iterator(); iterator4.hasNext();) {
                        cirugia = (Cirugias) iterator4.next();
                        nombreCirugia.add(cirugia.getTipoCirugia() + ";" + cirugia.getNoCirugia() + ";" + cirugia.getId());
                    }
                }
            }
        }
        cirugias = nombreCirugia;

        JsonArrayBuilder array = Json.createArrayBuilder();
        for (String cirugiaTemp : cirugias) {
            array.add(cirugiaTemp);
        }

        jb.add("cirugias", array.build());

        System.out.println(cirugias);
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarDiscapacidadesPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarDiscapacidadesPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("--->Entro a ver discapacidades pacientes");
        ArrayList<String> nombreDiscapacidad = new ArrayList<>();
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set datCliPaciente = paciente.getDatosClinicoses();
                for (Iterator iterator3 = datCliPaciente.iterator(); iterator3.hasNext();) {
                    datosClinicos = (DatosClinicos) iterator3.next();
                    Set datClinicosDiscapacidades = datosClinicos.getDiscapacidadeses();
                    for (Iterator iterator4 = datClinicosDiscapacidades.iterator(); iterator4.hasNext();) {
                        discapacidad = (Discapacidades) iterator4.next();
                        nombreDiscapacidad.add(discapacidad.getTipo() + ";" + discapacidad.getId());
                    }
                }
            }
        }
        discapacidades = nombreDiscapacidad;

        JsonArrayBuilder array = Json.createArrayBuilder();
        for (String discapacidadesTemp : discapacidades) {
            array.add(discapacidadesTemp);
        }

        jb.add("discapacidades", array.build());
        System.out.println(discapacidades);
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarMedicamentosPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarMedicamentosPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("--->Entro a ver medicamentos pacientes");
        String html = "";
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set datCliPaciente = paciente.getDatosClinicoses();
                for (Iterator iterator3 = datCliPaciente.iterator(); iterator3.hasNext();) {
                    datosClinicos = (DatosClinicos) iterator3.next();
                    Set datClinicosMedicacion = datosClinicos.getMedicacions();
                    int index = 0;
                    for (Iterator iterator4 = datClinicosMedicacion.iterator(); iterator4.hasNext();) {
                        medicacion = (Medicacion) iterator4.next();
                        html += "<div id=\"medicamentos" + index + "\" class=\"row\">" + "\n"
                                + "   <div class=\"col-lg-6\">" + "\n"
                                + "       <div style=\"margin-bottom:10px;\" class=\"form-group\">" + "\n"
                                + "           <label>Nombre del medicamento #" + (index + 1) + "</label>" + "\n"
                                + "           <input readonly=\"true\" class=\"form-control\" type=\"text\" value=\"" + medicacion.getNombreMedicamento() + "\" name=\"medicamento" + index + "\" id=\"medicamento" + index + "\" placeholder=\"Medicamentó" + (index + 1) + "\"/>" + "\n"
                                + "       </div>" + "\n"
                                + "   </div>" + "\n"
                                + "   <div class=\"col-lg-6\">" + "\n"
                                + "       <div style=\"margin-bottom:10px;\" class=\"form-group\">" + "\n"
                                + "           <label>Frecuencia</label>" + "\n"
                                + "           <input readonly=\"true\" class=\"form-control\" type=\"text\" value=\"" + medicacion.getFrecuencia() + "\" name=\"frecuencia" + index + "\" id=\"frecuencia" + index + "\" placeholder=\"Frecuencia\"/>" + "\n"
                                + "       </div>" + "\n"
                                + "   </div>" + "\n"
                                + "</div>";
                        index++;
                    }
                }
            }
        }
        medicamentos = html;
        jb.add("medicamentos", medicamentos);
        System.out.println(medicamentos);
        s.close();
        return Response.ok(jb.build()).build();
    }

    @POST
    @Path("/mostrarEnfermedadesCronicasPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarEnfermedadesCronicasPaciente(@FormParam("nombreUsuario") String nombreUsu) {
        Session s = com.hibernate.cfg.HibernateUtil.getSession();
        nombreUsuario = nombreUsu;
        JsonObjectBuilder jb = Json.createObjectBuilder();
        System.out.println("--->Entro a ver enfermedades cronicas pacientes");
        String html = "";
        listUsuarios = usuarioDAO.listarById(s, nombreUsuario);
        for (Iterator iterator1 = listUsuarios.iterator(); iterator1.hasNext();) {
            userPaciente = (Usuarios) iterator1.next();
            Set pacientes = userPaciente.getPacienteses();
            for (Iterator iterator2 = pacientes.iterator(); iterator2.hasNext();) {
                paciente = (Pacientes) iterator2.next();
                Set datCliPaciente = paciente.getDatosClinicoses();
                for (Iterator iterator3 = datCliPaciente.iterator(); iterator3.hasNext();) {
                    datosClinicos = (DatosClinicos) iterator3.next();
                    Set datClinicosEnfermedadesCronicas = datosClinicos.getEnfermedadesCronicases();
                    int index = 0;
                    for (Iterator iterator4 = datClinicosEnfermedadesCronicas.iterator(); iterator4.hasNext();) {
                        enfermedadCronica = (EnfermedadesCronicas) iterator4.next();
                        Date inicioEnfermedad = enfermedadCronica.getIncioEnfermedad();
                        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String inicioEnfermedadFormato = hourdateFormat.format(inicioEnfermedad);
                        String tipoEspecialidad = enfermedadCronica.getTipo();
                        html += "<div id=\"enfermedadesCronicas" + index + "\" class=\"row\">" + "\n"
                                + "   <div class=\"col-lg-4\">" + "\n"
                                + "       <div style=\"margin-bottom:10px;\" class=\"form-group\">" + "\n"
                                + "           <label>Enfermedad</label>" + "\n"
                                + "           <input disabled class=\"form-control\" type=\"text\" name=\"enfermedadCronica" + index + "\" id=\"enfermedadCronica" + index + "\" value=\"" + enfermedadCronica.getNombre() + "\" placeholder=\"Nombre enfermedad" + index + "\" />" + "\n"
                                + "       </div>" + "\n"
                                + "   </div>" + "\n"
                                + "   <div class=\"col-lg-4\">" + "\n"
                                + "       <label>Especialidad </label>" + "\n"
                                + "       <input  disabled name=\"tipoEnfermedad" + index + "\" class=\"form-control\"value=\""+ tipoEspecialidad +"\" type=\"text\"/>" + "\n"
                                + "       </select>" + "\n"
                                + "   </div>" + "\n"
                                + "   <div class=\"col-lg-4\">" + "\n"
                                + "       <div id=\"divInicioEnfermedadPaciente\" class=\"form-group\">" + "\n"
                                + "           <label for=\"inicioEnfermedad\">Inicio de enfermedad</label>" + "\n"
                                + "           <div class=\"input-group\">" + "\n"
                                + "               <div class=\"input-group-addon\">" + "\n"
                                + "                   <i class=\"fa fa-calendar\"></i>" + "\n"
                                + "               </div>" + "\n"
                                + "               <input readonly=\"true\" kl_virtual_keyboard_secure_input=\"on\" value=\"" + inicioEnfermedadFormato + "\" class=\"form-control\" name=\"inicioEnfermedad" + index + "\" id=\"inicioEnfermedad" + index + "\" data-inputmask=\"\\'alias\\': \\'dd/mm/yyyy\\'\" data-mask placeholder=\"dd/mm/yyyy\" type=\"text\">" + "\n"
                                + "           </div>" + "\n"
                                + "       </div>" + "\n"
                                + "   </div>" + "\n"
                                + "</div>";
                        index++;
                    }
                }
            }
        }
        enfermedadesCronicas = html;
        jb.add("enfermedadesCronicas", enfermedadesCronicas);
        System.out.println(enfermedadesCronicas);
        s.close();
        return Response.ok(jb.build()).build();
    }

}
