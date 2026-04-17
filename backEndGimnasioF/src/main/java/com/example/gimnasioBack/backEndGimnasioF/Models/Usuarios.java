package com.example.gimnasioBack.backEndGimnasioF.Models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="nombre",nullable = false)
    private String nombre;

    @Column(name="apellido",nullable = false)
    private String apellido;

    @Column(name="email",nullable = false,unique = true)
    private String email;

    @Column(name="passwd",nullable = false)
    private String passwd;


    @Column(name="f_registro",nullable = false)
    private Date f_registro;

    @Column(name="f_nacimiento",nullable = false)
    private Date f_nacimiento;

    @Column(name = "genero")
    private String genero;

    @Column(name = "rol")
    private String rol;

    @ManyToOne
    @JoinColumn(name = "gimnasio_id")
    private Gimnasio gimnasio;

    public Usuarios(String nombre, String apellido, String email, String passwd, Date f_registro, Date f_nacimiento,String genero,String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.passwd = passwd;
        this.f_registro = f_registro;
        this.f_nacimiento = f_nacimiento;
        this.genero = genero;
        this.rol = rol;
    }

    public Usuarios(){

    }
    public Usuarios(String email,String passwd){
        this.email = email;
        this.passwd = passwd;

    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }


    public Date getF_registro() {
        return f_registro;
    }

    public void setF_registro(Date f_registro) {
        this.f_registro = f_registro;
    }

    public Date getF_nacimiento() {
        return f_nacimiento;
    }

    public void setF_nacimiento(Date f_nacimiento) {
        this.f_nacimiento = f_nacimiento;
    }


    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Gimnasio getGimnasio() {
        return gimnasio;
    }

    public void setGimnasio(Gimnasio gimnasio) {
        this.gimnasio = gimnasio;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return this.getNombre() + " "+this.getApellido() + " Tiene la siguiente información: \n"+
                this.getEmail()+"\n-"+
                this.getF_nacimiento()+"\n-Se registro en: "+
                this.getF_registro()+"\n-";
    }
}
