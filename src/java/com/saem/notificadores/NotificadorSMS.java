/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.notificadores;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author Alejandro
 */
public class NotificadorSMS {

    private String mensaje;
    private String numero;// = "5529421329,5564164421";
    private final String urlRequest = "https://smsmasivos.com.mx/sms/api.multienvio.new.php";
    private final String apiKey = "7ed6648418b70bf6034e4c639433532a4c58f882";

    public NotificadorSMS(String mensaje, String numero) {
        this.mensaje = mensaje;
        this.numero = numero;
    }

    public Boolean enviarSMS() throws UnsupportedEncodingException {
        String urlParametros = "apikey=" + URLEncoder.encode(apiKey, "UTF-8")
                + "&mensaje=" + URLEncoder.encode(mensaje, "UTF-8")
                + "&numcelular=" + URLEncoder.encode(numero, "UTF-8")
                + "&numregion=" + URLEncoder.encode("52", "UTF-8");
        // + "&sandbox=" + URLEncoder.encode("1", "UTF-8");

        URL url;
        HttpURLConnection conexion = null;
        try {
            //-----------------------crear conexion----------------------------
            url = new URL(urlRequest);
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conexion.setRequestProperty("Content-Length", "" + Integer.toString(urlParametros.getBytes().length));
            conexion.setRequestProperty("Content-Language", "en-US");

            conexion.setUseCaches(false);
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            //-----------------------------------------------------------------

            //--------------------Enviar Peticion------------------------------
            DataOutputStream wr = new DataOutputStream(conexion.getOutputStream());
            wr.writeBytes(urlParametros);
            wr.flush();
            wr.close();
            //-----------------------------------------------------------------
            //-----------------------Obtener Respuesta-------------------------
            InputStream is = conexion.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            StringBuffer responsee = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                responsee.append(line);
                responsee.append('\r');
            }
            rd.close();
            String respuesta = responsee.toString();
            System.out.println("---> REspuesta: " + respuesta);
            if (respuesta.contains("ok")) {
                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }

        return false;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
