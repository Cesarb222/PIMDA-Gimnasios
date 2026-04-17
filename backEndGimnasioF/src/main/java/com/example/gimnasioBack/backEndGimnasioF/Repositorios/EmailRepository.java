package com.example.gimnasioBack.backEndGimnasioF.Repositorios;

import com.example.gimnasioBack.backEndGimnasioF.Models.Codigo;
import com.example.gimnasioBack.backEndGimnasioF.Models.Entrenamiento;
import com.example.gimnasioBack.backEndGimnasioF.Models.Usuarios;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class EmailRepository {

    //Funcion que nos va a permitir añadir en la base de datos el codigo a un usuario y que con ese codigo podamos acccder
    //a restablecer contraseña sin limitaciones
    //posteriormente se eliminan todos las filas que las contenga
    public boolean addCodigo(Codigo codigo){
        Transaction tr = null;
        Session session = null;
        try{
            session = Conexion.conectar().openSession();
            tr = session.beginTransaction();
            session.persist(codigo);
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

    public boolean deleteAllByUser(Usuarios user){
        Transaction tr = null;
        Session session = null;
        try{

            session = Conexion.conectar().openSession();
            tr = session.beginTransaction();
            session.createMutationQuery("delete Codigo WHERE usuario=:id_usuario")
                    .setParameter("id_usuario",user)
                    .executeUpdate();
            tr.commit();
            return true;


        }catch (Exception e){
            if(tr!=null) tr.rollback();
            e.printStackTrace();
        }finally {
            if (session!=null)session.close();
        }
        return false;
    }
}
