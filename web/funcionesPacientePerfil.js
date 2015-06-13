/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var prefijoURL = "http://192.168.0.8:8080/SAEMService/";
$(document).ready(function () {
     muestraInformacionPerfilPaciente("paciente22");
   


});


/*******************************************************************************/

function verDatosAccesoPaciente(nombreUsuario) {
    
    $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarDatosAccesoPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            
            $("#clave").val(msg.clave);
            $("#nombreUsuario").val(msg.nombreUsuario);
            $("#clave").attr('readonly', true);
            $("#nombreUsuario").attr('readonly', true);
        });

        $("#datosAccesoPaciente").show(1000);

//        
//        $("#fechaRegistro").val(fechaRegistro);
//        $("#datosAccesoPaciente").show(1000);
        

}

function verDatosPaciente(nombreUsuario) {

        $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarDatosPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            var urlImg = 'imagenesPerfilPaciente/'+msg.nombreUsuario+'.jpeg';
            $("#nss").val(msg.nss);
            $("#nss").attr('readonly', true);
            $("#nombre").val(msg.nombre);
            $("#nombre").attr('readonly', true);
            $("#apellidoPaterno").val(msg.apellidoPaterno);
            $("#apellidoPaterno").attr('readonly', true);
            $("#apellidoMaterno").val(msg.apellidoMaterno);
            $("#apellidoMaterno").attr('readonly', true);
            $("#unidadMedica").val(msg.unidadMedica);
            $("#unidadMedica").attr('readonly', true);
            $("#noConsultorio").val(msg.noConsultorio);
        });
        $("#datosPaciente").show(1000);
        
}

function verDatosDireccionPaciente(nombreUsuario) {
  
        $.ajax({
            dataType: "json",
            method: "POST",
            url: "webresources/obtenerInformacionPersonal/mostrarDatosDireccionPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            $("#calle").val(msg.calle);
            $("#calle").attr('readonly', true);
            $("#colonia").val(msg.colonia);
            $("#colonia").attr('readonly', true);
            $("#delegacion").val(msg.delegacion);
            $("#delegacion").attr('readonly', true);
            $("#entidadFederativa").val(msg.entidadFederativa);
            $("#entidadFederativa").attr('readonly', true);
            $("#codigoPostal").val(msg.codigoPostal);
            $("#codigoPostal").attr('readonly', true);
            $("#idDomPaciente").val(msg.idDomicilioPaciente);
            $("#idDomPaciente").attr('readonly', true);
           
        });

        $("#datosDireccionPaciente").show(1000);
        
    
}

function verDatosPersonalesPaciente(nombreUsuario) {
    
        $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarDatosPersonalesPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            if(msg.estadoCivil === "casado") {
                $("#radioCasado").attr('checked', true);
                $("#radioCasado").attr('readonly', true);
            }
            
            if(msg.estadoCivil === "union libre") {
                $("#radioUnionLibre").attr('checked', true);
                $("#radioUnionLibre").attr('readonly', true);
            }
            
            if(msg.estadoCivil === "soltero") {
                $("#radioSoltero").attr('checked', true);
                $("#radioSoltero").attr('readonly', true);
            }
            
            if(msg.estadoCivil === "divorciado") {
                $("#radioDivorciado").attr('checked', true);
                $("#radioDivorciado").attr('readonly', true);
            }
            
            $("#curp").val(msg.curp);
            
            if(msg.sexo === "masculino"){
                $("#radioMasculino").attr('checked', true);
                $("#radioMasculino").attr('readonly', true);
            }
            
            if(msg.sexo === "femenino"){
                $("#radioFemenino").attr('checked', true);
                $("#radioFemenino").attr('readonly', true);
            }
            
            $("#fechaNacimiento").val(msg.fechaNacimientoFormato);
            $("#fechaNacimiento").attr('readonly', true);
            $("#edad").val(msg.edad);
            $("#edad").attr('readonly', true);
            $("#peso").val(msg.peso);
            $("#peso").attr('readonly', true);
            $("#altura").val(msg.altura);
            $("#altura").attr('readonly', true);
            $("#talla").val(msg.talla);
            $("#talla").attr('readonly', true);
            $("#telCasa").val(msg.telCasa);
            $("#telCasa").attr('readonly', true);
            $("#telCel").val(msg.telCel);
            $("#telCel").attr('readonly', true);
            $("#correo").val(msg.correo);
            $("#correo").attr('readonly', true);
            $("#facebook").val(msg.facebook);
            $("#facebook").attr('readonly', true);
   
            $("#nssDatosPersonales").val(msg.nss);
            $("#nssDatosPersonales").attr('readonly', true);
        });

        $("#datosPersonalesPaciente").show(1000);
        
    
}

function verTelefonosPaciente(nombreUsuario) {
  
        $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarTelefonosPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            
            $("#TextBoxesGroupTelefonos").html(msg.telefonosDelPaciente);
            //$("#nomUs3").val(msg.nombreUsuario);
        });

        $("#datosTelefonosPaciente").show(1000);
        
    
}

function verContactosPaciente(nombreUsuario) {
    
        $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarContactosPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            $("#TextBoxesGroupContacto").html(msg.contactosDelPaciente);
            //$("#nomUs5").val(msg.nombreUsuario);
        });

        $("#datosContactosPaciente").show(1000);
        
    
}

function verDatosClinicosPaciente(nombreUsuario) {
    
    
        $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarDatosClinicosPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            if(msg.drogas === "1"){
                $("#radioDrogasSi").attr('checked', true);
                $("#radioDrogasSi").attr('readonly', true);
            }
            else {
                $("#radioDrogasNo").attr('checked', true);
                $("#radioDrogasNo").attr('readonly', true);
            }
            
            if(msg.alcohol === "1"){
                $("#radioAlcoholSi").attr('checked', true);
                $("#radioAlcoholSi").attr('readonly', true);
            }
            else {
                $("#radioAlcoholNo").attr('checked', true);
                $("#radioAlcoholNo").attr('readonly', true);
            }
            if(msg.fuma === "1"){
                $("#radioFumaSi").attr('checked', true);
                $("#radioFumaSi").attr('readonly', true);
            }
            else {
                $("#radioFumaNo").attr('checked', true);
                $("#radioFumaNo").attr('readonly', true);
            }

           
        });

        $("#datosClinicosPaciente").show(1000);
        
    
}

function verAlergiasPaciente(nombreUsuario) {
   
        $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarAlergiasPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            var alergias = msg.alergias;

            for(var i = 0; i < alergias.length; i++) {
                var alergia = new Array();
                alergia = alergias[i].split(";");
                for(var indexAlergia = 0; indexAlergia < alergia.length; indexAlergia++) {
                    if(alergia[indexAlergia] === "polen") {
                        $("#checkboxAlergia0").attr('checked', true);
                        $("#checkboxAlergia0").attr('readonly', true);
                        $("#especificarAlergia0").val(alergia[indexAlergia+1]);
                    }
                    
                    if(alergia[indexAlergia] === "acaros") {
                        $("#checkboxAlergia1").attr('checked', true);
                        $("#checkboxAlergia1").attr('readonly', true);
                        $("#especificarAlergia1").val(alergia[indexAlergia+1]);
                    }
                    
                    if(alergia[indexAlergia] === "animales") {
                        $("#checkboxAlergia2").attr('checked', true);
                        $("#checkboxAlergia2").attr('readonly', true);
                        $("#especificarAlergia2").val(alergia[indexAlergia+1]);
                    }
                    
                    if(alergia[indexAlergia] === "medicamentos") {
                        $("#checkboxAlergia3").attr('checked', true);
                        $("#checkboxAlergia3").attr('readonly', true);
                        $("#especificarAlergia3").val(alergia[indexAlergia+1]);
                    }
                    
                    if(alergia[indexAlergia] === "insectos") {
                        $("#checkboxAlergia4").attr('checked', true);
                        $("#checkboxAlergia4").attr('readonly', true);
                        $("#especificarAlergia4").val(alergia[indexAlergia+1]);
                    }
                    
                    if(alergia[indexAlergia] === "alimentos") {
                        $("#checkboxAlergia5").attr('checked', true);
                        $("#checkboxAlergia5").attr('readonly', true);
                        $("#especificarAlergia5").val(alergia[indexAlergia+1]);
                    }
                }
            }
        });

        $("#datosAlergiasPaciente").show(1000);
        
    
}

function verCirugiasPaciente(nombreUsuario) {
    
        $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarCirugiasPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            var cirugias = msg.cirugias;

            for(var i = 0; i < cirugias.length; i++) {
                var cirugia = new Array();
                cirugia = cirugias[i].split(";");
                for(var indexCirugia = 0; indexCirugia < cirugia.length; indexCirugia++) {
                    if(cirugia[indexCirugia] === "interna") {
                        $("#checkboxCirugia0").attr('checked', true);
                        $("#checkboxCirugia0").attr('readonly', true);
                        $("#noCirugia0").val(cirugia[indexCirugia+1]);
                    }
                    
                    if(cirugia[indexCirugia] === "externa") {
                        $("#checkboxCirugia1").attr('checked', true);
                        $("#checkboxCirugia1").attr('readonly', true);
                        $("#noCirugia1").val(cirugia[indexCirugia+1]);
                    }
                    
                    if(cirugia[indexCirugia] === "mayor") {
                        $("#checkboxCirugia2").attr('checked', true);
                        $("#checkboxCirugia2").attr('readonly', true);
                        $("#noCirugia2").val(cirugia[indexCirugia+1]);
                    }
                    
                    if(cirugia[indexCirugia] === "menor") {
                        $("#checkboxCirugia3").attr('checked', true);
                        $("#checkboxCirugia3").attr('readonly', true);
                        $("#noCirugia3").val(cirugia[indexCirugia+1]);
                    }
                    
                    if(cirugia[indexCirugia] === "curativa") {
                        $("#checkboxCirugia4").attr('checked', true);
                        $("#checkboxCirugia4").attr('readonly', true);
                        $("#noCirugia4").val(cirugia[indexCirugia+1]);
                    }
                    
                    if(cirugia[indexCirugia] === "reparadora") {
                        $("#checkboxCirugia5").attr('checked', true);
                        $("#checkboxCirugia5").attr('readonly', true);
                        $("#noCirugia5").val(cirugia[indexCirugia+1]);
                    }
                    
                    if(cirugia[indexCirugia] === "paliativa") {
                        $("#checkboxCirugia6").attr('checked', true);
                        $("#checkboxCirugia6").attr('readonly', true);
                        $("#noCirugia6").val(cirugia[indexCirugia+1]);
                    }
                    
                    if(cirugia[indexCirugia] === "cosmetica") {
                        $("#checkboxCirugia7").attr('checked', true);
                        $("#checkboxCirugia7").attr('readonly', true);
                        $("#noCirugia7").val(cirugia[indexCirugia+1]);
                    }
                }
            }
        });

        $("#datosCirugiasPaciente").show(1000);
        
    
}

function verDiscapacidadesPaciente(nombreUsuario) {
    
        $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarDiscapacidadesPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            var discapacidades = msg.discapacidades;            
            for(var i = 0; i < discapacidades.length; i++) {
                var discapacidad = new Array();
                discapacidad = discapacidades[i].split(";");
                for(var indexDiscapacidad = 0; indexDiscapacidad < discapacidad.length; indexDiscapacidad++) {
                    if(discapacidad[indexDiscapacidad] === "fisica") {
                        $("#checkboxDiscapacidad0").attr('checked', true);
                        $("#checkboxDiscapacidad0").attr('readonly', true);
                    }
                    
                    if(discapacidad[indexDiscapacidad] === "intelectual") {
                        $("#checkboxDiscapacidad1").attr('checked', true);
                        $("#checkboxDiscapacidad1").attr('readonly', true);
                    }
                    
                    if(discapacidad[indexDiscapacidad] === "psiquica") {
                        $("#checkboxDiscapacidad2").attr('checked', true);
                        $("#checkboxDiscapacidad2").attr('readonly', true);
                    }
                    
                    if(discapacidad[indexDiscapacidad] === "visual") {
                        $("#checkboxDiscapacidad3").attr('checked', true);
                        $("#checkboxDiscapacidad3").attr('readonly', true);
                    }
                    
                    if(discapacidad[indexDiscapacidad] === "auditiva") {
                        $("#checkboxDiscapacidad4").attr('checked', true);
                        $("#checkboxDiscapacidad4").attr('readonly', true);
                    }
                    
                    if(discapacidad[indexDiscapacidad] === "habla") {
                        $("#checkboxDiscapacidad5").attr('checked', true);
                        $("#checkboxDiscapacidad5").attr('readonly', true);
                    }

                }
            }
        });

        $("#datosDiscapacidadesPaciente").show(1000);
        
    
}

function verMedicamentosPaciente(nombreUsuario) {
    
    
        $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarMedicamentosPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            $("#TextBoxesGroup").html(msg.medicamentos);
        });

        $("#datosMedicamentosPaciente").show(1000);
        
    
}

function verEnfermedadesCronicasPaciente(nombreUsuario) {
    
         $.ajax({
            dataType: "json",
            method: "POST",
            url: prefijoURL+"webresources/obtenerInformacionPersonal/mostrarEnfermedadesCronicasPaciente",
            data: {nombreUsuario: nombreUsuario}
        }).done(function (msg) {
            $("#TextBoxesGroupEnfermedadCronica").html(msg.enfermedadesCronicas);
        });

        $("#datosEnfermedadesPaciente").show(1000);
        
    
}

/*******************************************************************************/

function muestraInformacionPerfilPaciente(nombreUsuario) {

    verDatosAccesoPaciente(nombreUsuario);
    verDatosPaciente(nombreUsuario);
    verDatosDireccionPaciente(nombreUsuario);
    verDatosPersonalesPaciente(nombreUsuario);
    verTelefonosPaciente(nombreUsuario);
    
    verContactosPaciente(nombreUsuario);
    verDatosClinicosPaciente(nombreUsuario);
    
    verAlergiasPaciente(nombreUsuario);
    verCirugiasPaciente(nombreUsuario);
    verDiscapacidadesPaciente(nombreUsuario);
    verMedicamentosPaciente(nombreUsuario);
    
    verEnfermedadesCronicasPaciente(nombreUsuario);
   
}