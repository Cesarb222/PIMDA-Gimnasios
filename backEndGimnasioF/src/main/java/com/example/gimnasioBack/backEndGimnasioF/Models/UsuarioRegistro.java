package com.example.gimnasioBack.backEndGimnasioF.Models;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "usuario_registro")
public class UsuarioRegistro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "f_publicacion")
    private Date f_publicacion;

    @Column(name = "mensaje")
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_registro")
    private Registro registro;

    public UsuarioRegistro(Date f_publicacion, String mensaje, Usuarios user, Registro registro) {
        this.f_publicacion = f_publicacion;
        this.mensaje = mensaje;
        this.usuario = user;
        this.registro = registro;
    }

    public UsuarioRegistro(){

    }

    public int getId() {
        return id;
    }


    public Date getF_publicacion() {
        return f_publicacion;
    }

    public void setF_publicacion(Date f_publicacion) {
        this.f_publicacion = f_publicacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }
}
