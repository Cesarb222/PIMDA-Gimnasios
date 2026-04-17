package com.example.gimnasioBack.backEndGimnasioF.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "registro")
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "cuerpo")
    private String cuerpo;
    @Column(name = "marca",nullable = true)
    private Float marca;
    @Column(name = "f_registro")
    private Date f_registro;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "publico")
    private boolean publico;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio")
    private Ejercicio ejercicio;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuarios usuario;

   /* @OneToMany(mappedBy = "registro")
    private List<UsuarioRegistro> registrosClientes;*/


    public Registro(Ejercicio ejercicio, boolean publico, String imagen, Date f_registro, float marca, String cuerpo, String titulo) {
        this.ejercicio = ejercicio;
        this.publico = publico;
        this.imagen = imagen;
        this.f_registro = f_registro;
        this.marca = marca;
        this.cuerpo = cuerpo;
        this.titulo = titulo;
    }

    public Registro(){

    }
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Float getMarca() {
        return marca;
    }

    public void setMarca(Float marca) {
        this.marca = marca;
    }

    public Date getF_registro() {
        return f_registro;
    }

    public void setF_registro(Date f_registro) {
        this.f_registro = f_registro;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    /*public List<UsuarioRegistro> getRegistrosClientes() {
        return registrosClientes;
    }

    public void setRegistrosClientes(List<UsuarioRegistro> registrosClientes) {
        this.registrosClientes = registrosClientes;
    }*/

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
