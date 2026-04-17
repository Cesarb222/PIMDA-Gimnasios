package com.example.gimnasioBack.backEndGimnasioF.Repositorios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Ejercicio;
import com.example.gimnasioBack.backEndGimnasioF.Models.Entrenamiento;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EntrenoRepository {
    public EntrenoRepository(){

    }

    //Funcion para recopilar el entreno por id
    public Entrenamiento getEntreno(int id){
        Session session = null;
        try{
            session = Conexion.conectar().openSession();
            return session.find(Entrenamiento.class,id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //Funcion para recopilar todos los entrenos
    public List<Entrenamiento> getAllEjercicio(Usuarios usuarios){
        Session session = null;
        List<Entrenamiento> lista = new ArrayList<Entrenamiento>();
        try{
            session = Conexion.conectar().openSession();
            lista = session.createQuery(
                            "SELECT e FROM Entrenamiento e LEFT JOIN FETCH " +
                                    "e.ejercicios WHERE e.usuarios=:user ORDER BY f_creacion DESC",
                            Entrenamiento.class)
                    .setParameter("user", usuarios)
                    .getResultList();
            return lista;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (session!=null) session.close();
        }
        return null;
    }

    //Funcion para borrar el entreno
    public boolean deleteEjercicio(int id) {
        Transaction tr = null;
        Session session = null;
        try{
            session = Conexion.conectar().openSession();
            tr = session.beginTransaction();
            session.remove(session.find(Entrenamiento.class,id));
            tr.commit();
            return true;
        }catch (Exception e){
            if (tr!=null) tr.rollback();
            e.printStackTrace();
        }finally {
            if (session!=null) session.close();
        }
        return false;
    }

    //Funcion que inserta un entrenamieto
    public boolean addEjercicio(Entrenamiento entrenamiento){
        Transaction tr = null;
        Session session = null;
        try{
            session = Conexion.conectar().openSession();
            tr = session.beginTransaction();
            session.persist(entrenamiento);
            tr.commit();
            return true;
        }catch (Exception e){
            if (tr!=null) tr.rollback();
            e.printStackTrace();
        }finally {
            if (session!=null) session.close();
        }
        return false;
    }
}
