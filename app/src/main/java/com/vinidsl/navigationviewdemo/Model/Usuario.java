package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 17/08/2015.
 */
public class Usuario {

    private String nombre;
    private String mail;
    private String contrasenia;
    private String cargo;
    private String telefono;

    public Usuario(String nombre, String mail, String contrasenia, String cargo, String telefono) {
        this.nombre = nombre;
        this.mail = mail;
        this.contrasenia = contrasenia;
        this.cargo = cargo;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMail() {
        return mail;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getCargo() {
        return cargo;
    }

    public String getTelefono() {
        return telefono;
    }

}