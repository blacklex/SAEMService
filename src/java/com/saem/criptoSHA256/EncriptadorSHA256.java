/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saem.criptoSHA256;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alejandro
 */
public class EncriptadorSHA256 {

    private String cadenaEntrada;
    private String cadenaEncriptada;

    public EncriptadorSHA256(String cadenaEntrada) {
        this.cadenaEntrada = cadenaEntrada;
    }

    public String encriptarCadena() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(cadenaEntrada.getBytes("UTF-8"));
            
            byte byteData[] = md.digest();
            
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            setCadenaEncriptada(sb.toString());
            //System.out.println("Hex format : " + sb.toString());
            
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncriptadorSHA256.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EncriptadorSHA256.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String getCadenaEntrada() {
        return cadenaEntrada;
    }

    public void setCadenaEntrada(String cadenaEntrada) {
        this.cadenaEntrada = cadenaEntrada;
    }

    public String getCadenaEncriptada() {
        return cadenaEncriptada;
    }

    public void setCadenaEncriptada(String cadenaEncriptada) {
        this.cadenaEncriptada = cadenaEncriptada;
    }
    
    /*
    
    String password="secret";
MessageDigest sha256=MessageDigest.getInstance("SHA-256");
sha256.update(password.getBytes("UTF-8"));
byte[] digest = sha256.digest();
StringBuffer sb=new StringBuffer();
for(int i=0;i&lt;digest.length;i++){
    sb.append(String.format("%02x", digest[i]));
}
String hash=sb.toString(); 
    */

}
