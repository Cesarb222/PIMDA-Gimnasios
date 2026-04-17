package com.example.gimnasioBack.backEndGimnasioF.Repositorios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Ejercicio;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EjercicioRepository {
    public EjercicioRepository(){

    }

    //Buscamos todos los ejercicios
    public List<Ejercicio> findAll(){
        List<Ejercicio> lista = new ArrayList<Ejercicio>();
        Session session = null;
        try{
            session = Conexion.conectar().openSession();
            lista = session.createQuery("from Ejercicio",Ejercicio.class).getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (session != null) session.close();
        }
        return lista;
    }
    //Funcion para recuperar el ejercicio por nombre
    public Ejercicio findByName(String nombreEjercicio){
        Ejercicio ej = null;
        Session session = null;
        try{
            session = Conexion.conectar().openSession();
            ej = session.createQuery("from Ejercicio WHERE nombre=:nombre",Ejercicio.class)
                    .setParameter("nombre",nombreEjercicio)
                    .getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (session != null) session.close();
        }
        return ej;
    }
}
