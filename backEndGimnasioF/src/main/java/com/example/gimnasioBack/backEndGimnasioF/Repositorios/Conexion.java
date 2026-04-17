package com.example.gimnasioBack.backEndGimnasioF.Repositorios;

import com.example.gimnasioBack.backEndGimnasioF.Models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Configuracion para utilizar el session y poder utilizar la base de datos.
public class Conexion {
    private static final  SessionFactory  sf  = conectar();
    public static SessionFactory conectar(){
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Usuarios.class)
                .addAnnotatedClass(Gimnasio.class)
                .addAnnotatedClass(Entrenamiento.class)
                .addAnnotatedClass(UsuarioRegistro.class)
                .addAnnotatedClass(Registro.class)
                .addAnnotatedClass(Ejercicio.class)
                .addAnnotatedClass(Codigo.class)
                .addAnnotatedClass(Subscripcion.class)
                .buildSessionFactory();

    }

    public  static Session SESSION_FACTORY(){
        return sf.openSession();
    }

}
