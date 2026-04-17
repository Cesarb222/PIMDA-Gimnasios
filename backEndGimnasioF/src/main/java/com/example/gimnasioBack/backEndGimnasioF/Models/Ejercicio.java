package com.example.gimnasioBack.backEndGimnasioF.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "ejercicios")
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre",unique = true)
    private String nombre;

    @Column(name = "grupo_muscular")
    private String grupoMuscular;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "material")
    private String material;

    @Column(name = "imagen")
    private String imagen;

    public Ejercicio(String nombre, String grupo_muscular, String descripcion, String material, String imagen) {
        this.nombre = nombre;
        this.grupoMuscular = grupo_muscular;
        this.descripcion = descripcion;
        this.material = material;
        this.imagen = imagen;
    }
    public Ejercicio(){

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

    public String getGrupo_muscular() {
        return grupoMuscular;
    }

    public void setGrupo_muscular(String grupo_muscular) {
        this.grupoMuscular = grupo_muscular;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
