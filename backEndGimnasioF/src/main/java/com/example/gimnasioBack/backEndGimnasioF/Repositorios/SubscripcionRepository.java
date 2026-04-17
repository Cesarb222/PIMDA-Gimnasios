package com.example.gimnasioBack.backEndGimnasioF.Repositorios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Subscripcion;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class SubscripcionRepository {
    public SubscripcionRepository(){}

    public boolean addSubscripcion(Subscripcion s){

        Transaction tr = null;
        Session session = null;
        try{
            session = Conexion.conectar().openSession();
            tr = session.beginTransaction();
            session.persist(s);
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
