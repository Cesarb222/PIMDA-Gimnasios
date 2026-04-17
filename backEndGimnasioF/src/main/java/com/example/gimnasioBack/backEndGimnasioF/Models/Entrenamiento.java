package com.example.gimnasioBack.backEndGimnasioF.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "entrenamientos")
public class Entrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "f_creacion")
    private Date f_creacion;

    @Column(name = "duracion")
    private int duracion;

    @ManyToOne
    @JoinColumn(name = "uuid_usuario")
    private Usuarios usuarios;

    @ManyToMany
    @JoinTable(name = "entrenamiento_ejercicios",
            joinColumns = @JoinColumn(name = "id_entrenamiento"),
            inverseJoinColumns = @JoinColumn(name = "id_ejercicio")
    )
    private List<Ejercicio> ejercicios = new ArrayList<Ejercicio>();

    public Entrenamiento(String nombre, String descripcion, Date f_creacion, int duracion, Usuarios usuarios) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.f_creacion = f_creacion;
        this.duracion = duracion;
        this.usuarios = usuarios;
    }
    public Entrenamiento(){

    }

    public int getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getF_creacion() {
        return f_creacion;
    }

    public void setF_creacion(Date f_creacion) {
        this.f_creacion = f_creacion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Usuarios getCliente() {
        return usuarios;
    }

    public void setCliente(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
}
