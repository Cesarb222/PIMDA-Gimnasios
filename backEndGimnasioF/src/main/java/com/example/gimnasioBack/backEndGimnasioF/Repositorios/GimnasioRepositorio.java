package com.example.gimnasioBack.backEndGimnasioF.Repositorios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Entrenamiento;
import com.example.gimnasioBack.backEndGimnasioF.Models.Gimnasio;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class GimnasioRepositorio {

    public GimnasioRepositorio(){

    }
    public Gimnasio getGimnasio(int id){
        Session session = null;
        try{
            session = Conexion.conectar().openSession();
            return session.find(Gimnasio.class,id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Gimnasio updateGimnasio(Gimnasio g){
        Transaction tr = null;
        Session session = null;
        try {
            session = Conexion.conectar().openSession();
            tr = session.beginTransaction();
            session.merge(g);
            tr.commit();
            return g;
        }catch (Exception e){
            if (tr!=null) tr.rollback();
            e.printStackTrace();
        }
        return null;
    }
}
