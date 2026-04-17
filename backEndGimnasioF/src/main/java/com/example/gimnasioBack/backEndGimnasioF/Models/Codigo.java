package com.example.gimnasioBack.backEndGimnasioF.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "codigos")
public class Codigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "codigo")
    private int codigo;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuarios usuario;

    public Codigo() {

    }

    public Codigo(int codigo, Usuarios usuario) {
        this.codigo = codigo;
        this.usuario = usuario;
    }

    public Codigo(int id, int codigo, Usuarios usuario) {
        this.id = id;
        this.codigo = codigo;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Codigo{" +
                "id=" + id +
                ", codigo=" + codigo +
                ", usuario=" + usuario +
                '}';
    }
}
